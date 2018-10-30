package server;

public interface IServerMessageHandler<T>
{
    void Handle(int senderId, int receiverId, T message);
}
