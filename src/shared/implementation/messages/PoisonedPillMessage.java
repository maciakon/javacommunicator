package shared.implementation.messages;

/**
 * This is a special purpose class used to poison (and kill) received items blocking queue upon client application exit.
 * When application exits, it is put into a blocking queue, causing blocked thread to receive such a message and react accordingly.
 */
public class PoisonedPillMessage extends MessageBase
{
}
