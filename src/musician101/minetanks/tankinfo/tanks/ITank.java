package musician101.minetanks.tankinfo.tanks;

import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.IInfo;
import musician101.minetanks.tankinfo.TankTypes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public interface ITank extends IInfo
{	
	public Countries getCountry();
	
	public TankTypes getType();
	
	public ItemStack[] getArmor();
	
	public Inventory getWeapons();
	
	public PotionEffect getSpeed();
	
	public double reloadTime();
	
	public int getLevel();
}
