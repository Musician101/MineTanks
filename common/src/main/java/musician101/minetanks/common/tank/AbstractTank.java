package musician101.minetanks.common.tank;

import musician101.minetanks.common.tank.modules.AbstractCannon;
import musician101.minetanks.common.tank.modules.AbstractEngine;
import musician101.minetanks.common.tank.modules.AbstractRadio;
import musician101.minetanks.common.tank.modules.AbstractTrackz;
import musician101.minetanks.common.tank.modules.AbstractTurret;

import java.util.List;

public abstract class AbstractTank<Country extends AbstractCountry, TankType extends AbstractTankType, Cannon extends AbstractCannon, Engine extends AbstractEngine, Radio extends AbstractRadio, Tracks extends AbstractTrackz, Turret extends AbstractTurret, ItemStack, PotionEffect> extends Info<ItemStack>
{
    protected final Armor armor;
    protected final Cannon cannon;
    private final Country country;
    protected final Engine engine;
    private final int health;
    private final int speed;
    protected final Radio radio;
    protected final Tracks tracks;
    protected final Turret turret;
    private final TankType type;

    protected AbstractTank(String name, Country country, TankType type, int health, Armor armor, int speed, Cannon cannon, Engine engine, Radio radio, Tracks tracks, Turret turret)
    {
        super(name);
        this.country = country;
        this.type = type;
        this.health = health;
        this.armor = armor;
        this.speed = speed / 10;
        this.cannon = cannon;
        this.engine = engine;
        this.radio = radio;
        this.tracks = tracks;
        this.turret = turret;
    }

    protected abstract ItemStack parseArmorValue(ItemStack item);

    protected abstract ItemStack parseArmorValue(ItemStack item, Armor armor);

    protected abstract ItemStack parseSpeedValue(ItemStack item);

    public Cannon getCannon()
    {
        return cannon;
    }

    public Country getCountry()
    {
        return country;
    }

    public int getHealth()
    {
        return health;
    }

    public int getSpeed()
    {
        return speed;
    }

    public abstract ItemStack getHelmet();

    public abstract ItemStack getChestplate();

    public abstract ItemStack getLeggings();

    public abstract ItemStack getBoots();

    public abstract List<ItemStack> getWeapons();

    public TankType getType()
    {
        return type;
    }

    public abstract PotionEffect getSpeedEffect();
}
