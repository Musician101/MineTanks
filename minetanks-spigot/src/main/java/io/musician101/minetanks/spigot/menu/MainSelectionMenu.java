package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotCountries;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
import io.musician101.musicianlibrary.java.minecraft.spigot.menu.AbstractSpigotChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainSelectionMenu extends AbstractSpigotChestMenu<SpigotMineTanks>
{
    private final SpigotBattleField field;

    public MainSelectionMenu(SpigotBattleField field, Player player)
    {
        super(player, 9, CommonReference.TANK_SELECTION, SpigotMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build()
    {
        List<SpigotCountry> countries = SpigotCountries.getValues();
        ItemStack bannerStack = new ItemStack(countries.get(new Random().nextInt(countries.size() - 1)).getItem());
        BannerMeta bannerMeta = (BannerMeta) bannerStack.getItemMeta();
        bannerMeta.setDisplayName("Countries");
        bannerStack.setItemMeta(bannerMeta);
        set(0, bannerStack, (player, clickType) -> new SelectCountryMenu(field, player));

        ItemStack minecart = new ItemStack(Material.MINECART);
        ItemMeta minecartMeta = minecart.getItemMeta();
        minecartMeta.setDisplayName("Tank Types");
        minecartMeta.setLore(Arrays.asList("View tanks by their types.", "Light, Medium, etc."));
        minecart.setItemMeta(minecartMeta);
        set(1, minecart, (player, clickType) -> new SelectTankTypeMenu(field, player));

        for (int slot = 2; slot < inventory.getSize(); slot++)
            set(slot, new ItemStack(Material.THIN_GLASS));
    }
}
