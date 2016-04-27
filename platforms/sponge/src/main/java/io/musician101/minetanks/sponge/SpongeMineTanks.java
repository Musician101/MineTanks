package io.musician101.minetanks.sponge;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.sponge.tank.SpongeTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.minetanks.sponge.command.MTSpongeCommand;
import io.musician101.minetanks.sponge.handler.TankSelectionHandler;
import io.musician101.minetanks.sponge.listener.BattlefieldListener;
import io.musician101.minetanks.sponge.listener.MTListener;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import io.musician101.minetanks.sponge.util.IconMenu;
import io.musician101.minetanks.sponge.util.SpongeInventoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.File;

@Plugin(id = CommonReference.ID, name = CommonReference.NAME, version = CommonReference.VERSION)
public class SpongeMineTanks
{
    private SpongeBattleFieldStorage fieldStorage;
    private SpongeInventoryStorage inventoryStorage;
    private IconMenu tankSelection;
    private Logger logger;
    @ConfigDir(sharedRoot = false)
    private final File configDir = new File(CommonReference.NAME);

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        logger = LoggerFactory.getLogger(CommonReference.NAME);

        fieldStorage = new SpongeBattleFieldStorage(this, configDir);
        fieldStorage.loadFromFiles();

        inventoryStorage = new SpongeInventoryStorage(configDir);

        initMenu();

        Game game = Sponge.getGame();
        game.getEventManager().registerListeners(this, new MTListener());
        game.getEventManager().registerListeners(this, new BattlefieldListener());

        game.getCommandManager().register(this, new MTSpongeCommand(this), CommonCommands.MT_CMD.replace("/", ""));

        logger.info(CommonReference.MOVIN_ON_OUT);
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event)
    {
        fieldStorage.saveToFiles();
        logger.info(CommonReference.PACK_IT_UP);
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
        return logger;
    }

    private void initMenu()
    {
        tankSelection = new IconMenu(CommonReference.TANK_SELECTION, new TankSelectionHandler());
        for (SpongeTank tank : SpongeTanks.ALL)
            tankSelection.setOption(ItemTypes.MINECART, (LiteralText) Text.builder().append(Text.of(tank.getName())).color(TextColors.GREEN).build(), CommonItemText.tankType(tank.getType()));
    }

    public void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
