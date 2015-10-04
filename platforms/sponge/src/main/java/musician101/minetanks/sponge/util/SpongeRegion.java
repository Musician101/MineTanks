package musician101.minetanks.sponge.util;

import musician101.minetanks.common.util.AbstractRegion;
import musician101.minetanks.sponge.MineTanks;
import org.json.simple.JSONObject;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;

public class SpongeRegion extends AbstractRegion
{
    private String worldName = "";

    public SpongeRegion(Location location)
    {
        this(location, location);
    }

    public SpongeRegion(Location location, Location location2)
    {
        super(Math.min((int) location.getPosition().getX(), (int) location2.getPosition().getX()),
                Math.min((int) location.getPosition().getY(), (int) location2.getPosition().getY()),
                Math.min((int) location.getPosition().getZ(), (int) location2.getPosition().getZ()),
                Math.max((int) location.getPosition().getX(), (int) location2.getPosition().getX()),
                Math.max((int) location.getPosition().getY(), (int) location2.getPosition().getY()),
                Math.max((int) location.getPosition().getZ(), (int) location2.getPosition().getZ()));

        this.worldName = ((World) location.getExtent()).getName();
    }

    public SpongeRegion(JSONObject json)
    {
        super((Integer) json.getOrDefault("minX", 0),
                (Integer) json.getOrDefault("minY", 0),
                (Integer) json.getOrDefault("minZ", 0),
                (Integer) json.getOrDefault("maxX", 0),
                (Integer) json.getOrDefault("maxY", 0),
                (Integer) json.getOrDefault("maxZ", 0));

        this.worldName = json.containsKey("World") ? (String) json.get("World") : "";
    }

    public World getWorld()
    {
        return MineTanks.getGame().getServer().getWorld(worldName).get();
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = super.serialize();
        map.put("world", this.worldName);
        return map;
    }

    public boolean isInRegion(Location location)
    {
        return ((World) location.getExtent()).getName().equals(worldName) && location.getPosition().getX() > getMinX() && location.getPosition().getX() < getMaxX() && location.getPosition().getY() > getMinY() && location.getPosition().getY() < getMaxY() && location.getPosition().getZ() > getMinZ() && location.getPosition().getZ() < getMaxZ();
    }

    public static SpongeRegion createFromLocationRadius(Location location, double radius)
    {
        return createFromLocationRadius(location, radius, radius, radius);
    }

    public static SpongeRegion createFromLocationRadius(Location location, double xRadius, double yRadius, double zRadius)
    {
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");

        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new SpongeRegion(MTUtils.subPosition(location, xRadius, yRadius, zRadius), MTUtils.addPosition(location, xRadius, yRadius, zRadius)) : new SpongeRegion(location);
    }
}
