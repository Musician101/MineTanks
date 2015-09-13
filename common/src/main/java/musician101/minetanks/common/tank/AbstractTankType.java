package musician101.minetanks.common.tank;

public abstract class AbstractTankType extends Info
{
    protected Object icon;

    public AbstractTankType(String name, Object icon)
    {
        super(name);
        this.icon = icon;
    }
}
