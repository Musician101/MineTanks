package musician101.minetanks.sponge.command;

@SuppressWarnings("serial")
public class NotEnoughArgumentsException extends Exception
{
    public NotEnoughArgumentsException(String message)
    {
        super(message);
    }
}
