package musician101.minetanks.spigot.menu;

import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.tank.SpigotCountries;
import musician101.minetanks.spigot.tank.SpigotCountry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainSelectionMenu extends AbstractMenu
{
    public MainSelectionMenu(SpigotMineTanks plugin, SpigotBattleField field, UUID viewer)
    {
        super(plugin, Bukkit.createInventory(null, 9, "Tank Selection"), event ->
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
            {
                Player player = event.getPlayer();
                if (!viewer.equals(player.getUniqueId()))
                    return;

                if (!field.getPlayers().containsKey(viewer))
                    return;

                String name = event.getItem().getItemMeta().getDisplayName();
                if (name.equals("Countries"))
                    new SelectCountryMenu(plugin, field, viewer).open(player);
                else if (name.equals("Tank Types"))
                    new SelectTankTypeMenu(plugin, field, viewer).open(player);
                else
                {
                    event.setWillClose(false);
                    event.setWillDestroy(false);
                }
            });
        });

        List<SpigotCountry> countries = SpigotCountries.ALL;
        setOption(0, new ItemStack(countries.get(new Random().nextInt(countries.size() - 1)).getItem())
        {
            {
                BannerMeta bannerMeta = (BannerMeta) getItemMeta();
                bannerMeta.setDisplayName("Countries");
                setItemMeta(bannerMeta);
            }
        });
        setOption(1, new ItemStack(Material.MINECART), "Tank Types", "View tanks by their types.", "Light, Medium, etc.");
        for (int slot = 2; slot < inv.getSize(); slot++)
            setOption(slot, new ItemStack(Material.THIN_GLASS));
    }
}
