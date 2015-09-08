package musician101.minetanks.common.tank;

import musician101.minetanks.common.tank.modules.AbstractEngine;
import musician101.minetanks.common.tank.modules.AbstractRadio;
import musician101.minetanks.common.tank.modules.AbstractTracks;
import musician101.minetanks.common.tank.modules.AbstractTurret;
import musician101.minetanks.common.tank.modules.AbstractCannon;

public abstract class AbstractTank extends Info
{
    AbstractCannon cannon;
    AbstractEngine engine;
    AbstractRadio radio;
    AbstractTracks tracks;
    AbstractTankType type;
	int health;
	int speed;
    String[] description;
	
	protected AbstractTank(String name, AbstractTankType type, int health, int speed, AbstractCannon cannon, AbstractEngine engine, AbstractRadio radio, AbstractTracks tracks, AbstractTurret turret, String... description)
	{
		super(name);
		this.type = type;
		this.health = health;
        this.speed = speed / 5;
        this.cannon = cannon;
        this.engine = engine;
        this.radio = radio;
        this.tracks = tracks;
        this.description = description;
	}

    public int getHealth()
    {
        return health;
    }

    public int getSpeed()
    {
        return speed;
    }
}