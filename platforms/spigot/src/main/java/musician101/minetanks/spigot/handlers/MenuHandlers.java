package musician101.minetanks.spigot.handlers;

import musician101.minetanks.spigot.MineTanks;
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
        MineTanks plugin;

        public TankSelectionHandler(MineTanks plugin)
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
                if (optionName == iTank.getName())
                    tank = iTank;

            for (String name : plugin.getFieldStorage().getFields().keySet())
            {
                plugin.getFieldStorage().getField(name).getPlayer(player.getUniqueId()).setTank(tank);
                player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getIcon().getType(), "Open Hangar.", "Tank: " + tank.getName()));
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " You have chosen the " + tank.getName() + ".");
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " If you wish to choose another tank, right click with the 'Open Hangar' item.");
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " When you are ready, simply right click the 'Ready' item.");
                return;
            }

            player.sendMessage(ChatColor.RED + plugin.getPrefix() + "Please click a valid tank.");
            event.setWillClose(false);
        }
    }
}
