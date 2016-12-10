package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import io.musician101.minetanks.spigot.tank.SpigotCountry;
import io.musician101.minetanks.spigot.tank.SpigotTank;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTanks;
import io.musician101.minetanks.spigot.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.spigot.menu.AbstractSpigotChestMenu;
import java.util.Collections;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class SelectTankMenu extends AbstractSpigotChestMenu<SpigotMineTanks> {

    private final SpigotCountry country;
    private final SpigotBattleField field;
    private final SpigotTankType tankType;

    public SelectTankMenu(SpigotBattleField field, SpigotCountry country, SpigotTankType tankType, Player player) {
        super(player, 18, "Select Tank", SpigotMineTanks.instance());
        this.field = field;
        this.country = country;
        this.tankType = tankType;
    }

    @Override
    protected void build() {
        int slot = 0;
        for (SpigotTank tank : SpigotTanks.getValues())
            if ((country == null || country.getName().equals(tank.getCountry().getName())) && (tankType == null || tankType.getName().equals(tank.getType().getName())))
                set(slot++, tank.getItem(), (player, clickType) ->
                {
                    SpigotPlayerTank pt = field.getPlayerTank(player.getUniqueId());
                    pt.setTank(tank);
                    player.getInventory().setItem(0, MTUtils.createCustomItem(tank.getType().getItem().getType(), CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(tank)));
                    player.sendMessage(ChatColor.GREEN + CommonMessages.tankSelection1(tank));
                    player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_2);
                    player.sendMessage(ChatColor.GREEN + CommonMessages.TANK_SELECTION_3);
                });

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Back");
        backMeta.setLore(Collections.singletonList("Go back to the previous menu."));
        back.setItemMeta(backMeta);
        set(inventory.getSize() - 1, back, (player, clickType) ->
        {
            if (country == null)
                new SelectTankTypeMenu(field, player);
            else if (tankType == null)
                new SelectCountryMenu(field, player);
        });
    }
}
