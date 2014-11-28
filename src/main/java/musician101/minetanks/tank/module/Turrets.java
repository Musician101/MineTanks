package musician101.minetanks.tank.module;

import java.util.Arrays;

import musician101.minetanks.tank.Tanks.TankTypes;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColors;

public enum Turrets
{
	/** Tanks w/o Turrets */
	NO_TURRET_TD("None", TankTypes.TD, 0, 0, 0),
	NO_TURRET_ARTY("None", TankTypes.ARTY, 0, 0, 0),
	/** Chinese Light Turrets */
	FT_18("FT-18", TankTypes.LIGHT, 22, 22, 16),
	T_26_MODEL_1936("T-26 model 1936", TankTypes.LIGHT, 30, 15, 15),
	TYPE_97_KAI("Type 97-Kai", TankTypes.LIGHT, 25, 25, 25),
	M5A1_C("M5A1", TankTypes.LIGHT, 44, 31, 31),
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
	_113("113", TankTypes.HEAVY, 240, 160, 80),
	/** French Light Turrets */
	FL_10("FL 10 Type D", TankTypes.LIGHT, 40, 20, 20),
	/** French Medium Turrets */
	BAT_CHAT_25_T("Batignolles-Chatillon 25 t", TankTypes.MEDIUM, 50, 30, 15),
	/** French Heavy Turrets */
	AMX_50_B("AMX 50 B", TankTypes.HEAVY, 100, 50, 30),
	/** German Light Turrets */
	RU_251("Spahpanzer Ru 251 Drehturm 360", TankTypes.LIGHT, 50, 30, 30),
	/** German Medium Turrets */
	E_50_M("E 50 Ausf. M", TankTypes.MEDIUM, 185, 80, 80),
	LEOPARD_1("Leopard 1", TankTypes.MEDIUM, 52, 60, 60),
	/** German Heavy Turrets */
	E_100("E 100", TankTypes.HEAVY, 250, 150, 150),
	MAUS("MAUS", TankTypes.HEAVY, 240, 210, 210),
	VK_7201("VK 72.01 (K)", TankTypes.HEAVY, 200, 160, 120),
	/** German TD Turrets */
	WAFFEN_E_100("Waffentrager auf E 100", TankTypes.TD, 20, 10, 8),
	/** Japanese Medium Turrets */
	STB_1("STB-1", TankTypes.MEDIUM, 132, 132, 37),
	/** British Medium Turrets */
	FV4202(TankTypes.MEDIUM, 196, 196, 35),
	/** British Heavy Turrets */
	FV215(TankTypes.HEAVY, 254, 152.4, 101.6),
	/** American Light Turrets */
	XM551("XM551 test bed", TankTypes.LIGHT, 38.1, 15.9, 15.9),
	/** American Medium Turrets */
	T87(TankTypes.MEDIUM, 177.8, 76.2, 50.8),
	M60(TankTypes.MEDIUM, 177.8, 76.2, 50.8),
	/** American Heavy Turrets */
	T169(TankTypes.HEAVY, 127, 137, 50.8),
	T53(TankTypes.HEAVY, 203, 127, 70),
	/** American TD Turrets */
	T110E4(TankTypes.TD, 203, 152, 152),
	/** Russian Light Turrets */
	T_54_LIGHT(TankTypes.LIGHT, 160, 120, 60),
	/** Russian Medium Turrets */
	OBJECT_140("Object 140", TankTypes.MEDIUM, 240, 187, 65),
	OBJECT_430("Object 430", TankTypes.MEDIUM, 240, 185, 63),
	T_62A("T-62A", TankTypes.MEDIUM, 240, 161, 65),
	/** Russian Heavy Turrets */
	IS_4M("IS-4M", TankTypes.HEAVY, 250, 200, 170),
	IS_7("IS-7", TankTypes.HEAVY, 240, 185, 94);
	
	double armor;
	String name;
	ItemStack helmet;
	
	private Turrets(TankTypes type, double front, double side, double rear)
	{
		this("", type, front, side, rear);
	}
	
	private Turrets(String name, TankTypes type, double front, double side, double rear)
	{
		if (name.equals(""))
			name = toString();
		
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
	
	public double getArmor()
	{
		return armor;
	}
	
	@SuppressWarnings("serial")
	private void parseHelmet(TankTypes type)
	{	
		final ItemType iType;
		if (type == TankTypes.LIGHT)
			iType = ItemTypes.LEATHER_HELMET;
		else if (type == TankTypes.MEDIUM)
			iType = ItemTypes.IRON_HELMET;
		else if (type == TankTypes.HEAVY)
			iType = ItemTypes.DIAMOND_HELMET;
		else if (type == TankTypes.TD)
			iType = ItemTypes.CHAINMAIL_HELMET;
		else if (type == TankTypes.ARTY)
			iType = ItemTypes.GOLDEN_HELMET;
		
		helmet = new ItemStack()
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
		
		ItemMeta meta = helmet.getItemMeta();
		meta.setDisplayName(TextColors.GREEN + this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.setLore(Arrays.asList("Your Turret"));
		helmet.setItemMeta(meta);
	}
}
