package musician101.minetanks;

import java.io.File;

import musician101.minetanks.battlefield.BattleFieldStorage;
import musician101.minetanks.commands.MTCommands;
import musician101.minetanks.listeners.FieldListener;
import musician101.minetanks.menu.Menus;

import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
	public BattleFieldStorage fieldStorage;
	public String prefix;
	
	@Override
	public void onEnable()
	{
		prefix = "[" + getDescription().getPrefix() + "]";
		new File(getDataFolder() + File.separator + "battlefields").mkdirs();
		new File(getDataFolder() + File.separator + "inventorystorage").mkdirs();
		
		
		fieldStorage = new BattleFieldStorage(this);
		fieldStorage.loadFromFiles();
		
		getServer().getPluginManager().registerEvents(new FieldListener(this), this);
		
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
	//TODO prevent player from picking new tank if they're ready
	//TODO need method setting up scoreboards
	//TODO need basic plugin info for MineTanks command
	//TODO splash damage mechanic for artillery
	//TODO experience, credits, "premium" currencies
	//TODO possible vault integration for economy support?
}
