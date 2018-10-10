package shared;

public class PortValidator implements IPortValidator
{
    public boolean ValidatePortNumber(String portNumberText) {
        try
        {
            Integer.parseInt(portNumberText);
            return true;
        }
        catch (Exception exc)
        {
            return false;
        }
    }
}
