package shared.interfaces;

import shared.interfaces.messages.IMessage;

/**
 * Produces message handlers.
 */
public interface IHandlerFactory
{
    /**
     * Gets a message handler for a specific message type.
     * @param message {@link IMessage} instance
     * @return Handler for a message type.
     */
    IHandle Get(IMessage message);
}
