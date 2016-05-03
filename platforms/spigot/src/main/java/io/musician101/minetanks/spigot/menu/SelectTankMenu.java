package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
import io.musician101.minetanks.spigot.tank.SpigotTank;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTanks;
import io.musician101.minetanks.spigot.util.MTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SelectTankMenu extends AbstractMenu
{
    public SelectTankMenu(SpigotMineTanks plugin, SpigotBattleField field, SpigotCountry country, SpigotTankType tankType, UUID viewer)
    {
        super(plugin, Bukkit.createInventory(null, 18, "Select Tank"), event ->
        {
            Player player = event.getPlayer();
            if (!viewer.equals(player.getUniqueId()) || !field.getPlayers().containsKey(viewer))
                return;

            String name = event.getItem().getItemMeta().getDisplayName();
            SpigotPlayerTank pt = field.getPlayerTank(viewer);
            SpigotTank tank = SpigotTanks.getTank(ChatColor.stripColor(name));
            if (name.equals("Back"))
            {
                if (country == null)
                    new SelectTankTypeMenu(plugin, field, viewer).open(player);
                else if (tankType == null)
                    new SelectCountryMenu(plugin, field, viewer).open(player);
                //TODO ready up method doesn't check if tank is set
                event.setWillDestroy(true);
            }
            else if (tank != null)
            {
                pt.setTank(tank);
                player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getItem().getType(), CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(tank)));
                player.sendMessage(ChatColor.GREEN + CommonMessages.tankSelection1(tank));
                player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_2);
                player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_3);
                event.setWillDestroy(true);
                event.setWillClose(true);
            }
        });

        int slot = 0;
        for (SpigotTank tank : SpigotTanks.getValues())
            if ((country == null || country.getName().equals(tank.getCountry().getName())) && (tankType == null || tankType.getName().equals(tank.getType().getName())))
                setOption(slot++, tank.getItem());

        addBackButton();
    }
}
