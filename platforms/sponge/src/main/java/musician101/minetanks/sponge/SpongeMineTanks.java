package musician101.minetanks.sponge;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import musician101.minetanks.sponge.command.MTCommand;
import musician101.minetanks.sponge.handler.TankSelectionHandler;
import musician101.minetanks.sponge.listener.BattlefieldListener;
import musician101.minetanks.sponge.listener.MTListener;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.tank.Tanks;
import musician101.minetanks.sponge.util.IconMenu;
import musician101.minetanks.sponge.util.SpongeInventoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.io.File;

@Plugin(id = CommonReference.ID, name = CommonReference.NAME, version = CommonReference.VERSION)
public class SpongeMineTanks
{
    private static SpongeBattleFieldStorage fieldStorage;
    private static SpongeInventoryStorage inventoryStorage;
    static IconMenu tankSelection;
    static Logger logger;
    @ConfigDir(sharedRoot = false)
    File configDir = new File(CommonReference.NAME);

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        logger = LoggerFactory.getLogger(CommonReference.NAME);

        fieldStorage = new SpongeBattleFieldStorage(configDir);
        fieldStorage.loadFromFiles();

        inventoryStorage = new SpongeInventoryStorage(configDir);

        initMenu();

        Game game = event.getGame();
        game.getEventManager().registerListeners(this, new MTListener());
        game.getEventManager().registerListeners(this, new BattlefieldListener());

        game.getCommandManager().register(this, new MTCommand(), CommonCommands.MT_CMD.replace("/", ""));

        logger.info(CommonReference.MOVIN_ON_OUT);
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event)
    {
        fieldStorage.saveToFiles();
        logger.info(CommonReference.PACK_IT_UP);
    }

    public static SpongeBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public static SpongeInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    public static Logger getLogger()
    {
        return logger;
    }

    private void initMenu()
    {
        tankSelection = new IconMenu("Tank Selection", new TankSelectionHandler());
        for (Tank tank : Tanks.tankList)
            tankSelection.setOption(ItemTypes.MINECART, Texts.builder().append(Texts.of(tank.getName())).color(TextColors.GREEN).build(), tank.getDescription());
    }

    public static void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
