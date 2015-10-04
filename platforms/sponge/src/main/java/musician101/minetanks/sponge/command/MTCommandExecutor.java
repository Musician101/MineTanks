package musician101.minetanks.sponge.command;

import com.google.common.base.Optional;
import musician101.minetanks.sponge.lib.Reference.Messages;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MTCommandExecutor implements CommandCallable
{
    CommandManager cm;

    public MTCommandExecutor()
    {
        cm = new CommandManager();
    }

    //TODO fix all of these so they do not return null
    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException
    {
        return null;
    }

    @Override
    public boolean call(CommandSource source, String arguments, List<String> parents) throws CommandException
    {
        if (!(source instanceof Player))
        {
            for (String msg : cm.getHelp(0))
                source.sendMessage(msg);

            return true;
        }

        String[] mainArgs = arguments.split(" ");
        List<String> args = new ArrayList<String>();
        Collections.addAll(args, mainArgs);
        if (mainArgs.length > 0)
            args.remove(mainArgs[0]);

        for (ISubCommand cmd : cm.getCommandList())
        {
            for (String alias : cmd.getAliases())
            {
                if (alias.equalsIgnoreCase(mainArgs[0]))
                {
                    try
                    {
                        cmd.execute(source, args);
                    }
                    catch (Exception e)
                    {
                        source.sendMessage(e.getMessage());
                        return true;
                    }
                    return true;
                }
            }
        }

        source.sendMessage(Messages.NEGATIVE_PREFIX + "Unkown command: " + mainArgs[0]);
        return true;
    }

    @Override
    public boolean testPermission(CommandSource source)
    {
        return false;
    }

    @Override
    public Optional<String> getShortDescription()
    {
        return null;
    }

    @Override
    public Optional<String> getHelp()
    {
        return null;
    }

    @Override
    public String getUsage()
    {
        return null;
    }
}
