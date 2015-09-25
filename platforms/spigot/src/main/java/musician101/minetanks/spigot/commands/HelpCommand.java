package musician101.minetanks.spigot.commands;

import musician101.minetanks.spigot.MineTanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class HelpCommand extends AbstractSpigotCommand
{
    private final AbstractSpigotCommand mainCommand;

    public HelpCommand(MineTanks plugin, AbstractSpigotCommand mainCommand)
    {
        super(plugin, "help", "Display help info for " + mainCommand.getUsage(), Arrays.asList(mainCommand.getUsage(), "help"), 1, "minetanks.help", false);
        this.mainCommand = mainCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, String... args)
    {
        sender.sendMessage(ChatColor.GREEN + "===== MineTanks =====");
        sender.sendMessage(ChatColor.GREEN + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GREEN + "Recommended Spigot Version: 1.8.8-R0.1-SNAPSHOT");
        sender.sendMessage(ChatColor.GREEN + "World of Tanks version: 0.9.10");
        if (sender.hasPermission("minetanks.participate") || sender.hasPermission("minetanks.edit"))
        {
            sender.sendMessage(ChatColor.GREEN + "[] = optional, <> = mandatory");
            sender.sendMessage(mainCommand.getCommandHelpInfo());
            mainCommand.getSubCommands().forEach(command -> {
                if (sender.hasPermission(command.getPermission()))
                    sender.sendMessage(command.getCommandHelpInfo());
            });
        }
        return true;
    }
}
