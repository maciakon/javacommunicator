package server;

import server.messageHandlers.ServerMessageHandlersFactory;
import shared.messages.ContactsListUpdatedMessage;
import shared.messages.IMessage;
import shared.messages.MessageBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private final ServerMessageHandlersFactory _handlersFactory;
    private ServerSocket _serverSocket;
    private Thread _runningThread;
    private List<ClientConnection> _connectedClients;
    private HashMap<Integer, String> _clientNames;

    JavaCommunicatorServer(ServerSocket serverSocket)
    {
        _serverSocket = serverSocket;
        set_connectedClients(new ArrayList<>());
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
    public void Handle(int clientId, IMessage message)
    {
        var handler = _handlersFactory.Get(message);
        handler.Handle(message);
    }


    @Override
    public void Disconnect(ClientConnection clientConnection)
    {
        get_connectedClients().remove(clientConnection);
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

    @Override
    public void Route(MessageBase message)
    {
        ClientConnection recipient = (ClientConnection) get_connectedClients().stream().filter(client-> client.get_Id()== message.getRecipientId());
        recipient.Send(message);
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

    public void set_connectedClients(List<ClientConnection> _connectedClients)
    {
        this._connectedClients = _connectedClients;
    }
}