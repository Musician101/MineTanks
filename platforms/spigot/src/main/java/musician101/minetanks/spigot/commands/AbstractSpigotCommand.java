package musician101.minetanks.spigot.commands;

import musician101.minetanks.common.AbstractCommand;
import musician101.minetanks.spigot.MineTanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSpigotCommand extends AbstractCommand
{
    List<AbstractSpigotCommand> subCommands;
    protected MineTanks plugin;
    String permission;

    protected AbstractSpigotCommand(MineTanks plugin, String name, String desc, List<String> usage, int minArgs, String permission, boolean isPlayerOnly)
    {
        this(plugin, name, desc, usage, minArgs, permission, isPlayerOnly, new ArrayList<>());
    }

    protected AbstractSpigotCommand(MineTanks plugin, String name, String desc, List<String> usage, int minArgs, String permission, boolean isPlayerOnly, List<AbstractSpigotCommand> subCommands)
    {
        super(name, desc, parseUsage(usage), minArgs, isPlayerOnly);
        this.plugin = plugin;
        this.permission = permission;
        this.subCommands = subCommands;
    }

    public String getPermission()
    {
        return permission;
    }

    public List<AbstractSpigotCommand> getSubCommands()
    {
        return subCommands;
    }

    @Override
    public String getCommandHelpInfo()
    {
        return getUsage() + ChatColor.AQUA + " " + getDescription();
    }

    public abstract boolean onCommand(CommandSender sender, String... args);

    protected boolean canSenderUseCommand(CommandSender sender)
    {
        if (isPlayerOnly() && !(sender instanceof Player))
        {
            //TODO need to set up Messages class
            sender.sendMessage("Quack");
            return false;
        }

        if (sender instanceof Player && !sender.hasPermission(getPermission()))
        {
            sender.sendMessage("Quack");
            return false;
        }

        return true;
    }

    /** Plugin specific method */
    protected boolean doesFieldExist(CommandSender sender, String name)
    {
        if (plugin.getFieldStorage().getField(name) != null)
            return true;

        sender.sendMessage(org.bukkit.ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
        return false;
    }

    protected boolean minArgsMet(CommandSender sender, int args, String message)
    {
        if (args >= getMinArgs())
            return true;

        sender.sendMessage(message);
        return false;
    }

    private static String parseUsage(List<String> usageList)
    {
        String usage = ChatColor.GRAY + usageList.get(0);
        if (usageList.size() > 1)
            usage = usage + " " + ChatColor.RESET + usageList.get(1);

        if (usageList.size() > 2)
            usage = usage + " " + ChatColor.GREEN + usageList.get(2);

        return usage;
    }

    protected String[] moveArguments(String[] args)
    {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, args);
        if (list.size() > 0)
            list.remove(0);

        return list.toArray(new String[args.length - 1]);
    }
}
