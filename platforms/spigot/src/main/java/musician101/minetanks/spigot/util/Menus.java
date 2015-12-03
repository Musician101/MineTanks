package musician101.minetanks.spigot.util;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.handlers.MenuHandlers.TankSelectionHandler;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.tank.Tanks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus
{
    private final IconMenu tankSelection;

    public Menus(MineTanks plugin)
    {
        this.tankSelection = new IconMenu(CommonReference.TANK_SELECTION, MTUtils.getMenuSize(), new TankSelectionHandler(plugin), plugin);

        //TODO need to implement a page system for the inevitable
        int slot = 0;
        for (final Tank tank : Tanks.tankList)
            tankSelection.setOption(slot++, new ItemStack(Material.MINECART, 1), "\u00A7a" + tank.getName(), tank.getDescription());
    }

    public void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
