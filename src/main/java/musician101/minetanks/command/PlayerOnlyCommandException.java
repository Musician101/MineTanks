package musician101.minetanks.command;

@SuppressWarnings("serial")
public class PlayerOnlyCommandException extends Exception
{
	public PlayerOnlyCommandException(String message)
	{
		super(message);
	}
}
