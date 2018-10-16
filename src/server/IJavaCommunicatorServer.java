package server;

import shared.Message;

public interface IJavaCommunicatorServer
{
    void Start();
    void Stop();

    void Handle(Message message);

    void Disconnect(ClientConnection clientConnection);
}
