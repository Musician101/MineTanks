package musician101.minetanks.common.util;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRegion<L>
{
    int minX = 0;
    int maxX = 0;
    int minY = 0;
    int maxY = 0;
    int minZ = 0;
    int maxZ = 0;

    protected AbstractRegion(int minX, int maxX, int minY, int maxY, int minZ, int maxZ)
    {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public abstract boolean isInRegion(L location);

    public int getMinX()
    {
        return minX;
    }

    public int getMaxX()
    {
        return maxX;
    }

    public int getMinY()
    {
        return minY;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public int getMinZ()
    {
        return minZ;
    }

    public int getMaxZ()
    {
        return maxZ;
    }

    public Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("minX", minX);
        map.put("maxX", maxX);
        map.put("minY", minY);
        map.put("maxY", maxY);
        map.put("minZ", minZ);
        map.put("maxZ", maxZ);
        return map;
    }
}
