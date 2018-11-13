package server;

import shared.messages.IMessage;
import shared.messages.MessageBase;

import java.util.List;

public interface IJavaCommunicatorServer
{
    void Start();
    void Stop();

    void Handle(int localPort, IMessage packet);

    void Disconnect(ClientConnection clientConnection);

    void AddClientId(int clientId, String name);

    void Route(MessageBase message);

    List<ClientConnection> get_connectedClients();
}
