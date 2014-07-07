package musician101.minetanks.tankinfo.tanks;

import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;
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

public enum ChinaLight implements ITank
{
	RENAULT(0, "Renault NC-31", 16, 16, 16, 25, new Engines("RENAULT NC-2", TankTypes.LIGHT), new Cannons("37 mm KwK 36 L/46.5", 150, 26.09), Turrets.FT_18, Tracks.M26_27, new Radios("ER 52", TankTypes.LIGHT)),
	VICKERS(1, "Vickers Mk. E Type B", 12, 12, 12, 35, new Engines("Franklin", TankTypes.LIGHT), new Cannons("40 mm Pom Pom", 160, 24.3), Turrets.T_26_MODEL_1936, Tracks.T_26_2, new Radios("71-TK-3", TankTypes.LIGHT)),
	CHI_HA(2, "Type 2597 Chi-Ha", 25, 25, 20, 40, new Engines("Type 100 V-12 A", TankTypes.LIGHT), new Cannons("47 mm Gun Type 1", 104, 20), Turrets.TYPE_97_KAI, Tracks.TYPE_97_KAI, new Radios("Type 3 Otsu", TankTypes.LIGHT)),
	M5(3, "M5A1 Stuart", 28, 28, 25, 64, new Engines("Continental R-975-C1", TankTypes.LIGHT), new Cannons("47 mm Gun Type 1", 73, 24), Turrets.M5A1, Tracks.VVSS_T55E1, new Radios("SCR 506", TankTypes.LIGHT)),
	_59_16(4, "59-16", 30, 20, 20, 60, new Engines("12150L-3", TankTypes.LIGHT), new Cannons("76 mm 54-76TG (autoloader)", 45, 19.57), Turrets._131_1, Tracks._131_MODEL_2, new Radios("A-220A", TankTypes.LIGHT)),
	WZ_131(5, "WZ-131", 35, 20, 20, 60, new Engines("12150L", TankTypes.LIGHT), new Cannons("100 mm 59-100T", 43, 6.45), Turrets._132B, Tracks.WZ_131_MODEL_2, new Radios("A-220A", TankTypes.LIGHT)),
	WZ_132(6, "WZ-132", 50, 25, 20, 64, new Engines("8V135", TankTypes.LIGHT), new Cannons("100 mm 60-100T", 43, 7.06), Turrets.WZ_132, Tracks.WZ_132, new Radios("A-220A", TankTypes.LIGHT));
	
	int id;
	int speed;
	String name;
	ItemStack[] wornArmor;
	Inventory weapons;
	double reloadTime;
	
	private ChinaLight(int id, String name, double front, double side, double rear, int speed, Engines engine, Cannons cannon, Turrets turret, Tracks tracks, Radios radio)
	{
		this.id = id;
		this.name = name;
		this.speed = speed / 10;
		this.wornArmor = MTUtils.parseArmor(engine, turret, tracks, radio, MTUtils.calculateArmorValue(front, side, rear), this.speed);
		this.weapons = MTUtils.parseWeapons(cannon);
		this.reloadTime = cannon.reloadTime();
	}
	
	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public Countries getCountry()
	{
		return Countries.CHINA;
	}

	@Override
	public TankTypes getType()
	{
		return TankTypes.LIGHT;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public ItemStack[] getArmor()
	{
		return wornArmor;
	}

	@Override
	public Inventory getWeapons()
	{
		return weapons;
	}
	
	@Override
	public int getMenuSize()
	{
		int rows = 0;
		while (rows * 9 < values().length)
			rows++;
		
		return rows * 9;
	}
	
	@Override
	public PotionEffect getSpeed()
	{
		return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, this.speed);
	}
	
	@Override
	public double reloadTime()
	{
		return reloadTime;
	}
}
