package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

//TODO add description to banner icon in MainSelectionMenu
class SelectTankTypeMenu extends AbstractMenu
{
    SelectTankTypeMenu(SpigotMineTanks plugin, SpigotBattleField field, UUID viewer)
    {
        super(plugin, Bukkit.createInventory(null, 9, "Select Tank Type"), event ->
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    Player player = event.getPlayer();
                    if (!viewer.equals(player.getUniqueId()))
                        return;

                    if (!field.getPlayers().containsKey(viewer))
                        return;

                    String name = event.getItem().getItemMeta().getDisplayName();
                    SpigotTankType type = SpigotTankTypes.getTankType(ChatColor.stripColor(name));
                    if (name.equals("Back"))
                        new MainSelectionMenu(plugin, field, viewer).open(player);
                    else if (type != null)
                        new SelectTankMenu(plugin, field, null, type, viewer).open(player);
                    else
                    {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                }));
        //TODO add GUI for all commands
        for (int slot = 0; slot < inv.getSize() - 1; slot++)
        {
            try
            {
                setOption(slot, SpigotTankTypes.getValues().get(slot).getItem());
            }
            catch (IndexOutOfBoundsException e)
            {
                setOption(slot, new ItemStack(Material.THIN_GLASS));
            }
        }

        addBackButton();
    }
}
