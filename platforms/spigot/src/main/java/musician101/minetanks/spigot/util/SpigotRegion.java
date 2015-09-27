package musician101.minetanks.spigot.util;

import musician101.minetanks.common.util.AbstractRegion;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.HashMap;
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
        super(map.containsKey("X1") ? (Integer) map.get("X1") : 0,
            map.containsKey("Y1") ? (Integer) map.get("Y1") : 0,
            map.containsKey("Z1") ? (Integer) map.get("Z1") : 0,
            map.containsKey("X2") ? (Integer) map.get("X2") : 0,
            map.containsKey("Y2") ? (Integer) map.get("Y2") : 0,
            map.containsKey("Z2") ? (Integer) map.get("Z2") : 0);

        this.worldName = map.containsKey("World") ? (String) map.get("World") : "";
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
        map.put("World", this.worldName);
        return map;
    }

    public boolean isInRegion(Location location)
    {
        return location.getWorld().getName().equals(worldName) && location.getX() > getMinX() && location.getX() < getMaxX() && location.getZ() > getMinZ() && location.getZ() < getMaxZ();
    }
}
