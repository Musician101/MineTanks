package io.musician101.minetanks.forge.tank;

import io.musician101.minetanks.common.CommonReference.CommonTankTypes;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import io.musician101.minetanks.forge.util.MTUtils;
import org.bukkit.Material;

import java.util.List;

public class ForgeTankTypes extends AbstractInfoStorage<ForgeTankType>
{
    private ForgeTankTypes()
    {
        super(
                new ForgeTankType(CommonTankTypes.LIGHT, MTUtils.createCustomItem(Material.WOOD_SWORD, CommonTankTypes.LIGHT, "High speed and low reload time.", "Low armor and damage."), 1),
                new ForgeTankType(CommonTankTypes.MEDIUM, MTUtils.createCustomItem(Material.STONE_SWORD, CommonTankTypes.MEDIUM, "Average speed, damage,", "reload time, and armor."), 3),
                new ForgeTankType(CommonTankTypes.HEAVY, MTUtils.createCustomItem(Material.IRON_SWORD, CommonTankTypes.HEAVY, "Low speed and average reload time.", "High armor and damage."), 5),
                new ForgeTankType(CommonTankTypes.TD, MTUtils.createCustomItem(Material.GOLD_SWORD, CommonTankTypes.TD, "Average speed and reload time.", "Above average armor, and high damage."), 3),
                new ForgeTankType(CommonTankTypes.SPG, MTUtils.createCustomItem(Material.DIAMOND_SWORD, CommonTankTypes.SPG, "Low speed and armor", "Super high damage."), 2));
    }

    public static ForgeTankType getTankType(String name)
    {
        return new ForgeTankTypes().get(name);
    }

    public static List<ForgeTankType> getValues()
    {
        return new ForgeTankTypes().values();
    }
}
