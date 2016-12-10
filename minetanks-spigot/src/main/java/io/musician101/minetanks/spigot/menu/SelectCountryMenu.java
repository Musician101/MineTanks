package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotCountries;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
import io.musician101.musicianlibrary.java.minecraft.spigot.menu.AbstractSpigotChestMenu;
import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class SelectCountryMenu extends AbstractSpigotChestMenu<SpigotMineTanks> {

    private final SpigotBattleField field;

    public SelectCountryMenu(SpigotBattleField field, Player player) {
        super(player, 9, "Select Country", SpigotMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build() {
        int slot = 0;
        for (SpigotCountry country : SpigotCountries.getValues())
            set(slot++, country.getItem(), (player, clickType) -> new SelectTankMenu(field, country, null, player));

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Back");
        backMeta.setLore(Collections.singletonList("Go back to the previous menu."));
        back.setItemMeta(backMeta);
        set(inventory.getSize() - 1, back, (player, clickType) -> new MainSelectionMenu(field, player));
    }
}
