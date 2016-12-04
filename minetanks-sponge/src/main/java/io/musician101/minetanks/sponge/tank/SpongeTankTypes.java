package io.musician101.minetanks.sponge.tank;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import org.spongepowered.api.item.ItemTypes;

import java.util.List;

public class SpongeTankTypes extends AbstractInfoStorage<SpongeTankType>
{
    private SpongeTankTypes()
    {
        super(new SpongeTankType(CommonTankTypes.LIGHT, ItemTypes.WOODEN_SWORD, 1),
                new SpongeTankType(CommonTankTypes.MEDIUM, ItemTypes.STONE_SWORD, 3),
                new SpongeTankType(CommonTankTypes.HEAVY, ItemTypes.IRON_SWORD, 5),
                new SpongeTankType(CommonTankTypes.TD, ItemTypes.GOLDEN_SWORD, 3),
                new SpongeTankType(CommonTankTypes.SPG, ItemTypes.DIAMOND_SWORD, 2));
    }

    public static SpongeTankType getTankType(String name)
    {
        return new SpongeTankTypes().get(name);
    }

    public static List<SpongeTankType> getValues()
    {
        return new SpongeTankTypes().values();
    }
}
