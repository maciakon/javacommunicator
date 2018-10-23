package server;

import javafx.collections.FXCollections;
import shared.ContactsListUpdatedMessage;
import shared.IHandlerFactory;
import shared.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private final ServerMessageHandlersFactory _handlersFactory;
    private ServerSocket _serverSocket;
    private Thread _runningThread;
    private static List<ClientConnection> _connectedClients;
    private static Map<Integer, String> _clientNames;

    JavaCommunicatorServer(ServerSocket serverSocket)
    {
        _serverSocket = serverSocket;
        _connectedClients = new ArrayList<>();
        _clientNames = new HashMap<>();
        _handlersFactory = new ServerMessageHandlersFactory(this);
    }

    public void Start()
    {
        _runningThread = new Thread(this::run);
        _runningThread.start();
    }

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

    @Override
    public void Handle(int clientId, Packet packet)
    {
        var handler = _handlersFactory.Get(packet);
        handler.Handle(clientId, packet.get_message());
    }

    @Override
    public void Disconnect(ClientConnection clientConnection)
    {
        _connectedClients.remove(clientConnection);
    }

    @Override
    public void AddClientId(int localPort, String name)
    {
        _clientNames.put(localPort, name);
        for (ClientConnection singleClient: _connectedClients)
        {
            var contactsUpdatedMessage = new ContactsListUpdatedMessage(FXCollections.observableArrayList());
            var packet = new Packet(0, 0, contactsUpdatedMessage);
            singleClient.Send(packet);
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

    private void EstablishConnection(ClientConnection connectedClient)
    {
        _connectedClients.add(connectedClient);
        connectedClient.OpenStreams();

        new Thread(connectedClient).start();

    }
}