package shared.interfaces;

/**
 * Defines a contract that port validator should implement.
 */
public interface IPortValidator
{
    /**
     * Parses port number from a given string.
     * @param portNumber String port number.
     * @return Integer port number,
     */
    int GetPortNumberFromString(String portNumber);
}
