package musician101.minetanks.tankinfo;

import musician101.minetanks.tankinfo.modules.Cannons;
import musician101.minetanks.tankinfo.modules.Engines;
import musician101.minetanks.tankinfo.modules.Radios;
import musician101.minetanks.tankinfo.modules.Tracks;
import musician101.minetanks.tankinfo.modules.Turrets;
import musician101.minetanks.util.MTUtils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum Tanks implements IInfo
{
	/** China Lights */
	WZ_132(0, "WZ-132", TankTypes.LIGHT, 950, 24.31, 50, 35, 20, 64, new Cannons("100 mm 60-100T", 43, 7.06), new Engines("8V135", TankTypes.LIGHT), new Radios("A-220A", TankTypes.LIGHT), Tracks.WZ_132, Turrets.WZ_132, new String[]{"Best agility and mobility."}),
	/** China Mediums */
	_121(1, "121", TankTypes.MEDIUM, 1950, 39, 120, 80, 60, 56, new Cannons("122 mm 60-122T", 40, 6.25), new Engines("12150L7", TankTypes.MEDIUM), new Radios("A-220B", TankTypes.MEDIUM), Tracks._121, Turrets._121, new String[]{"Very good high damage per shell and minute."}),
	/** China Heavies */
	_113(2, "113", TankTypes.HEAVY, 2300, 45, 120, 90, 70, 50, new Cannons("122 mm 60-122T", 34, 5.5), new Engines("V-2-54SC", TankTypes.HEAVY), new Radios("A-220B", TankTypes.HEAVY), Tracks._113, Turrets._113, new String[]{"Good mobility and hight top speed."}),
	/** France Lights */
	AMX_13_90(3, "AMX 13 90", TankTypes.LIGHT, 1100, 14.9, 40, 20, 15, 64, new Cannons("90 mm F3", 36, 6.47), new Engines("Detroit Diesel 6V53T-S", TankTypes.LIGHT), new Radios("SCR 528", TankTypes.LIGHT), Tracks.AMX_13_TYPE_2D, Turrets.FL_10, new String[]{"High speed.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** French Mediums */
	BAT_CHAT_25_T(4, "Bat.-Châtillon 25 t", TankTypes.MEDIUM, 1800, 25.28, 60, 40, 30, 65, new Cannons("105 mm mle. 57 (D. 1504)", 30, 5.89), new Engines("Hispano-Suiza HS 110", TankTypes.MEDIUM), new Radios("SCR 528", TankTypes.MEDIUM), Tracks.BAT_CHAT_25_T, Turrets.BAT_CHAT_25_T, new String[]{"High mibility.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** French Heavies */
	AMX_50_B(5, "AMX 50 B", TankTypes.HEAVY, 2100, 62.21, 170, 30, 30, 65, new Cannons("120 mm SA46", 56, 5), new Engines("Maybach HL 295 F", TankTypes.HEAVY), new Radios("SCR 619", TankTypes.HEAVY), Tracks.AMX_50_B, Turrets.AMX_50_B, new String[]{"Excellent speed.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** French TDs */
	AMX_50_FOCH_155(6, "AMX 50 Foch (155)", TankTypes.TD, 1850, 57.6, 180, 40, 40, 50, new Cannons("Canon de 155 mm", 36, 2.45), new Engines("Saurer", TankTypes.TD), new Radios("SCR 619", TankTypes.TD), Tracks.AMX_50_FOCH_155, Turrets.NO_TURRET_TD, new String[]{"High damage per shot.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** French Arty */
	BAT_CHAT_155_58(7, "Bat.-Châtillon 155 58", TankTypes.ARTY, 490, 35, 50, 30, 20, 62, new Cannons("180 mm B-1-P", 18, 1.71), new Engines("Hispano-Suiza HS 110", TankTypes.ARTY), new Radios("SCR 528", TankTypes.ARTY), Tracks.OBJECT_268, Turrets.NO_TURRET_ARTY, new String[]{"Very mobile.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** German Lights */
	AUFKL_PANTHER(8, "Aufklärungspanzer Panther", TankTypes.LIGHT, 850, 41.62, 85, 50, 45, 60, new Cannons("7,5/5,5 cm Waffe 0725", 81, 17.65), new Engines("Maybach HL 234 TRM P30", TankTypes.LIGHT), new Radios("FuG 12", TankTypes.LIGHT), Tracks.AUFKL_PANTHER_V, Turrets.VK_2801, new String[]{"Thick armor for a light tank.", "Good for ramming."}),
	/** German Mediums */
	E_50_M(9, "E 50 Ausf. M", TankTypes.MEDIUM, 2050, 62.31, 150, 80, 80, 60, new Cannons("10,5 cm Kw.K. L/52 Ausf. K", 48, 6.25), new Engines("Maybach HL 235", TankTypes.MEDIUM), new Radios("10WSc", TankTypes.MEDIUM), Tracks.E_50_M, Turrets.E_50_M, new String[]{"Extraordinary gun.", "Excellent for ramming."}),
	LEOPARD_1(10, "Leopard 1", TankTypes.MEDIUM, 1950, 40, 70, 35, 25, 65, new Cannons("10,5 cm Bodkanone L7A3", 60, 6.9), new Engines("MTU MB 838 CaM 500A", TankTypes.MEDIUM), new Radios("SEM 25A", TankTypes.MEDIUM), Tracks.LEOPARD_1, Turrets.LEOPARD_1, new String[]{"Great speed and mobility."}),
	/** German Heavies */
	E_100(11, "E 100", TankTypes.HEAVY, 2700, 129.86, 200, 120, 150, 30, new Cannons("15 cm Kw.K. L/38", 50, 3), new Engines("Maybach Neues Projekt", TankTypes.HEAVY), new Radios("10WSc", TankTypes.HEAVY), Tracks.E_100, Turrets.E_100, new String[]{"Strong armor all around.", "Second highest hit point pool of any tank."}),
	MAUS(12, "Maus", TankTypes.HEAVY, 3000, 188.98, 200, 185, 160, 20, new Cannons("12,8 cm Kw.K. 44 L/55", 68, 4.03), new Engines("DB 603 A2", TankTypes.HEAVY), new Radios("10WSc", TankTypes.HEAVY), Tracks.MAUS, Turrets.MAUS, new String[]{"Excellent armor.", "Highest hit point pool of any tank."}),
	VK_7201(13, "VK 72.01 (K)", TankTypes.HEAVY, 2500, 120, 200, 160, 120, 43, new Cannons("15 cm Kw.K L/38", 24, 2.73), new Engines("MB 517", TankTypes.HEAVY), new Radios("10WSc", TankTypes.HEAVY), Tracks.VK_7201, Turrets.VK_7201, new String[]{"Good ramming tank.", "High damage per shot."}),
	/** German TDs */
	JAGDPZ_E_100(14, "Jagdpanzer E 100", TankTypes.TD, 2200, 133.94, 200, 120, 150, 30, new Cannons("17 cm Pak", 24, 2.33), new Engines("Maybach Neues Projekt", TankTypes.TD), new Radios("10WSc", TankTypes.TD), Tracks.JAGDPZ_E_100, Turrets.NO_TURRET_TD, new String[]{"Incredible single shot power.", "Very strong all around armor with large hit point pool."}),
	WAFF_E_100(15, "Waffenträger auf E 100", TankTypes.TD, 2000, 97.8, 200, 120, 150, 40, new Cannons("15 cm Pak L/38", 60, 4.07), new Engines("Maybach Neues Projekt", TankTypes.TD), new Radios("10WSc", TankTypes.TD), Tracks.WAFF_E_100, Turrets.NO_TURRET_TD, new String[]{"Large health pool for a TD.", "Cannon has a clip for quick damage, but a long base reload."}),
	/** German Arty */
	GW_E_100(16, "G.W. E 100", TankTypes.ARTY, 550, 87, 80, 50, 40, 40, new Cannons("21 cm Mörser 18/2", 30, 1.41), new Engines("Maybach HL 234 TRM P45", TankTypes.ARTY), new Radios("10WSc", TankTypes.ARTY), Tracks.GW_E_100, Turrets.NO_TURRET_ARTY, new String[]{"Immune to ramming from most medium tanks.", "Better damage per minute than the T92."}),
	/** Japanese Mediums */
	STB_1(17, "STB-1", TankTypes.MEDIUM, 1950, 37.4, 110, 35, 25, 53, new Cannons("105 cm Rifled Gun", 50, 7.5), new Engines("Mitsubishi 10ZF21WT", TankTypes.MEDIUM), new Radios("Type 67", TankTypes.MEDIUM), Tracks.STB_1, Turrets.STB_1, new String[]{"Highest damage per minute out of all the mediums.", "Reasonably mobile."}),
	/** British Mediums */
	FV4202(18, "FV4202", TankTypes.MEDIUM, 1950, 40, 121, 51, 32, 40, new Cannons("105 mm Royal Ornance L7A1", 72, 6.98), new Engines("Rolls-Royce Meteor M120A", TankTypes.MEDIUM), new Radios("SR C45", TankTypes.MEDIUM), Tracks.FV4202, Turrets.FV4202, new String[]{"Very good mobility."}),
	/** British Heavies */
	FV215B(19, "FV215b", TankTypes.HEAVY, 2500, 70, 152.4, 101.6, 76.2, 34, new Cannons("120 mm Gun L1A1", 35, 6.9), new Engines("Rolls-Royce Griffon", TankTypes.HEAVY), new Radios("SR C45", TankTypes.HEAVY), Tracks.FV215, Turrets.FV215, new String[]{"High damage per minute.", "Fastest rate of fire among the heavies (exlcuding tanks with clips)."}),
	/** British TDs */
	FV215B_183(20, "FV215b (183)", TankTypes.TD, 2000, 60, 152.4, 50.8, 76.2, 34, new Cannons("183 mm L4", 12, 2), new Engines("Rolls-Royce Meteor Mk. 12", TankTypes.TD), new Radios("SR C45", TankTypes.TD), Tracks.FV215B, Turrets.NO_TURRET_TD, new String[]{"Unbelievable damage per shot.", "Nicknamed: The Death Star"}),
	/** British Arty */
	CONQUEROR_GC(21, "Conqueror Gun Carriage", TankTypes.ARTY, 530, 55, 152.4, 50.8, 76.2, 34, new Cannons("B.L. 9.2-in. Howitzer Mk. II", 24, 1.03), new Engines("Rolls-Royce Meteor M120", TankTypes.ARTY), new Radios("SR C45", TankTypes.ARTY), Tracks.CONQUEROR_GC, Turrets.NO_TURRET_ARTY, new String[]{"Very high damage.", "Beats the T92 in all aspects except reload time."}),
	/** American Lights */
	T71(22, "T71", TankTypes.LIGHT, 840, 16.96, 25.4, 22.2, 19.1, 64, new Cannons("76 mm Gun T185", 60, 12), new Engines("Continental AOI-628-2", TankTypes.LIGHT), new Radios("AN/GRC-7", TankTypes.LIGHT), Tracks.T71A1, Turrets.T71, new String[]{"Acceptable clip reload time.", "Excellent mobility."}),
	/** American Mediums */
	M48A1_PATTON(23, "M48A1 Patton", TankTypes.MEDIUM, 2000, 47.72, 110, 76.2, 35.1, 45, new Cannons("105 mm Gun M68", 57, 6.82), new Engines("Continental AV-1790-7B", TankTypes.MEDIUM), new Radios("AN/VRC-7", TankTypes.MEDIUM), Tracks.T97E2_M, Turrets.T87, new String[]{"Highly maneuverable.", "Good damage."}),
	M60(24, "M60", TankTypes.MEDIUM, 2000, 46.27, 93.2, 73.7, 25.4, 48, new Cannons("105 mm Gun M68", 57, 6.82), new Engines("Continental AVDS-1790-2", TankTypes.MEDIUM), new Radios("AN/VRC-12", TankTypes.MEDIUM), Tracks.M60, Turrets.M60, new String[]{"Great mobility and rate of fire."}),
	/** American Heavies */
	T57_HT(25, "T57 Heavy Tank", TankTypes.HEAVY, 2250, 54.43, 203, 50.8, 38.1, 35, new Cannons("120 mm Gun T179", 36, 7.74), new Engines("Continental AV-1790-5C", TankTypes.HEAVY), new Radios("AN/VRC-3", TankTypes.HEAVY), Tracks.T97, Turrets.T169, new String[]{"Highest damage per minute of all heavy tanks.", "Cannon has a clip for quick damage, but a long base reload."}),
	T110E5(26, "T110E5", TankTypes.HEAVY, 2200, 53.76, 254, 76, 38, 37, new Cannons("120 mm Gun M58", 42, 6), new Engines("Continental AOI-1490", TankTypes.HEAVY), new Radios("AN/VRC-3", TankTypes.HEAVY), Tracks.T110E27, Turrets.T53, new String[]{"Agile compared to other heavy tanks.", "Good damage output."}),
	/** American TDs */
	T110E3(27, "T110E3", TankTypes.TD, 2050, 74.73, 305, 76, 38, 30, new Cannons("155 mm AT Gun T7E2", 27, 3.53), new Engines("Continental AOI-1490", TankTypes.TD), new Radios("AN/VRC-3", TankTypes.TD), Tracks.T110E3, Turrets.NO_TURRET_TD, new String[]{"Some of the toughest armor.", "Powerful gun."}),
	T110E4(28, "T110E4", TankTypes.TD, 2000, 64.03, 254, 76, 38, 335, new Cannons("155 mm AT Gun T7E2", 27, 3.08), new Engines("Continental AOI-1490", TankTypes.TD), new Radios("AN/VRC-3", TankTypes.TD), Tracks.T110E4, Turrets.T110E4, new String[]{"Decent mobility.", "Acceptable frontal armor."}),
	/** American Arty */
	T92(29, "T92", TankTypes.ARTY, 500, 57.84, 25.4, 25.4, 21.7, 32, new Cannons("240 mm Howitzer M1", 16, 1.13), new Engines("Ford GAF T92", TankTypes.ARTY), new Radios("SCR 528A", TankTypes.ARTY), Tracks.T92_T80E1, Turrets.NO_TURRET_ARTY, new String[]{"Most powerful gun in the game with the ptoential of destroying full-health tanks.", "Huge splash damage."}),
	/** Russian Mediums */
	OBJECT_140(30, "Object 140", TankTypes.MEDIUM, 1900, 36, 100, 80, 50, 40, new Cannons("100 mm U-8TS", 50, 9.09), new Engines("TD-12", TankTypes.MEDIUM), new Radios("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_140, Turrets.OBJECT_140, new String[]{"Highest rate of fire out of all mediums.", "Fast and agile."}),
	OBJECT_430(31, "Object 430", TankTypes.MEDIUM, 1900, 35.3, 120, 80, 40, 55, new Cannons("100 mm U-8TS", 50, 8.7), new Engines("5TD", TankTypes.MEDIUM), new Radios("R-113M", TankTypes.MEDIUM), Tracks.OBJECT_430, Turrets.OBJECT_430, new String[]{"Great damage per minute.", "Great for skirmishing."}),
	T_62A(32, "T-62A", TankTypes.MEDIUM, 1950, 37, 102, 75, 45, 50, new Cannons("100 mm U-8TS", 50, 9.09), new Engines("V-55", TankTypes.MEDIUM), new Radios("R-123", TankTypes.MEDIUM), Tracks.T_62A, Turrets.T_62A, new String[]{"Very high damage per minute.", "Very strong turret armor."}),
	/** Russian Heavies */
	IS_4(33, "IS-4", TankTypes.HEAVY, 2500, 60.83, 140, 160, 100, 43, new Cannons("122 mm M62-T2", 30, 5), new Engines("V-12", TankTypes.HEAVY), new Radios("10RK-26", TankTypes.HEAVY), Tracks.IS_4M, Turrets.IS_4M, new String[]{"High health pool.", "Thick armor."}),
	IS_7(34, "IS-7", TankTypes.HEAVY, 2150, 68.19, 150, 150, 100, 60, new Cannons("130 mm S-70", 30, 4.38), new Engines("M-50T", TankTypes.HEAVY), new Radios("10RK-26", TankTypes.HEAVY), Tracks.IS_7, Turrets.IS_7, new String[]{"Excellent armor.", "High top speed."}),
	/** Russian TDs */
	OBJECT_263(35, "Object 263", TankTypes.TD, 1900, 60.01, 250, 80, 50, 55, new Cannons("130 mm S-70A", 42, 5.61), new Engines("M-50T", TankTypes.TD), new Radios("10RK-26", TankTypes.TD), Tracks.OBJECT_263, Turrets.NO_TURRET_TD, new String[]{"Better damage per minute than the Object 268.", "Great mobility."}),
	OBJECT_268(36, "Object 268", TankTypes.TD, 1950, 50.97, 187, 100, 50, 48, new Cannons("152 mm M64", 35, 3.64), new Engines("V-16FM", TankTypes.TD), new Radios("R-113M", TankTypes.TD), Tracks.OBJECT_268, Turrets.NO_TURRET_TD, new String[]{"Etremely good damager per minute.", "Good mobility."}),
	/** Russian Arty */
	OBJECT_261(37, "Object 268", TankTypes.ARTY, 510, 59.1, 75, 50, 30, 50, new Cannons("180 mm B-1-P", 18, 1.71), new Engines("M-50T", TankTypes.ARTY), new Radios("10RK-26", TankTypes.ARTY), Tracks.OBJECT_268, Turrets.NO_TURRET_ARTY, new String[]{"Fairly mobile."});
	
	int id, health, speed;
	String name;
	ItemStack[] wornArmor;
	Inventory weapons;
	TankTypes type;
	String[] description;
	double reloadTime, weight;
	
	private Tanks(int id, String name, TankTypes type, int health, double weight, double front, double side, double rear, int speed, Cannons cannon, Engines engine, Radios radio, Tracks tracks, Turrets turret, String[] description)
	{
		this.id = id;
		this.name = name;
		this.health = health;
		this.weight = weight;
		this.wornArmor = MTUtils.parseArmor(engine, radio, tracks, turret, MTUtils.calculateArmorValue(front, side, rear), speed);
		this.weapons = MTUtils.parseWeapons(cannon);
		this.reloadTime = cannon.reloadTime();
		this.type = type;
		this.description = description;
	}
	
	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getMenuSize()
	{
		int rows = 0;
		while (rows * 9 < values().length)
			rows++;
		
		return rows * 9;
	}
	
	public ItemStack[] getArmor()
	{
		return wornArmor;
	}
	
	public Inventory getWeapons()
	{
		return weapons;
	}
	
	public PotionEffect getSpeed()
	{
		return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, speed);
	}
	
	public double reloadTime()
	{
		return reloadTime;
	}
	
	public TankTypes getType()
	{
		return type;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public String[] getDescription()
	{
		return description;
	}
	
	public int getHealth()
	{
		return health;
	}
}
