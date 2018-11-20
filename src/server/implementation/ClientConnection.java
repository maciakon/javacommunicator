package server.implementation;

import shared.interfaces.messages.IMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Serves a single client on JavaCommunicator server side.
 * Each of the instances of this class gets its own socket (port) from server socket.
 * Each of the instances of this class works in a separate thread.
 * The thread blocks until a message has been received on the input stream.
 */
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

    /**
     * Starts receiving thread.
     * The thread blocks until a message has been received.
     */
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

    /**
     * Sends a {@link IMessage} instance through a {@link ObjectOutputStream} to the client.
     * The output stream is flushed and reset after each message to provide correct message sending for objects like HashMap.
     * @param message A message to send.
     */
    public void Send(IMessage message)
    {
        try
        {
            _outputStream.writeObject(message);
            _outputStream.flush();
            _outputStream.reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Internal receiving messages thread method.
     * Gets an {@link IMessage} instance from input stream.
     * Sets sender for further processing.
     * The message is handled by {@link server.interfaces.IJavaCommunicatorServer} instance.
     */
    private void Receive()
    {
        while(!Thread.interrupted())
        {
            try
            {
                var message = (IMessage) _inputStream.readObject();
                message.SetSender(_socket.getPort());
                _server.Handle(_socket.getPort(), message);
            }
            catch (Exception e)
            {
                _server.Disconnect(this, _socket.getPort());
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Gets client socket (port).
     * @return port
     */
    public int get_Id()
    {
        return _socket.getPort();
    }
}
