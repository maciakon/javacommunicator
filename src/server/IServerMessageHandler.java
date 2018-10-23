package server;

public interface IServerMessageHandler<T>
{
    void Handle(int clientId, T message);
}
