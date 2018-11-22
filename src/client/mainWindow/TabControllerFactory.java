package client.mainWindow;

import javafx.util.Callback;

/**
 * Encapsulates creating tab controllers.
 * Allows each and every conversation tab having its own controller,
 */
class TabControllerFactory implements Callback<Class<?>, Object>
{
    private final JavaCommunicatorClient client;
    private final int _contactIndex;

    public TabControllerFactory(JavaCommunicatorClient client, int contactIndex)
    {
        this.client = client;
        _contactIndex = contactIndex;
    }

    /**
     * Creates new tab controller.
     * @param cls
     * @return instance of the tab controller.
     */
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
