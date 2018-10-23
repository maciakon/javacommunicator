package server;

import shared.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void Send(Packet packet)
    {
        try
        {
            _outputStream.writeObject(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Receive()
    {
        while(!Thread.interrupted())
        {
            try
            {
                var message = (Packet) _inputStream.readObject();
                _server.Handle(_socket.getPort(), message);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public int get_Id()
    {
        return _socket.getLocalPort();
    }
}
