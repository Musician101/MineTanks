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

public enum ChinaMedium implements ITank
{
	TYPE_T_34(0, "Type T-34", 5, 430000, 16000, 28.85, 45, 45, 40, 55, new Engines("V-2-34", TankTypes.MEDIUM), new Cannons("57 mm 55-57FG", 100, 26.09), Turrets.TYPE_T_34M, Tracks.TYPE_T_34M, new Radios("9RM", TankTypes.MEDIUM)),
	TYPE_58(1, "Type 58", 6, 920000, 3000, 32.5, 45, 45, 40, 55, new Engines("12150L", TankTypes.MEDIUM), new Cannons("85 mm 56-85JT", 60, 12), Turrets.TYPE_58, Tracks.TYPE_58, new Radios("9RM", TankTypes.MEDIUM)),
	T_34_1(2, "T-34-1", 7, 1400000, 49000, 32.51, 60, 45, 40, 50, new Engines("12150L", TankTypes.MEDIUM), new Cannons("100 mm 44-100JT", 34, 6.45), Turrets.T_34_1_MODEL_2, Tracks.T_34_1_MODEL_2, new Radios("A-220A", TankTypes.MEDIUM)),
	T_34_2(3, "T-34-2", 8, 2480000, 105000, 34.18, 70, 45, 45, 50, new Engines("12150LS", TankTypes.MEDIUM), new Cannons("122 mm 37-122JT", 40, 4), Turrets.T_34_2_MODEL_2, Tracks.T_34_MODEL_2, new Radios("A-220A", TankTypes.MEDIUM)),
	WZ_120(4, "WZ-120", 9, 3520000, 175000, 37.2, 100, 80, 45, 56, new Engines("12150LT", TankTypes.MEDIUM), new Cannons("122 mm 60-122T", 40, 5.0), Turrets.WZ_120_1, Tracks.WZ_120_MODEL_3, new Radios("A-220A", TankTypes.MEDIUM)),
	_121(5, "121", 10, 39, 6100000, 180000, 120, 80, 60, 56, new Engines("12150L7", TankTypes.MEDIUM), new Cannons("122 mm 60-122TG", 40, 6.25), Turrets._121, Tracks._121, new Radios("A-220B", TankTypes.MEDIUM));
	
	int id, level, money, speed, xp;
	String name;
	ItemStack[] wornArmor;
	Inventory weapons;
	double reloadTime, weight;
	
	private ChinaMedium(int id, String name, int level, int money, int xp, double weight, double front, double side, double rear, int speed, Engines engine, Cannons cannon, Turrets turret, Tracks tracks, Radios radio)
	{
		this.id = id;
		this.name = name;
		this.level = level;
		this.speed = speed / 10;
		this.wornArmor = MTUtils.parseArmor(engine, turret, tracks, radio, MTUtils.calculateArmorValue(front, side, rear), this.speed);
		this.weapons = MTUtils.parseWeapons(cannon);
		this.reloadTime = cannon.reloadTime();
		this.weight = weight;
		this.money = money / 20;
		this.xp = xp / 20;
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
	public int getLevel()
	{
		return level;
	}

	@Override
	public TankTypes getType()
	{
		
		return TankTypes.MEDIUM;
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
	
	@Override
	public double getWeight()
	{
		return weight;
	}

	@Override
	public int getXp()
	{
		return xp;
	}

	@Override
	public int getMoney()
	{
		return money;
	}
}
