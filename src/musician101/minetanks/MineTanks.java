package musician101.minetanks;

import java.io.File;

import musician101.minetanks.battlefield.BattlefieldStorage;
import musician101.minetanks.command.MTCommandExecutor;
import musician101.minetanks.handler.TankSelectionHandler;
import musician101.minetanks.listener.BattlefieldListener;
import musician101.minetanks.listener.MTListener;
import musician101.minetanks.tank.Tanks;
import musician101.minetanks.util.IconMenu;
import musician101.minetanks.util.MTUtils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MineTanks extends JavaPlugin
{
	static BattlefieldStorage fieldStorage;
	static String prefix;
	static IconMenu tankSelection;
	
	@Override
	public void onEnable()
	{
		prefix = "[" + getDescription().getPrefix() + "]";
		new File(getDataFolder() + File.separator + "battlefields").mkdirs();
		new File(getDataFolder() + File.separator + "inventorystorage").mkdirs();
		
		fieldStorage = new BattlefieldStorage(this);
		fieldStorage.loadFromFiles();
		
		initMenu();
		
		getServer().getPluginManager().registerEvents(new MTListener(this), this);
		getServer().getPluginManager().registerEvents(new BattlefieldListener(this), this);
		
		getCommand("minetanks").setExecutor(new MTCommandExecutor());
		
		getLogger().info("Movin' on out. Shuck 'em up!");
	}
	
	@Override
	public void onDisable()
	{
		fieldStorage.saveToFiles();
		getLogger().info("Pack it up, boys. We're heading home.");
	}
	
	public static BattlefieldStorage getFieldStorage()
	{
		return fieldStorage;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}
	
	private void initMenu()
	{
		tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler(this), this);
		for (Tanks tank : Tanks.values())
			tankSelection.setOption(tank.getId(), new ItemStack(Material.MINECART, 1), "§a" + tank.getName(), tank.getDescription());
	}
	
	public static void openTankMenu(Player player)
	{
		tankSelection.open(player);
	}
}
