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

public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private final ServerMessageHandlersFactory _handlersFactory;
    private final ServerSocket _serverSocket;
    private Thread _runningThread;
    private final List<ClientConnection> _connectedClients;
    private final HashMap<Integer, String> _clientNames;

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

    @SuppressWarnings("unchecked")
    @Override
    public void Handle(int clientId, IMessage message)
    {
        var handler = _handlersFactory.Get(message);
        handler.Handle(message);
    }


    @Override
    public void Disconnect(ClientConnection clientConnection, int port)
    {
        get_connectedClients().remove(clientConnection);
        RemoveClient(port);
        clientConnection.CloseStreams();
    }

    @Override
    public void AddClientId(int localPort, String name)
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

    private void EstablishConnection(ClientConnection connectedClient)
    {
        get_connectedClients().add(connectedClient);
        connectedClient.OpenStreams();

        new Thread(connectedClient).start();

    }

    public List<ClientConnection> get_connectedClients()
    {
        return _connectedClients;
    }

}