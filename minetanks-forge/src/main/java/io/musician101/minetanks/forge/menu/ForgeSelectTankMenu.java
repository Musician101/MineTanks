package io.musician101.minetanks.forge.menu;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.battlefield.player.ForgePlayerTank;
import io.musician101.minetanks.forge.tank.ForgeCountry;
import io.musician101.minetanks.forge.tank.ForgeTank;
import io.musician101.minetanks.forge.tank.ForgeTankType;
import io.musician101.minetanks.forge.tank.ForgeTanks;
import io.musician101.minetanks.forge.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.Forge.menu.AbstractForgeChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

class ForgeSelectTankMenu extends AbstractForgeChestMenu<ForgeMineTanks>
{
    private final ForgeCountry country;
    private final ForgeBattleField field;
    private final ForgeTankType tankType;

    public ForgeSelectTankMenu(ForgeBattleField field, ForgeCountry country, ForgeTankType tankType, Player player)
    {
        super(player, 18, "Select Tank", ForgeMineTanks.instance());
        this.field = field;
        this.country = country;
        this.tankType = tankType;
    }

    @Override
    protected void build()
    {
        int slot = 0;
        for (ForgeTank tank : ForgeTanks.getValues())
            if ((country == null || country.getName().equals(tank.getCountry().getName())) && (tankType == null || tankType.getName().equals(tank.getType().getName())))
                set(slot++, tank.getItem(), (player, clickType) ->
                {
                    ForgePlayerTank pt = field.getPlayerTank(player.getUniqueId());
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
                new ForgeSelectTankTypeMenu(field, player);
            else if (tankType == null)
                new ForgeSelectCountryMenu(field, player);
        });
    }
}
