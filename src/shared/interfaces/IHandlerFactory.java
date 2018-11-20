package shared.interfaces;

import shared.interfaces.messages.IMessage;

public interface IHandlerFactory
{
    IHandle Get(IMessage message);
}
