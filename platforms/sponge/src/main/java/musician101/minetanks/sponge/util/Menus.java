package musician101.minetanks.sponge.util;

import musician101.minetanks.sponge.handler.TankSelectionHandler;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.tank.Tanks;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class Menus
{
    IconMenu tankSelection;

    public Menus()
    {
        this.tankSelection = new IconMenu("Tank Selection", new TankSelectionHandler());
        for (Tank tank : Tanks.tankList)
        {
            tankSelection.setOption(ItemTypes.MINECART, Texts.builder().append(Texts.of(tank.getName())).color(TextColors.GREEN).build(), tank.getDescription());
        }
    }

    public void openTankMenu(Player player)
    {
        tankSelection.open(player);
    }
}
