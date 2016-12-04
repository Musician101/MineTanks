package io.musician101.minetanks.forge.menu;

import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.tank.ForgeTankType;
import io.musician101.minetanks.forge.tank.ForgeTankTypes;
import io.musician101.musicianlibrary.java.minecraft.Forge.menu.AbstractForgeChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

//TODO add description to banner icon in ForgeMainSelectionMenu
class ForgeSelectTankTypeMenu extends AbstractForgeChestMenu<ForgeMineTanks>
{
    private final ForgeBattleField field;

    public ForgeSelectTankTypeMenu(ForgeBattleField field, Player player)
    {
        super(player, 9, "Select Tank Type", ForgeMineTanks.instance());
        //TODO add GUI for all commands
        this.field = field;
    }

    @Override
    protected void build()
    {
        for (int slot = 0; slot < inventory.getSize() - 1; slot++)
        {
            try
            {
                ForgeTankType ForgeTankType = ForgeTankTypes.getValues().get(slot);
                set(slot, ForgeTankType.getItem(), (player, clickType) -> new ForgeSelectTankMenu(field, null, ForgeTankType, player));
            }
            catch (IndexOutOfBoundsException e)//NOSONAR
            {
                set(slot, new ItemStack(Material.THIN_GLASS));
            }
        }

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Back");
        backMeta.setLore(Collections.singletonList("Go back to the previous menu."));
        back.setItemMeta(backMeta);
        set(inventory.getSize() - 1, back, (player, clickType) -> new ForgeMainSelectionMenu(field, player));
    }
}
