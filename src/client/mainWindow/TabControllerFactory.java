package client.mainWindow;

import javafx.util.Callback;

public class TabControllerFactory implements Callback<Class<?>, Object>
{
    private final JavaCommunicatorClient client;
    private final String tabName;

    public TabControllerFactory(JavaCommunicatorClient client, String tabName)
    {
        this.client = client;
        this.tabName = tabName;
    }

    @Override
    public Object call(Class<?> cls)
    {
        if (cls == TabController.class)
        {
            return new TabController(this.client, this.tabName);
        }
        else
            try
            {
                return cls.newInstance();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }
}
