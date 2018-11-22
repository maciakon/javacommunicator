package shared.interfaces;

import shared.interfaces.messages.IMessage;

/**
 * Describes a generic message handler.
 * @param <T> A IMessage instance.
 */
public interface IHandle<T extends IMessage>
{
    /**
     * Handles generic message.
     * @param message a message to be handled.
     */
    void Handle(T message);
}
