package musician101.minetanks.spigot.util;

import musician101.minetanks.common.CommonReference;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.handler.MenuHandlers.TankSelectionHandler;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.SpigotTanks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus
{
    private final IconMenu tankSelection;

    public Menus(SpigotMineTanks plugin)
    {
        this.tankSelection = new IconMenu(CommonReference.TANK_SELECTION, MTUtils.getMenuSize(), new TankSelectionHandler(plugin), plugin);

        //TODO need to implement a page system for the inevitable
        int slot = 0;
        for (final SpigotTank tank : SpigotTanks.tankList)
            tankSelection.setOption(slot++, new ItemStack(Material.MINECART, 1), "\u00A7a" + tank.getName(), CommonItemText.tankType(tank.getType()));
    }

    public void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
