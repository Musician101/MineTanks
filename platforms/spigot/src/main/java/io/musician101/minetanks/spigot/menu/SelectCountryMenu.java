package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotCountries;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SelectCountryMenu extends AbstractMenu
{
    public SelectCountryMenu(SpigotMineTanks plugin, SpigotBattleField field, UUID viewer)
    {
        super(plugin, Bukkit.createInventory(null, 9, "Select Country"), event ->
        {
            Player player = event.getPlayer();
            if (!viewer.equals(player.getUniqueId()))
                return;

            if (!field.getPlayers().containsKey(viewer))
                return;

            String name = event.getItem().getItemMeta().getDisplayName();
            SpigotCountry country = SpigotCountries.getCountry(ChatColor.stripColor(name));
            if (name.equalsIgnoreCase("Back"))
            {
                new MainSelectionMenu(plugin, field, viewer).open(player);
                event.setWillDestroy(true);
            }
            else if (country != null)
            {
                new SelectTankMenu(plugin, field, country, null, viewer).open(player);
                event.setWillDestroy(true);
            }
        });

        int slot = 0;
        for (SpigotCountry country : SpigotCountries.getValues())
            setOption(slot++, country.getItem());

        addBackButton();
    }
}
