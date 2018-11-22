package client.mainWindow;

import shared.implementation.messages.HandShakeMessage;
import shared.interfaces.messages.IMessage;
import shared.implementation.messages.PoisonedPillMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * This is the client's engine taking care of communication with the server. Two threads take part in this process,
 * One thread processes sending messages while the other processes receiving.
 * Messages are sent and received through appropriate {@link ObjectInputStream} and {@link ObjectOutputStream} objects,
 * <p>
 *     Received messages are added to a {@link BlockingQueue} which allows a receiving thread to block while waiting for messages to arrive.
 *     Messages to sent are added to a thread safe {@link CopyOnWriteArrayList}.
 *     Sending thread checks on whether is there any message to send on the list and sends it when one is found.
 * </p>
 */
public class JavaCommunicatorClient
{
    private ObjectInputStream _objectInputStream;
    private ObjectOutputStream _objectOutputStream;
    private Socket _socket = null;
    private Thread _sendingThread;
    private Thread _receivingThread;
    private CopyOnWriteArrayList<IMessage> _messagesToSend;
    private static final CountDownLatch _latch = new CountDownLatch(1);
    private final String _login;
    private BlockingQueue<IMessage> _receivedPacks;
    private HashMap<Integer, Map.Entry<Integer, String>> _contacts;
    private final HashMap<Integer, TabController> conversationTabsControllers = new HashMap<>();


    /**
     * Creates the object,
     * @param host a server address
     * @param portNumber a server port number
     * @param login logged user name
     */
    public JavaCommunicatorClient(String host, int portNumber, String login)
    {
        _login = login;
        try
        {
            _receivedPacks = new ArrayBlockingQueue<>(100);
            _socket = new Socket(host, portNumber);
            _objectOutputStream = new ObjectOutputStream(_socket.getOutputStream());
            _objectInputStream = new ObjectInputStream(_socket.getInputStream());
            _messagesToSend = new CopyOnWriteArrayList<>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Starts both sending and receiving threads.
     */
    void Start()
    {
        _sendingThread = new Thread(this::SendingLoop);
        _receivingThread = new Thread(this::ReceivingLoop);

        _receivingThread.start();
        _sendingThread.start();
    }

    /**
     * Gracefully disconnects from a server,
     */
    public void Stop()
    {
        if(_sendingThread != null)
        {
            _sendingThread.interrupt();
            _receivingThread.interrupt();
            _receivedPacks.add(new PoisonedPillMessage());

            try
            {
                _socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sens a message to the server.
     * @param message message to send
     */
    public void Send(IMessage message)
    {
        _messagesToSend.add(message);
    }

    /**
     * This method is accessed from a thread external to this class.
     * Internal receiving thread adds messages to the collection.
     * @return a received message,
     */
    public IMessage Receive()
    {
            try
            {
                return _receivedPacks.take();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return null;
            }
    }

    private void SendingLoop()
    {
        try
        {
            Thread.sleep(500);
            _latch.await();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        SendHandshake();
        while(!_sendingThread.isInterrupted())
        {
            if (!_messagesToSend.isEmpty())
            {
                var messageToSend = _messagesToSend.stream().findFirst().orElse(null);
                _messagesToSend.remove(messageToSend);
                try
                {
                    _objectOutputStream.writeObject(messageToSend);
                    _objectOutputStream.flush();
                    _objectOutputStream.reset();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    private void ReceivingLoop()
    {
        while(!_receivingThread.isInterrupted())
        {
            try
            {
                _latch.countDown();
                var objectRead = _objectInputStream.readObject();
                _receivedPacks.add((IMessage) objectRead);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    private void SendHandshake()
    {
        var handShakeMessage = new HandShakeMessage();
        handShakeMessage.Name = _login;
        Send(handShakeMessage);
    }

    public void setContacts(HashMap<Integer, Map.Entry<Integer, String>> contacts)
    {
        // contacts is a map of: index on list view to <port, name> of a client
        _contacts = contacts;
    }

    /**
     * Gets a map of contacts that is an index on the contact list mapped to the port and name of a contact.
     * @return the map
     */
    public HashMap<Integer, Map.Entry<Integer,String>> getContacts()
    {
        return _contacts;
    }

    /**
     * As each and every conversation tab has its own controller, this method returns a collection of them.
     * @return collection of tab controllers
     */
    public HashMap<Integer, TabController> getConversationTabsControllers()
    {
        return conversationTabsControllers;
    }

    /**
     * Gets a logged user name.
     * @return login
     */
    public String GetLogin() { return _login; }
}
