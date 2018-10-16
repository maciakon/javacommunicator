package server;

import shared.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private ServerSocket _serverSocket;
    private Thread _runningThread;
    private List<ClientConnection> _connectedClients;

    JavaCommunicatorServer(ServerSocket serverSocket)
    {
        _serverSocket = serverSocket;
        _connectedClients = new ArrayList<>();
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
    public void Handle(Message message)
    {

    }

    @Override
    public void Disconnect(ClientConnection clientConnection)
    {
        _connectedClients.remove(clientConnection);
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
        connectedClient.run();
    }
}