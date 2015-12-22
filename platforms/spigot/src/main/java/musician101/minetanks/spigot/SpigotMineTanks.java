package musician101.minetanks.spigot;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import musician101.minetanks.spigot.command.MTSpigotCommand;
import musician101.minetanks.spigot.listener.BattlefieldListener;
import musician101.minetanks.spigot.listener.MTListener;
import musician101.minetanks.spigot.util.SpigotInventoryStorage;
import musician101.minetanks.spigot.util.Menus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
//TODO need to change spawn related commands to check for region
public class SpigotMineTanks extends JavaPlugin
{
    private SpigotBattleFieldStorage fieldStorage;
    private SpigotInventoryStorage inventoryStorage;
    private Menus menus;

    @Override
    public void onEnable()
    {
        fieldStorage = new SpigotBattleFieldStorage(this);
        inventoryStorage = new SpigotInventoryStorage(this);

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
        return (command.getName().equalsIgnoreCase(CommonReference.ID) || command.getName().equalsIgnoreCase(CommonCommands.MT_CMD.replace("/", ""))) && new MTSpigotCommand(this).onCommand(sender, args);
    }

    public SpigotBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public SpigotInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    public Menus getMenuHandler()
    {
        return menus;
    }
}
