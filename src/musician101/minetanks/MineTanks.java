package musician101.minetanks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import musician101.minetanks.battlefield.BattleFieldStorage;
import musician101.minetanks.commands.MTCommands;
import musician101.minetanks.handlers.NavigationHandler;
import musician101.minetanks.listeners.MTListener;
import musician101.minetanks.stats.PlayerStatStorage;
import musician101.minetanks.util.Menus;

import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
	BattleFieldStorage fieldStorage;
	Menus menus;
	PlayerStatStorage statStorage;
	String prefix;
	Map<UUID, NavigationHandler> nav = new HashMap<UUID, NavigationHandler>();
	
	@Override
	public void onEnable()
	{
		prefix = "[" + getDescription().getPrefix() + "]";
		new File(getDataFolder() + File.separator + "battlefields").mkdirs();
		new File(getDataFolder() + File.separator + "inventorystorage").mkdirs();
		new File(getDataFolder() + File.separator + "playerstats").mkdirs();
		
		fieldStorage = new BattleFieldStorage(this);
		fieldStorage.loadFromFiles();
		
		menus = new Menus(this);
		
		statStorage = new PlayerStatStorage(this);
		statStorage.loadFromFiles();
		
		getServer().getPluginManager().registerEvents(new MTListener(this), this);
		
		getCommand("minetanks").setExecutor(new MTCommands(this));
		
		getLogger().info("Movin' on out. Shuck 'em up!");
	}
	
	@Override
	public void onDisable()
	{
		fieldStorage.saveToFiles();
		getLogger().info("Pack it up, boys. We're heading home.");
	}
	
	public BattleFieldStorage getFieldStorage()
	{
		return fieldStorage;
	}
	
	public Menus getMenuHandler()
	{
		return menus;
	}
	
	public PlayerStatStorage getStatStorage()
	{
		return statStorage;
	}
	
	public String getPrefix()
	{
		return prefix;
	}
	
	public Map<UUID, NavigationHandler> getNavigation()
	{
		return nav;
	}
	//TODO implement module unlocks (high)
	//TODO implement better tech tree searching
	//TODO disable tank unlocking if the previous tank has not been unlocked
	//TODO add the rest of the modules (medium)
	//TODO add the rest of the tanks (medium)
	//TODO kills, accuracy, total damage, "premium" currencies (medium)
	//TODO possible vault integration for economy support? (low)
	//TODO help information for commands (low)
	//TODO arrow drop? (low)
	//TODO arrow travel speed? (low)
	//TODO implement camo values? (low)
}
