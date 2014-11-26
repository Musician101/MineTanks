package musician101.minetanks;

import java.io.File;
import java.util.Arrays;

import musician101.minetanks.battlefield.BattlefieldStorage;
import musician101.minetanks.command.MTCommandExecutor;
import musician101.minetanks.handler.TankSelectionHandler;
import musician101.minetanks.lib.Reference;
import musician101.minetanks.listener.BattlefieldListener;
import musician101.minetanks.listener.MTListener;
import musician101.minetanks.tank.Tanks;
import musician101.minetanks.util.IconMenu;
import musician101.minetanks.util.MTUtils;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Player;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.Owner;
import org.spongepowered.api.util.event.Subscribe;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class MineTanks
{
	static BattlefieldStorage fieldStorage;
	//TODO move to battlefieldstorage or battlfield class
	@Deprecated
	public static File battlefields;
	//TODO move to battlefieldstorage or battlefield class
	@Deprecated
	public static File inventoryStorage;
	static Game game;
	static IconMenu tankSelection;
	static Logger logger;
	static Owner owner;
	static String prefix;
	
	@Subscribe
	public void preInit(PreInitializationEvent event)
	{
		game = event.getGame();
		logger = event.getPluginLog();
		prefix = "[" + Reference.NAME + "]";
		
		//TODO Temporary workaround until Owner get's a full implementation
		owner = new Owner()
		{
			public String getId()
			{
				return Reference.ID;
			}
		};
		
		//TODO move battlefields object to BattleFieldStorage
		battlefields = new File(event.getRecommendedConfigurationDirectory(), "battlefields");
		battlefields.mkdirs();
		inventoryStorage = new File(event.getRecommendedConfigurationDirectory(), "inventorystorage");
		inventoryStorage.mkdirs();
		
		fieldStorage = new BattlefieldStorage();
		fieldStorage.loadFromFiles();
		
		initMenu();
		
		game.getEventManager().register(owner, new MTListener());
		game.getEventManager().register(owner, new BattlefieldListener());
		
		game.getCommandDispatcher().register(owner, new MTCommandExecutor(), Arrays.asList("mt"));
		
		logger.info("Movin' on out. Shuck 'em up!");
	}
	
	@Subscribe
	public void serverStopping(ServerStoppingEvent event)
	{
		fieldStorage.saveToFiles();
		logger.info("Pack it up, boys. We're heading home.");
	}
	
	public static BattlefieldStorage getFieldStorage()
	{
		return fieldStorage;
	}
	
	public static Game getGame()
	{
		return game;
	}
	
	public static Logger getLogger()
	{
		return logger;
	}
	
	public static Owner getOwner()
	{
		return owner;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}
	
	private void initMenu()
	{
		tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler());
		for (Tanks tank : Tanks.values())
			tankSelection.setOption(tank.getId(), new ItemStack(ItemTypes.MINECART, 1), "\u00A7a" + tank.getName(), tank.getDescription());
	}
	
	public static void openTankMenu(Player player)
	{
		tankSelection.open(player);
	}
}
