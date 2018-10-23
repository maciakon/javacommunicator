package server;

import shared.Packet;

public interface IJavaCommunicatorServer
{
    void Start();
    void Stop();

    void Handle(int localPort, Packet packet);

    void Disconnect(ClientConnection clientConnection);

    void AddClientId(int clientId, String name);
}
