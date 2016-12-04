package io.musician101.minetanks.spigot;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import io.musician101.minetanks.spigot.command.MTSpigotCommand;
import io.musician101.minetanks.spigot.util.SpigotInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.spigot.AbstractSpigotPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//TODO despawn arrows if they leave the region
//TODO prevent mob spawning in region
//TODO check if code handles Draws
//TODO cancel all reload handler tasks when a player dies
//TODO implement new GUI that separates the ALL by country
public class SpigotMineTanks extends AbstractSpigotPlugin
{
    private SpigotBattleFieldStorage fieldStorage;
    private SpigotInventoryStorage inventoryStorage;

    public static SpigotMineTanks instance()
    {
        return getPlugin(SpigotMineTanks.class);
    }

    public SpigotBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public SpigotInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return (command.getName().equalsIgnoreCase(CommonReference.ID) || command.getName().equalsIgnoreCase(CommonCommands.MT_CMD.replace("/", ""))) && new MTSpigotCommand().onCommand(sender, args);
    }

    @Override
    public void onDisable()
    {
        fieldStorage.getFields().values().forEach(field -> field.endMatch(true));
        fieldStorage.saveToFiles();
        getLogger().info(CommonReference.PACK_IT_UP);
    }

    @Override
    public void onEnable()
    {
        fieldStorage = new SpigotBattleFieldStorage(this);
        inventoryStorage = new SpigotInventoryStorage(this);

        getLogger().info(CommonReference.MOVIN_ON_OUT);
    }
}
