package musician101.minetanks.tankinfo.modules;

import java.util.Arrays;

import musician101.minetanks.tankinfo.TankTypes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Tracks
{
	/** Chinese Light Tracks */
	M26_27("M26/27", TankTypes.LIGHT),
	T_26_2("T-26-2", TankTypes.LIGHT),
	TYPE_97_KAI("Type 97-Kai", TankTypes.LIGHT),
	VVSS_T55E1("VVSS T55E1", TankTypes.LIGHT),
	_131_MODEL_2("131 model 2", TankTypes.LIGHT),
	WZ_131_MODEL_2("WZ-131 model 2", TankTypes.LIGHT),
	WZ_132("WZ-132", TankTypes.LIGHT),
	/** Chinese Medium Tracks */
	TYPE_T_34M("Type T-34M", TankTypes.MEDIUM),
	TYPE_58("Type 58", TankTypes.MEDIUM),
	T_34_1_MODEL_2("T-34-1 model 2", TankTypes.MEDIUM),
	T_34_MODEL_2("T-34-2 model 2", TankTypes.MEDIUM),
	WZ_120_MODEL_3("WZ-120 model 3", TankTypes.MEDIUM),
	_121("121", TankTypes.MEDIUM),
	/** Chinese Heavy Tracks */
	IS_2_LATE("IS-2 late", TankTypes.HEAVY),
	_110_1("100-1", TankTypes.HEAVY),
	WZ_111_MODEL_4("WZ-111 model 4", TankTypes.HEAVY),
	_113("113", TankTypes.HEAVY),
	;
	
	String name;
	ItemStack tracks;
	
	private Tracks(String name, TankTypes type)
	{
		this.name = name;
		parseTracks(type);
	}
	
	public ItemStack getTracks()
	{
		return tracks;
	}
	
	private void parseTracks(TankTypes type)
	{
		Material material = Material.AIR;
		if (type == TankTypes.LIGHT)
			material = Material.LEATHER_BOOTS;
		else if (type == TankTypes.MEDIUM)
			material = Material.IRON_BOOTS;
		else if (type == TankTypes.HEAVY)
			material = Material.DIAMOND_BOOTS;
		else if (type == TankTypes.TD)
			material = Material.CHAINMAIL_BOOTS;
		else if (type == TankTypes.ARTY)
			material = Material.GOLD_BOOTS;
		
		tracks = new ItemStack(material);
		ItemMeta meta = tracks.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Tracks"));
		tracks.setItemMeta(meta);
	}
}
