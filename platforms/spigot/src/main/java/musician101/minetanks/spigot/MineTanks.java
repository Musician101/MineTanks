package musician101.minetanks.spigot;

import musician101.minetanks.spigot.battlefield.BattleFieldStorage;
import musician101.minetanks.spigot.commands.MTCommands;
import musician101.minetanks.spigot.listeners.BattlefieldListener;
import musician101.minetanks.spigot.listeners.MTListener;
import musician101.minetanks.spigot.util.InventoryStorage;
import musician101.minetanks.spigot.util.Menus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
    private BattleFieldStorage fieldStorage;
    private InventoryStorage inventoryStorage;
    private Menus menus;
    private String prefix;

    @Override
    public void onEnable()
    {
        prefix = "[" + getDescription().getPrefix() + "] ";

        fieldStorage = new BattleFieldStorage(this);
        inventoryStorage = new InventoryStorage(this);

        menus = new Menus(this);

        getServer().getPluginManager().registerEvents(new MTListener(this), this);
        getServer().getPluginManager().registerEvents(new BattlefieldListener(this), this);

        getLogger().info("Movin' on out. Shuck 'em up!");
    }

    @Override
    public void onDisable()
    {
        fieldStorage.saveToFiles();
        getLogger().info("Pack it up, boys. We're heading home.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return (command.getName().equalsIgnoreCase("minetanks") || command.getName().equalsIgnoreCase("mt")) && new MTCommands(this).onCommand(sender, args);
    }

    public BattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public InventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    public Menus getMenuHandler()
    {
        return menus;
    }

    public String getPrefix()
    {
        return prefix;
    }
}
