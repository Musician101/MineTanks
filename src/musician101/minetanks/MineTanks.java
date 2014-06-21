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
	//TODO need method setting up scoreboards
	//TODO need to prevent menu from opening if player is assigned
	//TODO need basic plugin info for MineTanks command
	//TODO saving player location when they join a battle
	//TODO fix armor not being returned to the player
	//TODO check if a match is in progress
}
