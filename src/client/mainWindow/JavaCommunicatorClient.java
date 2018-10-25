package client.mainWindow;

import shared.HandShakeMessage;
import shared.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class JavaCommunicatorClient
{
    private ObjectInputStream _objectInputStream;
    private ObjectOutputStream _objectOutputStream;
    private Socket _socket = null;
    private Thread _sendingThread;
    private Thread _receivingThread;
    private CopyOnWriteArrayList<Packet> _messagesToSend;
    private CopyOnWriteArrayList<Packet> _receivedPackets = new CopyOnWriteArrayList<>();
    private static CountDownLatch _latch = new CountDownLatch(1);

    public JavaCommunicatorClient(String host, int portNumber)
    {
        try
        {
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

    public void Start()
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

    public void Send(Packet packet)
    {
        _messagesToSend.add(packet);
    }

    public Packet Receive()
    {
        if(!_receivedPackets.isEmpty())
        {
            var message = _receivedPackets.stream().findFirst().orElse(null);
            _receivedPackets.remove(message);
            return  message;
        }
        return null;
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
                _receivedPackets.add((Packet)objectRead);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return;
    }

    public void SendHandshake()
    {
        var handShakeMessage = new HandShakeMessage();
        handShakeMessage.Name = "TestName";
        var packet = new Packet(0, 0, handShakeMessage);
        Send(packet);
    }
}
