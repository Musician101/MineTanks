package io.musician101.minetanks.sponge;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.minetanks.sponge.command.MTSpongeCommand;
import io.musician101.minetanks.sponge.util.SpongeInventoryStorage;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;


@Plugin(id = CommonReference.ID, name = CommonReference.NAME, version = CommonReference.VERSION, description = CommonReference.DESCRIPTION)
public class SpongeMineTanks
{
    @ConfigDir(sharedRoot = false)
    private final File configDir = new File(CommonReference.NAME);
    private SpongeBattleFieldStorage fieldStorage;
    private SpongeInventoryStorage inventoryStorage;

    public static PluginContainer getPluginContainer()
    {
        //noinspection OptionalGetWithoutIsPresent
        return Sponge.getPluginManager().getPlugin(CommonReference.ID).get();//NOSONAR
    }

    public static SpongeMineTanks instance()
    {
        //noinspection OptionalGetWithoutIsPresent
        return (SpongeMineTanks) getPluginContainer().getInstance().get();//NOSONAR
    }

    public SpongeBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public SpongeInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }


    public Logger getLogger()
    {
        return getPluginContainer().getLogger();
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        fieldStorage = new SpongeBattleFieldStorage(configDir);
        fieldStorage.loadFromFiles();

        inventoryStorage = new SpongeInventoryStorage(configDir);

        Sponge.getCommandManager().register(this, new MTSpongeCommand(), CommonCommands.MT_CMD.replace("/", ""));
        getLogger().info(CommonReference.MOVIN_ON_OUT);
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event)
    {
        fieldStorage.saveToFiles();
        getLogger().info(CommonReference.PACK_IT_UP);
    }
}
