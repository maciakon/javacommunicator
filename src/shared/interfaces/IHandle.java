package shared.interfaces;

import shared.interfaces.messages.IMessage;

/**
 * Describes a generic message handler.
 * @param <T> A IMessage instance.
 */
public interface IHandle<T extends IMessage>
{
    void Handle(T message);
}
