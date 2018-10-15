package server;

import java.io.IOException;
import java.net.ServerSocket;

public class JavaCommunicatorServer implements IJavaCommunicatorServer
{
    private ServerSocket _serverSocket;
    private Thread _runningThread;

    JavaCommunicatorServer(ServerSocket serverSocket)
    {
        _serverSocket = serverSocket;
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

    private void run()
    {
        while(true)
        {
            try
            {
                var socket = _serverSocket.accept();
                new ClientConnection(socket).run();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
}