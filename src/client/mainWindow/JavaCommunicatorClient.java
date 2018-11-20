package client.mainWindow;

import shared.messages.HandShakeMessage;
import shared.messages.IMessage;
import shared.messages.PoisonedPillMessage;

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

public class JavaCommunicatorClient
{
    private ObjectInputStream _objectInputStream;
    private ObjectOutputStream _objectOutputStream;
    private Socket _socket = null;
    private Thread _sendingThread;
    private Thread _receivingThread;
    private CopyOnWriteArrayList<IMessage> _messagesToSend;
    private static CountDownLatch _latch = new CountDownLatch(1);
    private String _login;
    private BlockingQueue<IMessage> _receivedPacks;
    private HashMap<Integer, Map.Entry<Integer, String>> _contacts;
    private HashMap<Integer, TabController> conversationTabsControllers = new HashMap<>();


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

    void Start()
    {
        _sendingThread = new Thread(this::SendingLoop);
        _receivingThread = new Thread(this::ReceivingLoop);

        _receivingThread.start();
        _sendingThread.start();
    }

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

    public void Send(IMessage message)
    {
        _messagesToSend.add(message);
    }

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
                _sendingThread.sleep(200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
        }
        return;
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
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                break;
            }
        }
        return;
    }

    public void SendHandshake()
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

    public HashMap<Integer, Map.Entry<Integer,String>> getContacts()
    {
        return _contacts;
    }

    public HashMap<Integer, TabController> getConversationTabsControllers()
    {
        return conversationTabsControllers;
    }

    public String GetLogin() { return _login; }
}
