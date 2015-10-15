package musician101.minetanks.common.tank;

public abstract class AbstractTank extends Info
{
    protected AbstractTankType type;
    int health;
    int speed;
    String[] description;

    protected AbstractTank(String name, AbstractTankType type, int health, int speed, String... description)
    {
        super(name);
        this.type = type;
        this.health = health;
        this.speed = speed / 5;
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

    public String[] getDescription()
    {
        return description;
    }
}
