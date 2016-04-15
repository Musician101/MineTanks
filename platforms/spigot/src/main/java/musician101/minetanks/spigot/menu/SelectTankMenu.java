package musician101.minetanks.spigot.menu;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.tank.SpigotCountry;
import musician101.minetanks.spigot.tank.SpigotTank;
import musician101.minetanks.spigot.tank.SpigotTankType;
import musician101.minetanks.spigot.tank.SpigotTanks;
import musician101.minetanks.spigot.util.MTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SelectTankMenu extends AbstractMenu
{
    public SelectTankMenu(SpigotMineTanks plugin, SpigotBattleField field, SpigotCountry country, SpigotTankType tankType, UUID viewer)
    {
        super(plugin, Bukkit.createInventory(null, 54, "Select Tank"), event ->
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    Player player = event.getPlayer();
                    if (!viewer.equals(player.getUniqueId()))
                        return;

                    if (!field.getPlayers().containsKey(viewer))
                        return;

                    String name = event.getItem().getItemMeta().getDisplayName();
                    SpigotPlayerTank pt = field.getPlayerTank(viewer);
                    SpigotTank tank = SpigotTanks.getTank(name);
                    if (tank == null)
                    {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                        return;
                    }

                    pt.setTank(tank);
                    player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getItem().getType(), CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(tank)));
                    player.sendMessage(ChatColor.GREEN + CommonMessages.tankSelection1(tank));
                    player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_2);
                    player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_3);
                }));

        int slot = 0;
        for (SpigotTank tank : SpigotTanks.tanks)
            if (country == tank.getCountry() || country == null)
                if (tankType == tank.getType() || tankType == null)
                    setOption(slot++, tank.getItem());

    }
}
