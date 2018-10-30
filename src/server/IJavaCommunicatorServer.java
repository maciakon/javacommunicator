package server;

import shared.messages.IMessage;

public interface IJavaCommunicatorServer
{
    void Start();
    void Stop();

    void Handle(int localPort, IMessage packet);

    void Disconnect(ClientConnection clientConnection);

    void AddClientId(int clientId, String name);
}
