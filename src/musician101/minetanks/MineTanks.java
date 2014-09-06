package musician101.minetanks;

import java.io.File;

import musician101.minetanks.battlefield.BattleFieldStorage;
import musician101.minetanks.commands.MTCommands;
import musician101.minetanks.listeners.BattlefieldListener;
import musician101.minetanks.listeners.MTListener;
import musician101.minetanks.util.Menus;

import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
	BattleFieldStorage fieldStorage;
	Menus menus;
	String prefix;
	
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
		
		getServer().getPluginManager().registerEvents(new MTListener(this), this);
		getServer().getPluginManager().registerEvents(new BattlefieldListener(this), this);
		
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
	
	public String getPrefix()
	{
		return prefix;
	}
}
