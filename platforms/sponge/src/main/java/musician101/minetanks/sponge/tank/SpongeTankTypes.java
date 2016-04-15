package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.CommonReference.CommonTankTypes;
import org.spongepowered.api.item.ItemTypes;

import java.util.Arrays;
import java.util.List;

public class SpongeTankTypes
{
    public static final SpongeTankType LIGHT = new SpongeTankType(CommonTankTypes.LIGHT, ItemTypes.WOODEN_SWORD, 1);
    public static final SpongeTankType MEDIUM = new SpongeTankType(CommonTankTypes.MEDIUM, ItemTypes.STONE_SWORD, 3);
    public static final SpongeTankType HEAVY = new SpongeTankType(CommonTankTypes.HEAVY, ItemTypes.IRON_SWORD, 5);
    public static final SpongeTankType TD = new SpongeTankType(CommonTankTypes.TD, ItemTypes.GOLDEN_SWORD, 3);
    public static final SpongeTankType ARTY = new SpongeTankType(CommonTankTypes.SPG, ItemTypes.DIAMOND_SWORD, 2);
    public static final List<SpongeTankType> ALL = Arrays.asList(LIGHT, MEDIUM, HEAVY, TD, ARTY);

    public static SpongeTankType getByName(String name)
    {
        for (SpongeTankType type : ALL)
            if (name.equalsIgnoreCase(type.getName()))
                return type;

        return null;
    }
}
