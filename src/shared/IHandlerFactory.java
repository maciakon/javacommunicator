package shared;

public interface IHandlerFactory
{
    IHandle Get(Packet packet);
}
