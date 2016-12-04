package io.musician101.minetanks.forge.menu;

import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.tank.ForgeCountries;
import io.musician101.minetanks.forge.tank.ForgeCountry;
import io.musician101.musicianlibrary.java.minecraft.Forge.menu.AbstractForgeChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

class ForgeSelectCountryMenu extends AbstractForgeChestMenu<ForgeMineTanks>
{
    private final ForgeBattleField field;

    public ForgeSelectCountryMenu(ForgeBattleField field, Player player)
    {
        super(player, 9, "Select Country", ForgeMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build()
    {
        int slot = 0;
        for (ForgeCountry country : ForgeCountries.getValues())
            set(slot++, country.getItem(), (player, clickType) -> new ForgeSelectTankMenu(field, country, null, player));

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Back");
        backMeta.setLore(Collections.singletonList("Go back to the previous menu."));
        back.setItemMeta(backMeta);
        set(inventory.getSize() - 1, back, (player, clickType) -> new ForgeMainSelectionMenu(field, player));
    }
}
