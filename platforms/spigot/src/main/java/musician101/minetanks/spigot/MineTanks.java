package musician101.minetanks.spigot;

import musician101.minetanks.spigot.battlefield.BattleFieldStorage;
import musician101.minetanks.spigot.commands.MTCommands;
import musician101.minetanks.spigot.listeners.BattlefieldListener;
import musician101.minetanks.spigot.listeners.MTListener;
import musician101.minetanks.spigot.util.InventoryStorage;
import musician101.minetanks.spigot.util.Menus;
import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
    BattleFieldStorage fieldStorage;
    InventoryStorage inventoryStorage;
    Menus menus;
    String prefix;

    @Override
    public void onEnable()
    {
        prefix = "[" + getDescription().getPrefix() + "]";

        fieldStorage = new BattleFieldStorage(this);
        inventoryStorage = new InventoryStorage(this);

        menus = new Menus(this);

        getServer().getPluginManager().registerEvents(new MTListener(this), this);
        getServer().getPluginManager().registerEvents(new BattlefieldListener(this), this);

        getCommand("minetanks").setExecutor(new MTCommands(this));

        getLogger().info("Movin' on out. Shuck 'em up!");
    }

    @Override
    public void onDisable()
    {
        fieldStorage.saveToFiles();
        getLogger().info("Pack it up, boys. We're heading home.");
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
