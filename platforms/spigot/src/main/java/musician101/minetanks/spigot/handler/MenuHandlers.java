package musician101.minetanks.spigot.handler;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.SpigotReference;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.tank.Tank;
import musician101.minetanks.spigot.tank.Tanks;
import musician101.minetanks.spigot.util.IconMenu.OptionClickEvent;
import musician101.minetanks.spigot.util.IconMenu.OptionClickEventHandler;
import musician101.minetanks.spigot.util.MTUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MenuHandlers
{
    public static class TankSelectionHandler implements OptionClickEventHandler
    {
        final SpigotMineTanks plugin;

        public TankSelectionHandler(SpigotMineTanks plugin)
        {
            this.plugin = plugin;
        }

        @Override
        public void onOptionClick(OptionClickEvent event)
        {
            String optionName = event.getName();
            event.getName();
            Player player = event.getPlayer();
            Tank tank = null;
            for (Tank iTank : Tanks.tankList)
                if (optionName.equals(iTank.getName()))
                    tank = iTank;

            for (String name : plugin.getFieldStorage().getFields().keySet())
            {
                SpigotPlayerTank pt = plugin.getFieldStorage().getField(name).getPlayerTank(player.getUniqueId());
                if (pt != null)
                {
                    pt.setTank(tank);
                    player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getItem().getType(), CommonItemText.OPEN_HANGAR, SpigotReference.tank(CommonItemText.SELECTED_TANK, tank)));
                    player.sendMessage(new String[]{ChatColor.GREEN + SpigotReference.tank(CommonMessages.TANK_SELECTION_1, tank),
                            ChatColor.GREEN + CommonMessages.TANK_SELECTION_2,
                            ChatColor.GREEN + CommonMessages.TANK_SELECTION_3});

                    return;
                }
            }

            player.sendMessage(ChatColor.RED + CommonMessages.INVALID_TANK);
            event.setWillClose(false);
        }
    }
}
