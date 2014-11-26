package musician101.minetanks.tank.module;

import java.util.Arrays;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import musician101.minetanks.tank.Tanks.TankTypes;

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
	BAT_CHAT_25_T("Batignolles-Chatillon 25 t", TankTypes.MEDIUM),
	/** French Heavy Tracks */
	AMX_50_B("AMX 50 B", TankTypes.HEAVY),
	/** French TD Tracks */
	AMX_50_FOCH_155("AMX 50 Foch (155)", TankTypes.TD),
	/** French SPG Tracks */
	BAT_CHAT_155_58("Batignolles-Chatillon 155 mle. 58", TankTypes.ARTY),
	/** German Light Tracks */
	RU_251_PT_II("Spahpanzer Ru 251 PT II", TankTypes.LIGHT),
	/** German Medium Tracks */
	E_50_M("E 50 Ausf. M", TankTypes.MEDIUM),
	LEOPARD_1("Leopard 1", TankTypes.MEDIUM),
	/** German Heavy Tanks */
	E_100("E 100", TankTypes.HEAVY),
	MAUS("Maus", TankTypes.HEAVY),
	VK_7201("VK 72.01 (k)", TankTypes.HEAVY),
	/** German TD Tracks */
	JAGDPZ_E_100("Jagdpanzer E 100", TankTypes.TD),
	WAFF_E_100("Waffentrager auf E 100", TankTypes.TD),
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
	CONTINENTAL_AOSI_865_5("Continental AOSI-895-5", TankTypes.LIGHT),
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
	/** Russian Light Tracks */
	T_54_ENHANCED_LIGHT("T-54 enhanced lightweight version", TankTypes.LIGHT),
	/** Russian Medium Tracks */
	OBJECT_140("Object 140", TankTypes.MEDIUM),
	OBJECT_430("Object 430", TankTypes.MEDIUM),
	T_62A("T-62A", TankTypes.MEDIUM),
	/** Russian Heavy Tracks */
	IS_4M("IS-4M", TankTypes.HEAVY),
	IS_7("IS-7", TankTypes.HEAVY),
	/** Russian TD Tracks */
	OBJECT_263("Object 263", TankTypes.TD),
	OBJECT_268("Object 268", TankTypes.TD),
	/** Russian SPG Tracks */
	OBJECT_261("Object 261", TankTypes.ARTY);
	
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
	
	@SuppressWarnings("serial")
	private void parseTracks(TankTypes type)
	{
		boolean parseError = false;
		final ItemType iType;
		if (type == TankTypes.LIGHT)
			iType = ItemTypes.LEATHER_BOOTS;
		else if (type == TankTypes.MEDIUM)
			iType = ItemTypes.IRON_BOOTS;
		else if (type == TankTypes.HEAVY)
			iType = ItemTypes.DIAMOND_BOOTS;
		else if (type == TankTypes.TD)
			iType = ItemTypes.CHAINMAIL_BOOTS;
		else if (type == TankTypes.ARTY)
			iType = ItemTypes.GOLDEN_BOOTS;
		else
		{
			iType = ItemTypes.LEATHER_BOOTS;
			parseError = true;
		}
		
		tracks = new ItemStack()
		{
			@Override
			public int compareTo(ItemStack o)
			{
				return 0;
			}

			@Override
			public ItemType getItem()
			{
				return iType;
			}

			@Override
			public short getDamage()
			{
				return 0;
			}

			@Override
			public void setDamage(short damage)
			{
				//NOOP
			}

			@Override
			public int getQuantity()
			{
				return 1;
			}

			@Override
			public void setQuantity(int quantity) throws IllegalArgumentException
			{
				//NOOP
			}

			@Override
			public int getMaxStackQuantity()
			{
				return 0;
			}

			@Override
			public void setMaxStackQuantity(int quantity)
			{
				//NOOP
			}
		};
		
		//TODO Item metadata support missing
		ItemMeta meta = tracks.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Tracks"));
		tracks.setItemMeta(meta);
	}
}
