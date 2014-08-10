package musician101.minetanks;

import java.io.File;

import musician101.minetanks.battlefield.BattleFieldStorage;
import musician101.minetanks.commands.MTCommands;
import musician101.minetanks.listeners.MTListener;
import musician101.minetanks.menu.Menus;
import musician101.minetanks.stats.PlayerStatStorage;

import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
	public BattleFieldStorage fieldStorage;
	public PlayerStatStorage statStorage;
	public String prefix;
	
	@Override
	public void onEnable()
	{
		prefix = "[" + getDescription().getPrefix() + "]";
		new File(getDataFolder() + File.separator + "battlefields").mkdirs();
		new File(getDataFolder() + File.separator + "inventorystorage").mkdirs();
		new File(getDataFolder() + File.separator + "playerstats").mkdirs();
		
		fieldStorage = new BattleFieldStorage(this);
		fieldStorage.loadFromFiles();
		
		statStorage = new PlayerStatStorage(this);
		statStorage.loadFromFiles();
		
		getServer().getPluginManager().registerEvents(new MTListener(this), this);
		
		getCommand("minetanks").setExecutor(new MTCommands(this));
		
		Menus.init(this);
		
		getLogger().info("Movin' on out. Shuck 'em up!");
	}
	
	@Override
	public void onDisable()
	{
		fieldStorage.saveToFiles();
		getLogger().info("Pack it up, boys. We're heading home.");
	}
	//TODO add the rest of the tanks (medium)
	//TODO kills, accuracy, total damage, "premium" currencies (medium)
	//TODO possible vault integration for economy support? (low)
	//TODO help information for commands (low)
	//TODO arrow drop? (low)
	//TODO arrow travel speed? (low)
	//TODO implement camo values? (low)
	//TODO create methods for fetching storage classes & custom prefix (low)
}
