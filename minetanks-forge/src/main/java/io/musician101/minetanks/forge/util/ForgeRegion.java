package io.musician101.minetanks.forge.util;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.musicianlibrary.java.minecraft.AbstractRegion;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class ForgeRegion extends AbstractRegion<BlockPos>
{
    private int dimension = 0;

    private ForgeRegion(BlockPos blockPos, int dimension)
    {
        this(blockPos, blockPos, dimension);
    }

    private ForgeRegion(BlockPos blockPos, BlockPos blockPos2, int dimension)
    {
        super(Math.min(blockPos.getX(), blockPos2.getX()),
                Math.max(blockPos.getX(), blockPos2.getX()),
                Math.min(blockPos.getY(), blockPos2.getY()),
                Math.max(blockPos.getY(), blockPos2.getY()),
                Math.min(blockPos.getZ(), blockPos2.getZ()),
                Math.max(blockPos.getZ(), blockPos2.getZ()));

        this.dimension = dimension;
    }

    public ForgeRegion(NBTTagCompound nbt)
    {
        super(nbt.hasKey(CommonConfig.MIN_X) ? nbt.getInteger(CommonConfig.MIN_X) : 0,//NOSONAR
                nbt.hasKey(CommonConfig.MAX_X) ? nbt.getInteger(CommonConfig.MAX_X) : 0,
                nbt.hasKey(CommonConfig.MIN_Y) ? nbt.getInteger(CommonConfig.MIN_Y) : 0,
                nbt.hasKey(CommonConfig.MAX_Y) ? nbt.getInteger(CommonConfig.MAX_Y) : 0,
                nbt.hasKey(CommonConfig.MIN_Z) ? nbt.getInteger(CommonConfig.MIN_Z) : 0,
                nbt.hasKey(CommonConfig.MAX_Z) ? nbt.getInteger(CommonConfig.MAX_Z) : 0);

        this.dimension = nbt.hasKey(CommonConfig.WORLD) ? nbt.getInteger(CommonConfig.WORLD) : 0;
    }

    public static ForgeRegion createFromBlockPosRadius(BlockPos blockPos, int radius, int dimension)
    {
        return createFromBlockPosRadius(blockPos, radius, radius, radius, dimension);
    }

    public static ForgeRegion createFromBlockPosRadius(BlockPos blockPos, int xRadius, int yRadius, int zRadius, int dimension)
    {
        if (xRadius < 0 || yRadius < 0 || zRadius < 0)
            throw new IllegalArgumentException("The radius cannot be negative!");

        return xRadius > 0 || yRadius > 0 || zRadius > 0 ? new ForgeRegion(blockPos.subtract(new Vec3i(xRadius, yRadius, zRadius)), blockPos.add(new Vec3i(xRadius, yRadius, zRadius)), dimension) : new ForgeRegion(blockPos, dimension);
    }

    public WorldServer getWorld()
    {
        return DimensionManager.getWorld(dimension);
    }

    public int getDimension()
    {
        return dimension;
    }

    /**
     * @Deprecated because BlockPos doesn't include dimension/world
     */
    @Deprecated
    @Override
    public boolean isInRegion(BlockPos blockPos)
    {
        return blockPos.getX() > getMinX() && blockPos.getX() < getMaxX() && blockPos.getZ() > getMinZ() && blockPos.getZ() < getMaxZ();
    }

    public boolean isInRegion(BlockPos blockPos, int dimension)
    {
        return this.dimension == dimension && isInRegion(blockPos);
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = super.serialize();
        map.put(CommonConfig.WORLD, this.dimension);
        return map;
    }
}
