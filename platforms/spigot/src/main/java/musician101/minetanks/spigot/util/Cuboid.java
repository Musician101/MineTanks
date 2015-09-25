package musician101.minetanks.spigot.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author KingFaris10 on GitHub
 * @link https://gist.github.com/KingFaris10/4527fbaf8caa9fd7b800
 */
public class Cuboid implements Iterable<Block>, ConfigurationSerializable
{
    private String worldName = "";
    private int x1 = 0, y1 = 0, z1 = 0;
    private int x2 = 0, y2 = 0, z2 = 0;

    public Cuboid(Location location)
    {
        this(location, location);
    }

    public Cuboid(Location location, Location location2)
    {
        Validate.notNull(location);
        Validate.notNull(location2);
        if (!location.getWorld().getUID().equals(location2.getWorld().getUID()))
            throw new IllegalArgumentException("Location 1 must be in the same world as Location 2!");

        this.worldName = location.getWorld().getName();

        this.x1 = Math.min((int) location.getX(), (int) location2.getX());
        this.y1 = Math.min((int) location.getY(), (int) location2.getY());
        this.z1 = Math.min((int) location.getZ(), (int) location2.getZ());

        this.x2 = Math.max((int) location.getX(), (int) location2.getX());
        this.y2 = Math.max((int) location.getY(), (int) location2.getY());
        this.z2 = Math.max((int) location.getZ(), (int) location2.getZ());
    }

    private Cuboid(Map<String, Object> serializedCuboid)
    {
        Validate.notNull(serializedCuboid);
        this.worldName = serializedCuboid.containsKey("World") ? (String) serializedCuboid.get("World") : "";
        this.x1 = serializedCuboid.containsKey("X1") ? (Integer) serializedCuboid.get("X1") : 0;
        this.y1 = serializedCuboid.containsKey("Y1") ? (Integer) serializedCuboid.get("Y1") : 0;
        this.z1 = serializedCuboid.containsKey("Z1") ? (Integer) serializedCuboid.get("Z1") : 0;
        this.x2 = serializedCuboid.containsKey("X2") ? (Integer) serializedCuboid.get("X2") : 0;
        this.y2 = serializedCuboid.containsKey("Y2") ? (Integer) serializedCuboid.get("Y2") : 0;
        this.z2 = serializedCuboid.containsKey("Z2") ? (Integer) serializedCuboid.get("Z2") : 0;
    }

    public static Cuboid deserialize(Map<String, Object> serializedCuboid)
    {
        return new Cuboid(serializedCuboid);
    }

    public static Cuboid createFromLocationRadius(Location location, double radius)
    {
        return createFromLocationRadius(location, radius, radius, radius);
    }

    public static Cuboid createFromLocationRadius(Location location, double xRadius, double yRadius, double zRadius)
    {
        Validate.notNull(location);
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");
        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new Cuboid(location.clone().subtract(xRadius, yRadius, zRadius), location.clone().add(xRadius, yRadius, zRadius)) : new Cuboid(location);
    }

    public int getMinX()
    {
        return x1;
    }

    public int getMaxX()
    {
        return x2;
    }

    public int getMinY()
    {
        return y1;
    }

    public int getMaxY()
    {
        return y2;
    }

    public int getMinZ()
    {
        return z1;
    }

    public int getMaxZ()
    {
        return z2;
    }

    public int getVolume()
    {
        return ((this.x2 - this.x1) + 1) * ((this.y2 - this.y1) + 1) * ((this.z2 - this.z1) + 1);
    }

    public List<Block> getBlocks()
    {
        List<Block> blockList = new ArrayList<>();
        World cuboidWorld = this.getWorld();
        for (int x = this.x1; x <= this.x2; x++)
        {
            for (int y = this.y1; y <= this.y2; y++)
            {
                for (int z = this.z1; z <= this.z2; z++)
                {
                    blockList.add(cuboidWorld.getBlockAt(x, y, z));
                }
            }
        }
        return blockList;
    }

    public World getWorld()
    {
        World cuboidWorld = Bukkit.getServer().getWorld(this.worldName);
        if (cuboidWorld == null)
            cuboidWorld = Bukkit.getServer().createWorld(WorldCreator.name(this.worldName));
        return cuboidWorld;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> serializedCuboid = new HashMap<>();
        serializedCuboid.put("World", this.worldName);
        serializedCuboid.put("X1", this.x1);
        serializedCuboid.put("Y1", this.y1);
        serializedCuboid.put("Z1", this.z1);
        serializedCuboid.put("X2", this.x2);
        serializedCuboid.put("Y2", this.y2);
        serializedCuboid.put("Z2", this.z2);
        return serializedCuboid;
    }

    @Override
    public ListIterator<Block> iterator()
    {
        return this.getBlocks().listIterator();
    }

    public boolean isInCuboid(Location location)
    {
        return location.getWorld().getName().equals(worldName) && location.getX() > x1 && location.getX() < x2 && location.getZ() > z1 && location.getZ() < z2;
    }
}
