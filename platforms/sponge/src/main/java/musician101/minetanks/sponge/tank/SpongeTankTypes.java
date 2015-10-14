package musician101.minetanks.sponge.tank;

import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.item.ItemTypes;

public class SpongeTankTypes
{
    public static final SpongeTankType LIGHT = new SpongeTankType("Light", MTUtils.createCustomItem(ItemTypes.WOODEN_SWORD));
    public static final SpongeTankType MEDIUM = new SpongeTankType("Medium", MTUtils.createCustomItem(ItemTypes.STONE_SWORD));
    public static final SpongeTankType HEAVY = new SpongeTankType("Heavy", MTUtils.createCustomItem(ItemTypes.IRON_SWORD));
    public static final SpongeTankType TD = new SpongeTankType("TD", MTUtils.createCustomItem(ItemTypes.GOLDEN_SWORD));
    public static final SpongeTankType ARTY = new SpongeTankType("SPG", MTUtils.createCustomItem(ItemTypes.DIAMOND_SWORD));
}
