package client.mainWindow;

import javafx.util.Callback;

public class TabControllerFactory implements Callback<Class<?>, Object>
{
    private final JavaCommunicatorClient client;
    private int _contactIndex;

    public TabControllerFactory(JavaCommunicatorClient client, int contactIndex)
    {
        this.client = client;
        _contactIndex = contactIndex;
    }

    @Override
    public Object call(Class<?> cls)
    {
        if (cls == TabController.class)
        {
            return new TabController(this.client, _contactIndex);
        }
        try
        {
            return cls.getConstructor().newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
