package musician101.minetanks.command;

import musician101.minetanks.lib.Reference;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements ISubCommandManager
{
    List<ISubCommand> commandList = new ArrayList<ISubCommand>();

    public CommandManager()
    {
        addCommand(new Create("create", "Create a battlefield with the given name.", "<name>", Arrays.asList("c")));
        addCommand(new Cuboid("cuboid", "Set the cuboid of the currently selected battlefield.", "<radius | xradius yradius zradius>", Arrays.asList("cu")));
        addCommand(new Edit("edit", "Set the battlefield to be edited.", "<name>", Arrays.asList("e")));
        addCommand(new Enable("enable", "Toggle wheather the currently selected battlefield is enabled or disabled.", Arrays.asList("disable", "toggle")));
        addCommand(new ForceEnd("forceend", "Forcibly end the match at the specified battlefield.", "<field>", Arrays.asList("fe")));
        addCommand(new GreenSpawn("greenspawn", "Set the spawn point for the green team for the currently selected field.", Arrays.asList("gs")));
        addCommand(new Join("join", "Join the specified field.", "<field>", Arrays.asList("j")));
        addCommand(new Leave("leave", "Leave the current battlefield you are in.", Arrays.asList("l")));
        addCommand(new RedSpawn("redspawn", "Set the spawn point for the red team for the currently selected field.", "<field>", Arrays.asList("rs")));
        addCommand(new Remove("remove", "Remove the selected battlefield.", "<name>", Arrays.asList("r")));
        addCommand(new Spectate("spectate", "Spectate the specified battlefield.", "<field>", Arrays.asList("s")));
        addCommand(new Spectators("spectators", "Set the spectators spawn point of the currently selected battlefield.", Arrays.asList("ss")));
        addCommand(new Status("status", "Check the status of the currently selected battlefield.", Arrays.asList("st")));
    }

    @Override
    public List<ISubCommand> getCommandList()
    {
        return commandList;
    }

    @Override
    public void addCommand(ISubCommand command)
    {
        commandList.add(command);
    }

    @Override
    public void removeCommand(ISubCommand command)
    {
        commandList.remove(command);
    }

    @Override
    public List<String> getHelp(int page)
    {
        //Page argument is ignored until multiple help pages are needed
        List<String> help = new ArrayList<String>();
        help.add(TextColors.GREEN + "===== MineTanks =====");
        help.add(TextColors.GREEN + "Version: " + Reference.VERSION);
        help.add(TextColors.GREEN + "Recommended SpongeAPI Version: 1.0.0-SNAPSHOT");
        help.add(TextColors.GREEN + "World of Tanks version: 0.9.2");
        help.add(TextColors.GREEN + "[] = optional, <> = mandatory");
        for (ISubCommand command : commandList)
            help.add(TextColors.GREEN + command.getUsage() + ": " + TextColors.AQUA + command.getDescription());

        return help;
    }
}
