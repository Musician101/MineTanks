package musician101.minetanks.tankinfo.modules;

import java.util.Arrays;

import musician101.minetanks.tankinfo.TankTypes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Turrets
{
	/** Chinese Light Turrets */
	FT_18("FT-18", TankTypes.LIGHT, 22, 22, 16),
	T_26_MODEL_1936("T-26 model 1936", TankTypes.LIGHT, 30, 15, 15),
	TYPE_97_KAI("Type 97-Kai", TankTypes.LIGHT, 25, 25, 25),
	M5A1("M5A1", TankTypes.LIGHT, 44, 31, 31),
	_131_1("131-1", TankTypes.LIGHT, 45, 30, 20),
	_132B("132B", TankTypes.LIGHT, 45, 35, 20),
	WZ_132("WZ-132", TankTypes.LIGHT, 55,35, 20),
	/** Chinese Medium Turrets */
	TYPE_T_34M("Type T-34M", TankTypes.MEDIUM, 52, 52, 45),
	TYPE_58("Type 58", TankTypes.MEDIUM, 90, 75, 52),
	T_34_1_MODEL_2("T-34_1 model 2", TankTypes.MEDIUM, 170, 120, 60),
	T_34_2_MODEL_2("T-34-2 model 2", TankTypes.MEDIUM, 180, 120, 60),
	WZ_120_1("WZ-120-1", TankTypes.MEDIUM, 220, 160, 60),
	_121("121", TankTypes.MEDIUM, 240, 130, 60),
	/** Chinese Heavy Turrets */
	IS_2_LATE("IS-2 late", TankTypes.HEAVY, 100, 90, 90),
	T_10("T-10", TankTypes.HEAVY, 201, 148, 50),
	WZ_111("WZ-111", TankTypes.HEAVY, 230, 120, 60),
	_113("113", TankTypes.HEAVY, 240, 160, 80);
	
	double armor;
	String name;
	ItemStack helmet;
	
	private Turrets(String name, TankTypes type, double front, double side, double rear)
	{
		this.name = name;
		this.armor = (front + side + rear) / 3;
		parseHelmet(type);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ItemStack getHelmet()
	{
		return helmet;
	}
	
	private void parseHelmet(TankTypes type)
	{	
		Material material = Material.AIR;
		if (type == TankTypes.LIGHT)
			material = Material.LEATHER_HELMET;
		else if (type == TankTypes.MEDIUM)
			material = Material.IRON_HELMET;
		else if (type == TankTypes.HEAVY)
			material = Material.DIAMOND_HELMET;
		else if (type == TankTypes.TD)
			material = Material.CHAINMAIL_HELMET;
		else if (type == TankTypes.ARTY)
			material = Material.GOLD_HELMET;
		
		helmet = new ItemStack(material);
		ItemMeta meta = helmet.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Turret"));
		helmet.setItemMeta(meta);
	}
}
