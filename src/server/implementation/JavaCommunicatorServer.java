package server.implementation;

import server.interfaces.IJavaCommunicatorServer;
import server.implementation.messageHandlers.ServerMessageHandlersFactory;
import shared.implementation.messages.ContactsListUpdatedMessage;
import shared.interfaces.messages.IMessage;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p> Multithreaded engine for JavaCommunicator server. </p>
 * Listens for incoming connections on a specified ServerSocket.
 * Spawns a new thread whenever a connection is established.
 * Schedules message handling incoming from connected clients.
 */
public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private final ServerMessageHandlersFactory _handlersFactory;
    private final ServerSocket _serverSocket;
    private final List<ClientConnection> _connectedClients;
    private final HashMap<Integer, String> _clientNames;
    private Thread _runningThread;

    /**
     * Constructor
     * @param serverSocket A server socket to listen on. Default: 44411.
     */
    JavaCommunicatorServer(ServerSocket serverSocket)
    {
        _serverSocket = serverSocket;
        _connectedClients = new ArrayList<>();
        _clientNames = new HashMap<>();
        _handlersFactory = new ServerMessageHandlersFactory(this);
    }

    /**
     * Starts listening for incoming connections.
     */
    public void Start()
    {
        _runningThread = new Thread(this::run);
        _runningThread.start();
    }

    /**
     * Stops listening for incoming connections.
     * Closes the server socket.
     */
    public void Stop()
    {
        if(_runningThread != null)
        {
            _runningThread.interrupt();
            try
            {
                _serverSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets appropriate handler from a factory to handle specific message type.
     * @param clientId Socket's port that a client is connected to.
     * @param message A {@link IMessage} instance to handle.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void Handle(int clientId, IMessage message)
    {
        var handler = _handlersFactory.Get(message);
        handler.Handle(message);
    }

    /**
     * Called whenever a client disconnects.
     * <ul>
     *     <li>Removes a client from a clients list</li>
     *     <li>Closes client's input and output streams</li>
     *     <li>Sends updated contacts list to other clients.</li>
     * </ul>
     * @param clientConnection Disconnected client connection.
     * @param port Server socket port of disconnected client.
     */
    @Override
    public void Disconnect(ClientConnection clientConnection, int port)
    {
        get_connectedClients().remove(clientConnection);
        RemoveClient(port);
        clientConnection.CloseStreams();
    }

    /**
     * Called whenever a client connects.
     * @param localPort Socket's port that a client is connected to.
     * @param name Clients name.
     */
    @Override
    public void AddClient(int localPort, String name)
    {
        _clientNames.put(localPort, name);
        for (ClientConnection singleClient: get_connectedClients())
        {
            var contactsUpdatedMessage = new ContactsListUpdatedMessage(_clientNames);
            singleClient.Send(contactsUpdatedMessage);
        }
    }

    private void RemoveClient(int localPort)
    {
        _clientNames.remove(localPort);
        for (ClientConnection singleClient: get_connectedClients())
        {
            var contactsUpdatedMessage = new ContactsListUpdatedMessage(_clientNames);
            singleClient.Send(contactsUpdatedMessage);
        }
    }

    private void run()
    {
        while(true)
        {
            try
            {
                var socket = _serverSocket.accept();
                var connectedClient = new ClientConnection(socket, this);
                EstablishConnection(connectedClient);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Spawns a thread attending a single connected client.
     * @param connectedClient Client connection to attend.
     */
    private void EstablishConnection(ClientConnection connectedClient)
    {
        get_connectedClients().add(connectedClient);
        connectedClient.OpenStreams();
        new Thread(connectedClient).start();
    }

    /**
     * Gets connected clients list.
     * @return A list of {@link ClientConnection} instances.
     */
    public List<ClientConnection> get_connectedClients()
    {
        return _connectedClients;
    }
}