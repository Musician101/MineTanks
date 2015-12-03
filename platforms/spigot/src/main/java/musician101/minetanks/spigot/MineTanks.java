package musician101.minetanks.spigot;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.spigot.battlefield.BattleFieldStorage;
import musician101.minetanks.spigot.commands.MTCommands;
import musician101.minetanks.spigot.listeners.BattlefieldListener;
import musician101.minetanks.spigot.listeners.MTListener;
import musician101.minetanks.spigot.util.InventoryStorage;
import musician101.minetanks.spigot.util.Menus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
//TODO need to change spawn related commands to check for region
public class MineTanks extends JavaPlugin
{
    private BattleFieldStorage fieldStorage;
    private InventoryStorage inventoryStorage;
    private Menus menus;

    @Override
    public void onEnable()
    {
        fieldStorage = new BattleFieldStorage(this);
        inventoryStorage = new InventoryStorage(this);

        menus = new Menus(this);

        getServer().getPluginManager().registerEvents(new MTListener(this), this);
        getServer().getPluginManager().registerEvents(new BattlefieldListener(this), this);

        getLogger().info(CommonReference.MOVIN_ON_OUT);
    }

    @Override
    public void onDisable()
    {
        fieldStorage.getFields().keySet().forEach(field -> fieldStorage.getField(field).endMatch(true));
        fieldStorage.saveToFiles();
        getLogger().info(CommonReference.PACK_IT_UP);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return (command.getName().equalsIgnoreCase(CommonReference.ID) || command.getName().equalsIgnoreCase(CommonCommands.MT)) && new MTCommands(this).onCommand(sender, args);
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
}
