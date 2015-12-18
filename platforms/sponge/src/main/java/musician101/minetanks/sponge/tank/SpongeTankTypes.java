package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.CommonReference.CommonTankTypes;
import org.spongepowered.api.item.ItemTypes;

public class SpongeTankTypes
{
    public static final SpongeTankType LIGHT = new SpongeTankType(CommonTankTypes.LIGHT, ItemTypes.WOODEN_SWORD);
    public static final SpongeTankType MEDIUM = new SpongeTankType(CommonTankTypes.MEDIUM, ItemTypes.STONE_SWORD);
    public static final SpongeTankType HEAVY = new SpongeTankType(CommonTankTypes.HEAVY, ItemTypes.IRON_SWORD);
    public static final SpongeTankType TD = new SpongeTankType(CommonTankTypes.TD, ItemTypes.GOLDEN_SWORD);
    public static final SpongeTankType ARTY = new SpongeTankType(CommonTankTypes.SPG, ItemTypes.DIAMOND_SWORD);
}
