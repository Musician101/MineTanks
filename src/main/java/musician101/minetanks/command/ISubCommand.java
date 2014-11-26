package musician101.minetanks.command;

import java.util.List;

import org.spongepowered.api.util.command.CommandSource;

public interface ISubCommand
{
String getName();
	
	String getDescription();
	
	String getUsage();
	
	String getPermission();
	
	List<String> getAliases();
	
	void execute(CommandSource source, List<String> args) throws Exception;
}
