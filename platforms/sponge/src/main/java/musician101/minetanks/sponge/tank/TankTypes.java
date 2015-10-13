package musician101.minetanks.sponge.tank;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TankTypes
{
    public static final TankType LIGHT = new TankType("Light", new ItemStack(Material.WOOD_SWORD, 1));
    public static final TankType MEDIUM = new TankType("Medium", new ItemStack(Material.STONE_SWORD, 1));
    public static final TankType HEAVY = new TankType("Heavy", new ItemStack(Material.IRON_SWORD, 1));
    public static final TankType TD = new TankType("TD", new ItemStack(Material.GOLD_SWORD, 1));
    public static final TankType ARTY = new TankType("SPG", new ItemStack(Material.DIAMOND_SWORD, 1));
}
