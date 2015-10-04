package musician101.minetanks.sponge.command;

@SuppressWarnings("serial")
public class PlayerOnlyCommandException extends Exception
{
    public PlayerOnlyCommandException(String message)
    {
        super(message);
    }
}
