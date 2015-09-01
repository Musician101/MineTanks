package musician101.minetanks.command;

import java.util.Arrays;
import java.util.List;

import musician101.minetanks.lib.Reference.Perms;

public abstract class SubCommand implements ISubCommand
{
	List<String> aliases;
	String description;
	String name;
	String permission;
	String usage;
	
	//TODO get rid of edit command in favor of having an additional argument to specify fields
	@Deprecated
	public SubCommand(String name, String description, List<String> aliases)
	{
		this(name, description, "", aliases);
	}
	
	public SubCommand(String name, String description, String usage, List<String> aliases)
	{
		this.name = name;
		this.description = description;
		this.permission = Perms.PERMS_PREFIX + name;
		if (usage.equals(""))
			this.usage = "/mt " + name;
		else
			this.usage = "/mt " + name + " " + usage;
		
		List<String> fullAliases = Arrays.asList(name);
		fullAliases.addAll(aliases);
		this.aliases = fullAliases;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public String getUsage()
	{
		return usage;
	}

	@Override
	public String getPermission()
	{
		return permission;
	}

	@Override
	public List<String> getAliases()
	{
		return aliases;
	}
}
