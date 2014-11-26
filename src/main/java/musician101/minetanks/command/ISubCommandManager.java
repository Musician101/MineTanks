package musician101.minetanks.command;

import java.util.List;

public interface ISubCommandManager
{
	List<ISubCommand> getCommandList();
	
	void addCommand(ISubCommand command);
	
	void removeCommand(ISubCommand command);
	
	List<String> getHelp(int page);
}
