package server.implementation;

import shared.interfaces.messages.IMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
    private final Socket _socket;
    private final JavaCommunicatorServer _server;
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
            _server.Disconnect(this, _socket.getPort());
        }
    }

    void CloseStreams()
    {
        try
        {
            _outputStream.close();
            _inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Send(IMessage packet)
    {
        try
        {
            _outputStream.writeObject(packet);
            _outputStream.flush();
            _outputStream.reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void Receive()
    {
        while(!Thread.interrupted())
        {
            try
            {
                var message = (IMessage) _inputStream.readObject();
                message.SetSender(_socket.getPort());
                _server.Handle(_socket.getPort(), message);

            } catch (Exception e)
            {
                _server.Disconnect(this, _socket.getPort());
                e.printStackTrace();
                break;
            }
        }
    }

    public int get_Id()
    {
        return _socket.getPort();
    }
}
