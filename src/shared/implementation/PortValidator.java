package shared.implementation;

import shared.interfaces.IPortValidator;

/**
 * Validates port number parsing a given string.
 */
public class PortValidator implements IPortValidator
{
    /**
     * Converts given string into an integer port number.
     * @param portNumberText string port number
     * @return integer port number, -1 in case the passed string is not valid.
     */
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
