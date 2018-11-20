package shared.implementation;

import shared.interfaces.IPortValidator;

public class PortValidator implements IPortValidator
{
    public int GetPortNumberFromString(String portNumberText)
    {
        try
        {
            return Integer.parseInt(portNumberText);
        }
        catch (Exception exc)
        {
            return -1;
        }
    }
}
