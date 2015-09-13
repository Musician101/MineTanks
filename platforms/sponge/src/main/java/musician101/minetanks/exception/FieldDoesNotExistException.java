package musician101.minetanks.exception;

@SuppressWarnings("serial")
public class FieldDoesNotExistException extends Exception
{
    public FieldDoesNotExistException(String message)
    {
        super(message);
    }
}
