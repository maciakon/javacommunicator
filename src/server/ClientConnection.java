package server;


import java.net.Socket;

public class ClientConnection implements Runnable
{
    private Socket _socket;


    ClientConnection(Socket socket)
    {
        _socket = socket;
    }

    @Override
    public void run()
    {
        // serve for client request
        // until connection close


        // client says hi
        // gets added to server list
        // the list gets propagated to all of the clients
    }
}
