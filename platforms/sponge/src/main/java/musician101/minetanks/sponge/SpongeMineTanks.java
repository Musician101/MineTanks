package musician101.minetanks.sponge;

import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import musician101.minetanks.sponge.command.MTCommandExecutor;
import musician101.minetanks.sponge.handler.TankSelectionHandler;
import musician101.minetanks.sponge.lib.Reference;
import musician101.minetanks.sponge.listener.BattlefieldListener;
import musician101.minetanks.sponge.listener.MTListener;
import musician101.minetanks.sponge.tank.Tanks;
import musician101.minetanks.sponge.util.IconMenu;
import musician101.minetanks.sponge.util.MTUtils;
import musician101.minetanks.sponge.util.Menus;
import musician101.minetanks.sponge.util.SpongeInventoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.config.ConfigDir;

import java.io.File;
import java.util.Arrays;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class SpongeMineTanks
{
    private static SpongeBattleFieldStorage fieldStorage;
    private static SpongeInventoryStorage inventoryStorage;
    private Menus menus;
    static Game game;
    static IconMenu tankSelection;
    static Logger logger;
    static String prefix;
    @ConfigDir(sharedRoot = false)
    File configDir = new File(Reference.NAME);

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        game = event.getGame();
        logger = LoggerFactory.getLogger(Reference.NAME);
        prefix = "[" + Reference.NAME + "]";

        fieldStorage = new SpongeBattleFieldStorage(configDir);
        fieldStorage.loadFromFiles();

        inventoryStorage = new SpongeInventoryStorage(configDir);

        initMenu();

        //TODO using 'this' might not work. Won't know for sure until all missing API is implemented
        game.getEventManager().register(this, new MTListener());
        game.getEventManager().register(this, new BattlefieldListener());

        game.getCommandDispatcher().register(this, new MTCommandExecutor(), Arrays.asList("mt"));

        logger.info("Movin' on out. Shuck 'em up!");
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event)
    {
        fieldStorage.saveToFiles();
        logger.info("Pack it up, boys. We're heading home.");
    }

    public static SpongeBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public static Game getGame()
    {
        return game;
    }

    public static SpongeInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    public static Logger getLogger()
    {
        return logger;
    }

    public static String getPrefix()
    {
        return prefix;
    }

    private void initMenu()
    {
        tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler());
        for (Tanks tank : Tanks.values())
            tankSelection.setOption(tank.getId(), ItemTypes.MINECART, "\u00A7a" + tank.getName(), tank.getDescription());
    }

    public static void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }

    public static PluginContainer getPluginContainer()
    {
        return game.getPluginManager().getPlugin(Reference.ID).get();
    }
}
