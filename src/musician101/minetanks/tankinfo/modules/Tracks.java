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
	WZ_132("WZ-132", TankTypes.LIGHT),
	/** Chinese Medium Tracks */
	_121("121", TankTypes.MEDIUM),
	/** Chinese Heavy Tracks */
	_113("113", TankTypes.HEAVY),
	/** French Light Tracks */
	AMX_13_TYPE_2D("AMX 13 Type 2D", TankTypes.LIGHT),
	/** French Medium Tracks */
	BAT_CHAT_25_T("Batignolles-Ch�tillon 25 t", TankTypes.MEDIUM),
	/** French Heavy Tracks */
	AMX_50_B("AMX 50 B", TankTypes.HEAVY),
	/** French TD Tracks */
	AMX_50_FOCH_155("AMX 50 Foch (155)", TankTypes.TD),
	/** French SPG Tracks */
	BAT_CHAT_155_58("Batignolles-Ch�tillon 155 mle. 58", TankTypes.ARTY),
	/** German Light Tracks */
	AUFKL_PANTHER_V("Aufkl. Panther verst�rkteketten", TankTypes.LIGHT),
	/** German Medium Tracks */
	E_50_M("E 50 Ausf. M", TankTypes.MEDIUM),
	LEOPARD_1("Leopard 1", TankTypes.MEDIUM),
	/** German Heavy Tanks */
	E_100("E 100", TankTypes.HEAVY),
	MAUS("Maus", TankTypes.HEAVY),
	VK_7201("VK 72.01 (k)", TankTypes.HEAVY),
	/** German TD Tracks */
	JAGDPZ_E_100("Jagdpanzer E 100", TankTypes.TD),
	WAFF_E_100("Waffentr�ger auf E 100", TankTypes.TD),
	/** German Arty Tracks */
	GW_E_100("G.W. E 100", TankTypes.ARTY),
	/** Japanese Medium Tracks */
	STB_1("STB-1", TankTypes.MEDIUM),
	/** British Medium Tracks */
	FV4202(TankTypes.MEDIUM),
	/** British Heavy Tracks */
	FV215(TankTypes.HEAVY),
	/** British TD Tracks */
	FV215B("FV215b", TankTypes.TD),
	/** British SPG Tracks */
	CONQUEROR_GC("Conqueror Gun Carriage", TankTypes.ARTY),
	/** American Light Tracks */
	T71A1(TankTypes.LIGHT),
	/** American Medium Tracks */
	T97E2_M("T97E2", TankTypes.MEDIUM),
	M60(TankTypes.MEDIUM),
	/** American Heavy Tracks */
	T110E27(TankTypes.HEAVY),
	T97(TankTypes.HEAVY),
	/** American TD Tracks */
	T110E4(TankTypes.TD),
	T110E3(TankTypes.TD),
	/** American SPG Tracks */
	T92_T80E1("T92 T80E1", TankTypes.ARTY),
	/** Russian Medium Tracks */
	OBJECT_140("Object 140", TankTypes.MEDIUM),
	OBJECT_430("Object 430", TankTypes.MEDIUM),
	T_62A("T-62A", TankTypes.MEDIUM),
	/** Russian Heavy Tracks */
	IS_4M("IS-4M", TankTypes.HEAVY),
	IS_7("IS-7", TankTypes.HEAVY),
	/** Russian TD Tracks */
	OBJECT_263("Object 263", TankTypes.HEAVY),
	OBJECT_268("Object 268", TankTypes.HEAVY),
	/** Russian SPG Tracks */
	OBJECT_261("Object 261", TankTypes.HEAVY),;
	
	String name;
	ItemStack tracks;
	
	private Tracks(TankTypes type)
	{
		this("", type);
	}
	
	private Tracks(String name, TankTypes type)
	{
		if (name.equals(""))
			name = this.toString();
		
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
