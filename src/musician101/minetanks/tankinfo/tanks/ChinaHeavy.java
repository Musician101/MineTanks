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

public enum ChinaHeavy implements ITank
{
	IS_2(0, "IS-2", 7, 46.04, 90, 90, 60, 37, new Engines("12150LS", TankTypes.HEAVY), new Cannons("122 mm D-25T", 28, 4.88), Turrets.IS_2_LATE, Tracks.IS_2_LATE, new Radios("A-220", TankTypes.HEAVY)),
	_110(1, "110", 8, 50.47, 120, 90, 60, 40, new Engines("12150LS", TankTypes.HEAVY), new Cannons("100 mm 62-100T", 40, 6), Turrets.T_10, Tracks._110_1, new Radios("A-220A", TankTypes.HEAVY)),
	WZ_111(2, "WZ-111 model 1-4", 9, 45, 120, 80, 60, 50, new Engines("12150LT", TankTypes.HEAVY), new Cannons("130 mm 59-130T", 40, 4.29), Turrets.WZ_111, Tracks.WZ_111_MODEL_4, new Radios("A-220A", TankTypes.HEAVY)),
	_113(3, "113", 10, 45, 120, 90, 70, 50, new Engines("V-2-54SC", TankTypes.HEAVY), new Cannons("122 mm 60-122T", 34, 5.5), Turrets._113, Tracks._113, new Radios("A-220B", TankTypes.HEAVY));
	
	int id;
	int speed;
	int level;
	double weight;
	String name;
	ItemStack[] wornArmor = new ItemStack[4];
	Inventory weapons;
	double reloadTime;
	
	private ChinaHeavy(int id, String name, int level, double weight, double front, double side, double rear, int speed, Engines engine, Cannons cannon, Turrets turret, Tracks tracks, Radios radio)
	{
		this.id = id;
		this.name = name;
		this.level = level;
		this.speed = speed / 10;
		this.wornArmor = MTUtils.parseArmor(engine, turret, tracks, radio, MTUtils.calculateArmorValue(front, side, rear), this.speed);
		this.weapons = MTUtils.parseWeapons(cannon);
		this.reloadTime = cannon.reloadTime();
		this.weight = weight;
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
		
		return TankTypes.HEAVY;
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
}
