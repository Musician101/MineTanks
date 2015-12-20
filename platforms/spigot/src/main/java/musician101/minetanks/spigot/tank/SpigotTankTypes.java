package musician101.minetanks.spigot.tank;

import musician101.minetanks.common.CommonReference.CommonTankTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpigotTankTypes
{
    public static final SpigotTankType LIGHT = new SpigotTankType(CommonTankTypes.LIGHT, new ItemStack(Material.WOOD_SWORD, 1));
    public static final SpigotTankType MEDIUM = new SpigotTankType(CommonTankTypes.MEDIUM, new ItemStack(Material.STONE_SWORD, 1));
    public static final SpigotTankType HEAVY = new SpigotTankType(CommonTankTypes.HEAVY, new ItemStack(Material.IRON_SWORD, 1));
    public static final SpigotTankType TD = new SpigotTankType(CommonTankTypes.TD, new ItemStack(Material.GOLD_SWORD, 1));
    public static final SpigotTankType ARTY = new SpigotTankType(CommonTankTypes.SPG, new ItemStack(Material.DIAMOND_SWORD, 1));
}
