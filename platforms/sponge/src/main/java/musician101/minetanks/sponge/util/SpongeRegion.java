package musician101.minetanks.sponge.util;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.util.AbstractRegion;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;

public class SpongeRegion extends AbstractRegion<Location<World>>
{
    private String worldName = "";

    public SpongeRegion(Location<World> location)
    {
        this(location, location);
    }

    public SpongeRegion(Location<World> location, Location<World> location2)
    {
        super(Math.min((int) location.getPosition().getX(), (int) location2.getPosition().getX()),
                Math.min((int) location.getPosition().getY(), (int) location2.getPosition().getY()),
                Math.min((int) location.getPosition().getZ(), (int) location2.getPosition().getZ()),
                Math.max((int) location.getPosition().getX(), (int) location2.getPosition().getX()),
                Math.max((int) location.getPosition().getY(), (int) location2.getPosition().getY()),
                Math.max((int) location.getPosition().getZ(), (int) location2.getPosition().getZ()));

        this.worldName = location.getExtent().getName();
    }

    public SpongeRegion(SpongeJSONConfig json)
    {
        super(json.getInteger(CommonConfig.MIN_X, 0),
                json.getInteger(CommonConfig.MIN_Y, 0),
                json.getInteger(CommonConfig.MIN_Z, 0),
                json.getInteger(CommonConfig.MAX_X, 0),
                json.getInteger(CommonConfig.MAX_Y, 0),
                json.getInteger(CommonConfig.MAX_Z, 0));

        this.worldName = json.containsKey(CommonConfig.WORLD) ? (String) json.get(CommonConfig.WORLD) : "";
    }

    public World getWorld()
    {
        return Sponge.getGame().getServer().getWorld(worldName).get();
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = super.serialize();
        map.put(CommonConfig.WORLD, this.worldName);
        return map;
    }

    public boolean isInRegion(Location location)
    {
        return ((World) location.getExtent()).getName().equals(worldName) && location.getPosition().getX() > getMinX() && location.getPosition().getX() < getMaxX() && location.getPosition().getY() > getMinY() && location.getPosition().getY() < getMaxY() && location.getPosition().getZ() > getMinZ() && location.getPosition().getZ() < getMaxZ();
    }

    public static SpongeRegion createFromLocationRadius(Location<World> location, double radius)
    {
        return createFromLocationRadius(location, radius, radius, radius);
    }

    public static SpongeRegion createFromLocationRadius(Location<World> location, double xRadius, double yRadius, double zRadius)
    {
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");

        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new SpongeRegion(location.sub(xRadius, yRadius, zRadius), location.add(xRadius, yRadius, zRadius)) : new SpongeRegion(location);
    }
}
