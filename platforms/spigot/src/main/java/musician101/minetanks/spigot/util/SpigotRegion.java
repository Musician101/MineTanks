package musician101.minetanks.spigot.util;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.util.AbstractRegion;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.Map;

public class SpigotRegion extends AbstractRegion
{
    private String worldName = "";

    public SpigotRegion(Location location)
    {
        this(location, location);
    }

    public SpigotRegion(Location location, Location location2)
    {
        super(Math.min((int) location.getX(), (int) location2.getX()),
                Math.min((int) location.getY(), (int) location2.getY()),
                Math.min((int) location.getZ(), (int) location2.getZ()),
                Math.max((int) location.getX(), (int) location2.getX()),
                Math.max((int) location.getY(), (int) location2.getY()),
                Math.max((int) location.getZ(), (int) location2.getZ()));

        this.worldName = location.getWorld().getName();
    }

    public SpigotRegion(Map<String, Object> map)
    {
        super(map.containsKey(CommonConfig.MIN_X) ? (Integer) map.get(CommonConfig.MIN_X) : 0,
            map.containsKey(CommonConfig.MIN_Y) ? (Integer) map.get(CommonConfig.MIN_Y) : 0,
            map.containsKey(CommonConfig.MIN_Z) ? (Integer) map.get(CommonConfig.MIN_Z) : 0,
                map.containsKey(CommonConfig.MAX_X) ? (Integer) map.get(CommonConfig.MAX_X) : 0,
                map.containsKey(CommonConfig.MAX_Y) ? (Integer) map.get(CommonConfig.MAX_Y) : 0,
                map.containsKey(CommonConfig.MAX_Z) ? (Integer) map.get(CommonConfig.MAX_Z) : 0);

        this.worldName = map.containsKey(CommonConfig.WORLD) ? (String) map.get(CommonConfig.WORLD) : "";
    }

    public static SpigotRegion createFromLocationRadius(Location location, double radius)
    {
        return createFromLocationRadius(location, radius, radius, radius);
    }

    public static SpigotRegion createFromLocationRadius(Location location, double xRadius, double yRadius, double zRadius)
    {
        Validate.notNull(location);
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");
        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new SpigotRegion(location.clone().subtract(xRadius, yRadius, zRadius), location.clone().add(xRadius, yRadius, zRadius)) : new SpigotRegion(location);
    }

    public World getWorld()
    {
        World world = Bukkit.getServer().getWorld(this.worldName);
        if (world == null)
            world = Bukkit.getServer().createWorld(WorldCreator.name(this.worldName));

        return world;
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
        return location.getWorld().getName().equals(worldName) && location.getX() > getMinX() && location.getX() < getMaxX() && location.getZ() > getMinZ() && location.getZ() < getMaxZ();
    }
}
