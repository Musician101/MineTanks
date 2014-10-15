package musician101.minetanks.tank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import musician101.minetanks.tank.module.Cannon;
import musician101.minetanks.tank.module.Engine;
import musician101.minetanks.tank.module.Radio;
import musician101.minetanks.tank.module.Tracks;
import musician101.minetanks.tank.module.Turrets;
import musician101.minetanks.tank.module.Cannon.CannonTypes;
import musician101.minetanks.util.MTUtils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum Tanks
{
	/** China Lights */
	WZ_132("WZ-132", TankTypes.LIGHT, 950, 50, 35, 20, 64, new Cannon("100 mm 60-100T", 43, 8.5), new Engine("8V135", TankTypes.LIGHT), new Radio("A-220A", TankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132, Arrays.asList("Type: Light", "Best agility and mobility.")),
	/** China Mediums */
	_121("121", TankTypes.MEDIUM, 1950, 120, 80, 60, 56, new Cannon("122 mm 60-122T", 40, 9.6), new Engine("12150L7", TankTypes.MEDIUM), new Radio("A-220B", TankTypes.MEDIUM), Tracks._121, Turrets._121, Arrays.asList("Type: Medium", "Very good high damage per shell and minute.")),
	/** China Heavies */
	_113("113", TankTypes.HEAVY, 2300, 120, 90, 70, 50, new Cannon("122 mm 60-122T", 34, 10.9), new Engine("V-2-54SC", TankTypes.HEAVY), new Radio("A-220B", TankTypes.HEAVY), Tracks._113, Turrets._113, Arrays.asList("Type: Heavy", "Good mobility and hight top speed.")),
	/** France Lights */
	AMX_13_90("AMX 13 90", TankTypes.LIGHT, 1100, 40, 20, 15, 64, new Cannon("90 mm F3", CannonTypes.AUTO_LOADER, 36, 42, 2.73, 6), new Engine("Detroit Diesel 6V53T-S", TankTypes.LIGHT), new Radio("SCR 528", TankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10, Arrays.asList("Type: Light", "High speed.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** French Mediums */
	BAT_CHAT_25_T("Bat.-Chatillon 25 t", TankTypes.MEDIUM, 1800, 60, 40, 30, 65, new Cannon("105 mm mle. 57 (D. 1504)", CannonTypes.AUTO_LOADER, 30, 40, 2.73, 5), new Engine("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radio("SCR 528", TankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T, Arrays.asList("Type: Medium", "High mibility.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** French Heavies */
	AMX_50_B("AMX 50 B", TankTypes.HEAVY, 2100, 170, 30, 30, 65, new Cannon("120 mm SA46", CannonTypes.AUTO_LOADER, 56, 30, 2.5, 4), new Engine("Maybach HL 295 F", TankTypes.HEAVY), new Radio("SCR 619", TankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B, Arrays.asList("Type: Heavy", "Excellent speed.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** French TDs */
	AMX_50_FOCH_155("AMX 50 Foch (155)", TankTypes.TD, 1850, 180, 40, 40, 50, new Cannon("Canon de 155 mm", CannonTypes.AUTO_LOADER, 30, 50, 5, 3), new Engine("Saurer", TankTypes.TD), new Radio("SCR 619", TankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "High damage per shot.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** French Arty */
	BAT_CHAT_155_58("Bat.-Chatillon 155 58", TankTypes.ARTY, 490, 50, 30, 20, 62, new Cannon("180 mm B-1-P", CannonTypes.AUTO_LOADER, 36, 80, 6, 4), new Engine("Hispano-Suiza HS 110", TankTypes.ARTY), new Radio("SCR 528", TankTypes.ARTY), Tracks.BAT_CHAT_155_58, Turrets.NO_TURRET_ARTY, Arrays.asList("Type: SPG", "Very mobile.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** German Lights */
	RU_251("Spahpanzer Ru 251", TankTypes.LIGHT, 1200, 25, 20, 8, 80, new Cannon("Spahpanzer Ru 251 Drehturm 360", 41, 6.2), new Engine("MTU MB 837 A", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.RU_251_PT_II, Turrets.RU_251, Arrays.asList("Type: Light", "Fastest tank.", "Excellent damage per minute.", "Worst armor out of all the tanks.")),
	/** German Mediums */
	E_50_M("E 50 Ausf. M", TankTypes.MEDIUM, 2050, 150, 80, 80, 60, new Cannon("10,5 cm Kw.K. L/52 Ausf. K", 48, 9.6), new Engine("Maybach HL 235", TankTypes.MEDIUM), new Radio("10WSc", TankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M, Arrays.asList("Type: Medium", "Extraordinary gun.", "Excellent for ramming.")),
	LEOPARD_1("Leopard 1", TankTypes.MEDIUM, 1950, 70, 35, 25, 65, new Cannon("10,5 cm Bodkanone L7A3", 60, 8.7), new Engine("MTU MB 838 CaM 500A", TankTypes.MEDIUM), new Radio("SEM 25A", TankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1, Arrays.asList("Type: Medium", "Great speed and mobility.")),
	/** German Heavies */
	E_100("E 100", TankTypes.HEAVY, 2700, 200, 120, 150, 30, new Cannon("15 cm Kw.K. L/38", 50, 20), new Engine("Maybach Neues Projekt", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.E_100, Turrets.E_100, Arrays.asList("Type: Heavy", "Strong armor all around.", "Second highest hit point pool of any tank.")),
	MAUS("Maus", TankTypes.HEAVY, 3000, 200, 185, 160, 20, new Cannon("12,8 cm Kw.K. 44 L/55", 68, 14.9), new Engine("DB 603 A2", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS, Arrays.asList("Type: Heavy", "Excellent armor.", "Highest hit point pool of any tank.")),
	VK_7201("VK 72.01 (K)", TankTypes.HEAVY, 2500, 200, 160, 120, 43, new Cannon("15 cm Kw.K L/38", 24, 22), new Engine("MB 517", TankTypes.HEAVY), new Radio("10WSc", TankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201, Arrays.asList("Type: Heavy", "Good ramming tank.", "High damage per shot.")),
	/** German TDs */
	JAGDPZ_E_100("Jagdpanzer E 100", TankTypes.TD, 2200, 200, 120, 150, 30, new Cannon("17 cm Pak", 24, 25.7), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.JAGDPZ_E_100, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Incredible single shot power.", "Very strong all around armor with large hit point pool.")),
	WAFF_E_100("Waffentrager auf E 100", TankTypes.TD, 2000, 200, 120, 150, 40, new Cannon("15 cm Pak L/38", CannonTypes.AUTO_LOADER, 60, 50, 3, 4), new Engine("Maybach Neues Projekt", TankTypes.TD), new Radio("10WSc", TankTypes.TD), Tracks.WAFF_E_100, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Large health pool for a TD.", "Cannon has a clip for quick damage, but a long base reload.")),
	/** German Arty */
	GW_E_100("G.W. E 100", TankTypes.ARTY, 550, 80, 50, 40, 40, new Cannon("21 cm Morser 18/2", 30, 42.5), new Engine("Maybach HL 234 TRM P45", TankTypes.ARTY), new Radio("10WSc", TankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY, Arrays.asList("Type: SPG", "Immune to ramming from most medium tanks.", "Better damage per minute than the T92.")),
	/** Japanese Mediums */
	STB_1("STB-1", TankTypes.MEDIUM, 1950, 110, 35, 25, 53, new Cannon("105 cm Rifled Gun", 50, 8), new Engine("Mitsubishi 10ZF21WT", TankTypes.MEDIUM), new Radio("Type 67", TankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1, Arrays.asList("Type: Medium", "Highest damage per minute out of all the mediums.", "Reasonably mobile.")),
	/** British Mediums */
	FV4202("FV4202", TankTypes.MEDIUM, 1950, 121, 51, 32, 40, new Cannon("105 mm Royal Ornance L7A1", 72, 8.6), new Engine("Rolls-Royce Meteor M120A", TankTypes.MEDIUM), new Radio("SR C45", TankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202, Arrays.asList("Type: Medium", "Very good mobility.")),
	/** British Heavies */
	FV215B("FV215b", TankTypes.HEAVY, 2500, 152.4, 101.6, 76.2, 34, new Cannon("120 mm Gun L1A1", 35, 8.7), new Engine("Rolls-Royce Griffon", TankTypes.HEAVY), new Radio("SR C45", TankTypes.HEAVY), Tracks.FV215, Turrets.FV215, Arrays.asList("Type: Heavy", "High damage per minute.", "Fastest rate of fire among the heavies (exlcuding tanks with clips).")),
	/** British TDs */
	FV215B_183("FV215b (183)", TankTypes.TD, 2000, 152.4, 50.8, 76.2, 34, new Cannon("183 mm L4", 12, 30), new Engine("Rolls-Royce Meteor Mk. 12", TankTypes.TD), new Radio("SR C45", TankTypes.TD), Tracks.FV215B, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Unbelievable damage per shot.", "Nicknamed: The Death Star")),
	/** British Arty */
	CONQUEROR_GC("Conqueror Gun Carriage", TankTypes.ARTY, 530, 152.4, 50.8, 76.2, 34, new Cannon("B.L. 9.2-in. Howitzer Mk. II", 24, 58.1), new Engine("Rolls-Royce Meteor M120", TankTypes.ARTY), new Radio("SR C45", TankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY, Arrays.asList("Type: SPG", "Very high damage.", "Beats the T92 in all aspects except reload time.")),
	/** American Lights */
	T49("T49", TankTypes.LIGHT, 1000, 25.4, 25.4, 19.1, 72, new Cannon("152 mm Gun-Launcher XM81 (conventional)", 22, 23), new Engine("Continental AOI-865-5", TankTypes.LIGHT), new Radio("AN/GRC-7", TankTypes.LIGHT), Tracks.CONTINENTAL_AOSI_865_5, Turrets.XM551, Arrays.asList("Type: Light", "Acceptable clip reload time.", "Excellent mobility.")),
	/** American Mediums */
	M48A1_PATTON("M48A1 Patton", TankTypes.MEDIUM, 2000, 110, 76.2, 35.1, 45, new Cannon("105 mm Gun M68", 57, 8.8), new Engine("Continental AV-1790-7B", TankTypes.MEDIUM), new Radio("AN/VRC-7", TankTypes.MEDIUM), Tracks.T97E2_M, Turrets.T87, Arrays.asList("Type: Medium", "Highly maneuverable.", "Good damage.")),
	M60("M60", TankTypes.MEDIUM, 2000, 93.2, 73.7, 25.4, 48, new Cannon("105 mm Gun M68", 57, 8.8), new Engine("Continental AVDS-1790-2", TankTypes.MEDIUM), new Radio("AN/VRC-12", TankTypes.MEDIUM), Tracks.M60, Turrets.M60, Arrays.asList("Type: Medium", "Great mobility and rate of fire.")),
	/** American Heavies */
	T57_HT("T57 Heavy Tank", TankTypes.HEAVY, 2250, 203, 50.8, 38.1, 35, new Cannon("120 mm Gun T179", CannonTypes.AUTO_LOADER, 36, 25, 2, 4), new Engine("Continental AV-1790-5C", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T97, Turrets.T169, Arrays.asList("Type: Heavy", "Highest damage per minute of all heavy tanks.", "Cannon has a clip for quick damage, but a long base reload.")),
	T110E5("T110E5", TankTypes.HEAVY, 2200, 254, 76, 38, 37, new Cannon("120 mm Gun M58", 42, 10), new Engine("Continental AOI-1490", TankTypes.HEAVY), new Radio("AN/VRC-3", TankTypes.HEAVY), Tracks.T110E27, Turrets.T53, Arrays.asList("Type: Heavy", "Agile compared to other heavy tanks.", "Good damage output.")),
	/** American TDs */
	T110E3("T110E3", TankTypes.TD, 2050, 305, 76, 38, 30, new Cannon("155 mm AT Gun T7E2", 27, 17), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Some of the toughest armor.", "Powerful gun.")),
	T110E4("T110E4", TankTypes.TD, 2000, 254, 76, 38, 335, new Cannon("155 mm AT Gun T7E2", 27, 19.5), new Engine("Continental AOI-1490", TankTypes.TD), new Radio("AN/VRC-3", TankTypes.TD), Tracks.T110E4, Turrets.T110E4, Arrays.asList("Type: TD", "Decent mobility.", "Acceptable frontal armor.")),
	/** American Arty */
	T92("T92", TankTypes.ARTY, 500, 25.4, 25.4, 21.7, 32, new Cannon("240 mm Howitzer M1", 16, 53), new Engine("Ford GAF T92", TankTypes.ARTY), new Radio("SCR 528A", TankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY, Arrays.asList("Type: SPG", "Most powerful gun in the game.", "Huge splash damage.")),
	/** Russian Lights */
	T_54_LIGHT("T-54 lightweight", TankTypes.LIGHT, 1200, 80, 60, 45, 69, new Cannon("100 mm D10T mod. 1945", 34, 8.2), new Engine("V-14", TankTypes.LIGHT), new Radio("10RT-26", TankTypes.LIGHT), Tracks.T_54_ENHANCED_LIGHT, Turrets.T_54_LIGHT, Arrays.asList("Type: Light", "High speed and maneuverability.")),
	/** Russian Mediums */
	OBJECT_140("Object 140", TankTypes.MEDIUM, 1900, 100, 80, 50, 40, new Cannon("100 mm U-8TS", 50, 6.6), new Engine("TD-12", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140, Arrays.asList("Type: Medium", "Highest rate of fire out of all mediums.", "Fast and agile.")),
	OBJECT_430("Object 430", TankTypes.MEDIUM, 1900, 120, 80, 40, 55, new Cannon("100 mm U-8TS", 50, 6.9), new Engine("5TD", TankTypes.MEDIUM), new Radio("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430, Arrays.asList("Type: Medium", "Great damage per minute.", "Great for skirmishing.")),
	T_62A("T-62A", TankTypes.MEDIUM, 1950, 102, 75, 45, 50, new Cannon("100 mm U-8TS", 50, 6.6), new Engine("V-55", TankTypes.MEDIUM), new Radio("R-123", TankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A, Arrays.asList("Type: Medium", "Very high damage per minute.", "Very strong turret armor.")),
	/** Russian Heavies */
	IS_4("IS-4", TankTypes.HEAVY, 2500, 140, 160, 100, 43, new Cannon("122 mm M62-T2", 30, 12), new Engine("V-12", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M, Arrays.asList("Type: Heavy", "High health pool.", "Thick armor.")),
	IS_7("IS-7", TankTypes.HEAVY, 2150, 150, 150, 100, 60, new Cannon("130 mm S-70", 30, 13.7), new Engine("M-50T", TankTypes.HEAVY), new Radio("10RK-26", TankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7, Arrays.asList("Type: Heavy", "Excellent armor.", "High top speed.")),
	/** Russian TDs */
	OBJECT_263("Object 263", TankTypes.TD, 1900, 250, 80, 50, 55, new Cannon("130 mm S-70A", 42, 10.7), new Engine("M-50T", TankTypes.TD), new Radio("10RK-26", TankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Better damage per minute than the Object 268.", "Great mobility.")),
	OBJECT_268("Object 268", TankTypes.TD, 1950, 187, 100, 50, 48, new Cannon("152 mm M64", 35, 16.5), new Engine("V-16FM", TankTypes.TD), new Radio("R-113M", TankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD, Arrays.asList("Type: TD", "Extremely good damager per minute.", "Good mobility.")),
	/** Russian Arty */
	OBJECT_261("Object 261", TankTypes.ARTY, 510, 75, 50, 30, 50, new Cannon("180 mm B-1-P", 18, 35), new Engine("M-50T", TankTypes.ARTY), new Radio("10RK-26", TankTypes.ARTY), Tracks.OBJECT_261, Turrets.NO_TURRET_ARTY, Arrays.asList("Type: SPG", "Fairly mobile."));
	
	int id, health, speed, clipSize;
	String name;
	CannonTypes cannonType;
	ItemStack[] wornArmor;
	Inventory weapons;
	TankTypes type;
	List<String> description;
	double cycleTime, reloadTime;
	static Map<Tanks, Integer> ids = new HashMap<Tanks, Integer>();
	
	private Tanks(String name, TankTypes type, int health, double front, double side, double rear, int speed, Cannon cannon, Engine engine, Radio radio, Tracks tracks, Turrets turret, List<String> description)
	{
		this.name = name;
		this.health = health;
		this.wornArmor = MTUtils.parseArmor(engine, radio, tracks, turret, MTUtils.calculateArmorValue(front, side, rear), speed);
		this.weapons = MTUtils.parseWeapons(cannon);
		this.cannonType = cannon.getType();
		this.cycleTime = cannon.cycleTime();
		this.reloadTime = cannon.reloadTime();
		this.clipSize = cannon.getClipSize();
		this.type = type;
		this.description = description;
		this.speed = speed / 5;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ItemStack[] getArmor()
	{
		return wornArmor;
	}
	
	public Inventory getWeapons()
	{
		return weapons;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public PotionEffect getSpeedEffect()
	{
		int speed = 0;
		if (this.speed >= 1 && this.speed < 2)
			speed = 1;
		else if (this.speed >= 2 && this.speed < 3)
			speed = 2;
		else if (this.speed >= 3 && this.speed < 4)
			speed = 3;
		else if (this.speed >= 4 && this.speed < 5)
			speed = 4;
		else if (this.speed >= 5 && this.speed < 6)
			speed = 5;
		else if (this.speed >= 6 && this.speed < 7)
			speed = 6;
		else if (this.speed >= 7 && this.speed < 8)
			speed = 7;
		else if (this.speed >= 8 && this.speed < 9)
			speed = 8;
		else if (this.speed >= 9 && this.speed < 10)
			speed = 9;
		else if (this.speed >= 10)
			speed = 10;
		
		if (speed == 1)
			return new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 5);
		else if (speed == 2)
			return new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 4);
		else if (speed == 3)
			return new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3);
		else if (speed == 4)
			return new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2);
		else if (speed == 5)
			return new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1);
		else if (speed == 6)
			return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
		else if (speed == 7)
			return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2);
		else if (speed == 8)
			return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3);
		else if (speed == 9)
			return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4);
		
		return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 5);
	}
	
	public int cycleTime()
	{
		return ((Double) cycleTime).intValue();
	}
	
	public int reloadTime()
	{
		return ((Double) reloadTime).intValue();
	}
	
	public int getClipSize()
	{
		return clipSize;
	}
	
	public CannonTypes getCannonType()
	{
		return cannonType;
	}
	
	public TankTypes getType()
	{
		return type;
	}
	
	public List<String> getDescription()
	{
		return description;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getId()
	{
		return ids.get(this);
	}
	
	static 
	{
		int id = 0;
		for (Tanks tank : values())
		{
			ids.put(tank, id);
			id++;
			//TODO test putting id++ in the put method
		}
	}
	
	public static enum TankTypes
	{
		LIGHT(0, "Light", new ItemStack(Material.WOOD_SWORD, 1)),
		MEDIUM(1, "Medium", new ItemStack(Material.STONE_SWORD, 1)),
		HEAVY(2, "Heavy", new ItemStack(Material.IRON_SWORD, 1)),
		TD(3, "TD", new ItemStack(Material.GOLD_SWORD, 1)),
		ARTY(4, "SPG", new ItemStack(Material.DIAMOND_SWORD, 1));
		
		int id;
		String name;
		ItemStack icon;
		
		private TankTypes(int id, String name, ItemStack icon)
		{
			this.id = id;
			this.name = name;
			this.icon = icon;
		}
		
		public int getId()
		{
			return id;
		}
		
		public String getName()
		{
			return name;
		}
		
		public ItemStack getIcon()
		{
			return icon;
		}
	}
}
