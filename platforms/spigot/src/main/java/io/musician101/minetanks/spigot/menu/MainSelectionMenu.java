package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.tank.SpigotCountries;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
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
            Player player = event.getPlayer();
            if (!viewer.equals(player.getUniqueId()))
                return;

            if (!field.getPlayers().containsKey(viewer))
                return;

            String name = event.getItem().getItemMeta().getDisplayName();
            switch (name)
            {
                case "Countries":
                {
                    new SelectCountryMenu(plugin, field, viewer).open(player);
                    event.setWillDestroy(true);
                    break;
                }
                case "Tank Types":
                {
                    new SelectTankTypeMenu(plugin, field, viewer).open(player);
                    event.setWillDestroy(true);
                    break;
                }
                default:
                    break;
            }
        });

        List<SpigotCountry> countries = SpigotCountries.getValues();
        ItemStack bannerStack = new ItemStack(countries.get(new Random().nextInt(countries.size() - 1)).getItem());
        BannerMeta bannerMeta = (BannerMeta) bannerStack.getItemMeta();
        bannerMeta.setDisplayName("Countries");
        bannerStack.setItemMeta(bannerMeta);
        setOption(0, bannerStack);
        setOption(1, new ItemStack(Material.MINECART), "Tank Types", "View ALL by their types.", "Light, Medium, etc.");
        for (int slot = 2; slot < inv.getSize(); slot++)
            setOption(slot, new ItemStack(Material.THIN_GLASS));
    }
}
