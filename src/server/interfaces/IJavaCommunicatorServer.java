package server.interfaces;

import server.implementation.ClientConnection;
import shared.interfaces.messages.IMessage;

import java.util.List;

/**
 * Describes API of JavaCommunicatorServer
 */
public interface IJavaCommunicatorServer
{
    /**
     * Starts listening for incoming connections.
     */
    void Start();

    /**
     * Stops listening for incoming connections.
     * Closes the server socket.
     */
    void Stop();

    /**
     * Gets appropriate handler from a factory to handle specific message type.
     * @param clientId Socket's port that a client is connected to.
     * @param message A {@link IMessage} instance to handle.
     */
    void Handle(int clientId, IMessage message);

    /**
     * Called whenever a client disconnects.
     * @param clientConnection
     * @param port
     */
    void Disconnect(ClientConnection clientConnection, int port);

    /**
     * Adds client to a known clients list.
     * @param clientId Socket's port that a client is connected t
     * @param name Client's name.
     */
    void AddClient(int clientId, String name);

    /**
     * Returns list of connected clients.
     * @return
     */
    List<ClientConnection> get_connectedClients();
}
