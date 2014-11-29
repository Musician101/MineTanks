package musician101.minetanks.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import musician101.minetanks.MineTanks;

import org.spongepowered.api.block.BlockLoc;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * 
 * @author KingFaris10 on GitHub
 * @link https://gist.github.com/KingFaris10/4527fbaf8caa9fd7b800
 * @authro Musician101 port to SpongeAPI
 */
public class Region implements Iterable<BlockLoc>
{
    private String worldName = "";
    private int x1 = 0, y1 = 0, z1 = 0;
    private int x2 = 0, y2 = 0, z2 = 0;
 
    public Region(Location location)
    {
        this(location, location);
    }
 
    public Region(Location location, Location location2)
    {
        if (!((World) location.getExtent()).getUniqueID().equals(((World) location2.getExtent()).getUniqueID()))
            throw new IllegalArgumentException("Location 1 must be in the same world as Location 2!");
 
        this.worldName = ((World) location.getExtent()).getName();
 
        this.x1 = Math.min((int) location.getPosition().getX(), (int) location2.getPosition().getX());
        this.y1 = Math.min((int) location.getPosition().getY(), (int) location2.getPosition().getY());
        this.z1 = Math.min((int) location.getPosition().getZ(), (int) location2.getPosition().getZ());
 
        this.x2 = Math.max((int) location.getPosition().getX(), (int) location2.getPosition().getX());
        this.y2 = Math.max((int) location.getPosition().getY(), (int) location2.getPosition().getY());
        this.z2 = Math.max((int) location.getPosition().getZ(), (int) location2.getPosition().getZ());
    }
 
    private Region(Map<String, Object> serializedCuboid)
    {
        this.worldName = serializedCuboid.containsKey("World") ? (String) serializedCuboid.get("World") : "";
        this.x1 = serializedCuboid.containsKey("X1") ? (Integer) serializedCuboid.get("X1") : 0;
        this.y1 = serializedCuboid.containsKey("Y1") ? (Integer) serializedCuboid.get("Y1") : 0;
        this.z1 = serializedCuboid.containsKey("Z1") ? (Integer) serializedCuboid.get("Z1") : 0;
        this.x2 = serializedCuboid.containsKey("X2") ? (Integer) serializedCuboid.get("X2") : 0;
        this.y2 = serializedCuboid.containsKey("Y2") ? (Integer) serializedCuboid.get("Y2") : 0;
        this.z2 = serializedCuboid.containsKey("Z2") ? (Integer) serializedCuboid.get("Z2") : 0;
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
 
    public List<BlockLoc> getBlocks()
    {
        List<BlockLoc> blockList = new ArrayList<BlockLoc>();
        World cuboidWorld = this.getWorld();
        for (int x = this.x1; x <= this.x2; x++)
            for (int y = this.y1; y <= this.y2; y++)
                for (int z = this.z1; z <= this.z2; z++)
                    blockList.add(cuboidWorld.getBlock(x, y, z));
        
        return blockList;
    }
 
    public World getWorld()
    {
        return MineTanks.getGame().getWorld(this.worldName);
    }
    
    public Map<String, Object> serialize()
    {
        Map<String, Object> serializedCuboid = new HashMap<String, Object>();
        serializedCuboid.put("world", this.worldName);
        serializedCuboid.put("minX", this.x1);
        serializedCuboid.put("minY", this.y1);
        serializedCuboid.put("minZ", this.z1);
        serializedCuboid.put("maxX", this.x2);
        serializedCuboid.put("maxY", this.y2);
        serializedCuboid.put("maxZ", this.z2);
        return serializedCuboid;
    }
 
    @Override
    public ListIterator<BlockLoc> iterator() 
    {
        return this.getBlocks().listIterator();
    }
    
    public boolean isInRegion(Location location)
    {
    	return !(location.getPosition().getX() <= x1 || location.getPosition().getX() >= x2 || location.getPosition().getY() <= y1 || location.getPosition().getY() >= y2 || location.getPosition().getZ() <= z1 || location.getPosition().getZ() >= z2);
    }
    
    public static Region deserialize(Map<String, Object> serializedCuboid)
    {
        return new Region(serializedCuboid);
    }
 
    public static Region createFromLocationRadius(Location location, double radius)
    {
        return createFromLocationRadius(location, radius, radius, radius);
    }
 
    public static Region createFromLocationRadius(Location location, double xRadius, double yRadius, double zRadius)
    {
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");
        
        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new Region(MTUtils.subPosition(location, xRadius, yRadius, zRadius), MTUtils.addPosition(location, xRadius, yRadius, zRadius)) : new Region(location);
    }
}
