package shared.interfaces;

import shared.interfaces.messages.IMessage;

public interface IHandle<T extends IMessage>
{
    void Handle(T message);
}
