package server;

import shared.Message;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable
{
    private final Socket _socket;
    private JavaCommunicatorServer _server;
    private ObjectInputStream _inputStream;
    private ObjectOutputStream _outputStream;

    ClientConnection(Socket socket, JavaCommunicatorServer javaCommunicatorServer)
    {
        _socket = socket;
        _server = javaCommunicatorServer;
    }

    @Override
    public void run()
    {
        Receive();
    }

    void OpenStreams()
    {
        try
        {
            _outputStream  = new ObjectOutputStream(_socket.getOutputStream());
            _inputStream = new ObjectInputStream(_socket.getInputStream());
        }
        catch (IOException e)
        {
            _server.Disconnect(this);
        }
    }

    public void Send(Message message)
    {
        try
        {
            _outputStream.writeObject(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Receive()
    {
        try
        {
            var message = (Message)_inputStream.readObject();
            _server.Handle(message);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
