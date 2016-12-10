package io.musician101.minetanks.sponge;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.minetanks.sponge.command.MTSpongeCommands;
import io.musician101.minetanks.sponge.util.SpongeInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.AbstractConfig;
import io.musician101.musicianlibrary.java.minecraft.sponge.AbstractSpongePlugin;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.MLCommandCallable;
import java.io.File;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;


@Plugin(id = CommonReference.ID, name = CommonReference.NAME, version = CommonReference.VERSION, description = CommonReference.DESCRIPTION)
public class SpongeMineTanks extends AbstractSpongePlugin<AbstractConfig> {

    private final File configDir = new File(CommonReference.NAME);
    private SpongeBattleFieldStorage fieldStorage;
    private SpongeInventoryStorage inventoryStorage;

    public static PluginContainer getPluginContainer() {
        return Sponge.getPluginManager().getPlugin(CommonReference.ID).get();
    }

    public static SpongeMineTanks instance() {
        return (SpongeMineTanks) getPluginContainer().getInstance().get();
    }

    public SpongeBattleFieldStorage getFieldStorage() {
        return fieldStorage;
    }

    public SpongeInventoryStorage getInventoryStorage() {
        return inventoryStorage;
    }

    @Override
    public Logger getLogger() {
        return getPluginContainer().getLogger();
    }
    //TODO write AbstractMTSpongeCommand and implement it, refer to AbstractMTSpigotCommand

    @Listener
    public void onServerStart(GameStartingServerEvent event) {
        fieldStorage = new SpongeBattleFieldStorage(configDir);
        fieldStorage.loadFromFiles();

        inventoryStorage = new SpongeInventoryStorage(configDir);

        Sponge.getCommandManager().register(this, new MLCommandCallable<>(getPluginContainer(), MTSpongeCommands.mt()), CommonCommands.MT_CMD.replace("/", ""));
        getLogger().info(CommonReference.MOVIN_ON_OUT);
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        fieldStorage.saveToFiles();
        getLogger().info(CommonReference.PACK_IT_UP);
    }
}
