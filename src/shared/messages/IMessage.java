package shared.messages;

public interface IMessage
{
    void SetSender(int port);

    int GetSender();
}
