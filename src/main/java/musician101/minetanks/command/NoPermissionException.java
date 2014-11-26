package musician101.minetanks.command;

@SuppressWarnings("serial")
public class NoPermissionException extends Exception
{
	public NoPermissionException(String message)
	{
		super(message);
	}
}
