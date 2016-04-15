package musician101.minetanks.spigot.menu;

import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.tank.SpigotCountries;
import musician101.minetanks.spigot.tank.SpigotCountry;
import musician101.minetanks.spigot.tank.SpigotTankType;
import musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SelectTankTypeMenu extends AbstractMenu
{
    public SelectTankTypeMenu(SpigotMineTanks plugin, SpigotBattleField field, UUID viewer)
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
                    SpigotTankType type = SpigotTankTypes.getByName(name);
                    if (name.equalsIgnoreCase("Back"))
                        new MainSelectionMenu(plugin, field, viewer);
                    else if (type != null)
                        new SelectTankMenu(plugin, field, null, type, viewer);
                    else
                    {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                }));

        int slot = 0;
        for (SpigotCountry country : SpigotCountries.ALL)
            setOption(slot++, country.getItem());

        addBackButton();
    }
}
