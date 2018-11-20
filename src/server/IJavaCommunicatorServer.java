package server;

import shared.messages.IMessage;

import java.util.List;

public interface IJavaCommunicatorServer
{
    void Start();
    void Stop();

    void Handle(int localPort, IMessage packet);

    void Disconnect(ClientConnection clientConnection, int port);

    void AddClientId(int clientId, String name);

    List<ClientConnection> get_connectedClients();
}
