package shared;

import shared.messages.IMessage;

public interface IHandlerFactory
{
    IHandle Get(IMessage message);
}
