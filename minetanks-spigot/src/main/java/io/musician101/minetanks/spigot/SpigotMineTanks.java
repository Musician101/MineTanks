package io.musician101.minetanks.spigot;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import io.musician101.minetanks.spigot.command.MTSpigotCommands;
import io.musician101.minetanks.spigot.util.SpigotInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.AbstractConfig;
import io.musician101.musicianlibrary.java.minecraft.spigot.AbstractSpigotPlugin;

//TODO despawn arrows if they leave the region
//TODO prevent mob spawning in region
//TODO check if code handles Draws
//TODO cancel all reload handler tasks when a player dies
//TODO implement new GUI that separates the ALL by country
public class SpigotMineTanks extends AbstractSpigotPlugin<AbstractConfig, SpigotMineTanks> {

    private SpigotBattleFieldStorage fieldStorage;
    private SpigotInventoryStorage inventoryStorage;

    public static SpigotMineTanks instance() {
        return getPlugin(SpigotMineTanks.class);
    }

    public SpigotBattleFieldStorage getFieldStorage() {
        return fieldStorage;
    }

    public SpigotInventoryStorage getInventoryStorage() {
        return inventoryStorage;
    }

    @Override
    public void onDisable() {
        fieldStorage.getFields().values().forEach(field -> field.endMatch(true));
        fieldStorage.saveToFiles();
        getLogger().info(CommonReference.PACK_IT_UP);
    }

    @Override
    public void onEnable() {
        //TODO left off here. Updating commands to use new command builders
        fieldStorage = new SpigotBattleFieldStorage();
        inventoryStorage = new SpigotInventoryStorage(this);
        commands.add(MTSpigotCommands.mt());
        getLogger().info(CommonReference.MOVIN_ON_OUT);
    }
}
