package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonTankTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TankTypes
{
    public static final TankType LIGHT = new TankType(CommonTankTypes.LIGHT, new ItemStack(Material.WOOD_SWORD, 1));
    public static final TankType MEDIUM = new TankType(CommonTankTypes.MEDIUM, new ItemStack(Material.STONE_SWORD, 1));
    public static final TankType HEAVY = new TankType(CommonTankTypes.HEAVY, new ItemStack(Material.IRON_SWORD, 1));
    public static final TankType TD = new TankType(CommonTankTypes.TD, new ItemStack(Material.GOLD_SWORD, 1));
    public static final TankType ARTY = new TankType(CommonTankTypes.SPG, new ItemStack(Material.DIAMOND_SWORD, 1));
}
