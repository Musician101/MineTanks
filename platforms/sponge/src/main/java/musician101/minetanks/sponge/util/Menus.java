package musician101.minetanks.sponge.util;

import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.handler.TankSelectionHandler;
import musician101.minetanks.sponge.tank.Tanks;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemTypes;

public class Menus
{
    SpongeMineTanks plugin;
    IconMenu tankSelection;

    public Menus(SpongeMineTanks plugin)
    {
        this.plugin = plugin;
        this.tankSelection = new IconMenu("Tank Selection", MTUtils.getMenuSize(), new TankSelectionHandler());
        int nextId = 0;
        for (Tanks tank : Tanks.values())
        {
            nextId++;
            //TODO ItemStack is an interface
            tankSelection.setOption(nextId, ItemTypes.MINECART, "\u00A7a" + tank.getName(), tank.getDescription());
        }
    }

    public void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
