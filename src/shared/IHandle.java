package shared;

import shared.messages.IMessage;

public interface IHandle<T extends IMessage>
{
    void Handle(T message);
}
