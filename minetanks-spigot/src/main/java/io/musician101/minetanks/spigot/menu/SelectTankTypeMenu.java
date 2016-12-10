package io.musician101.minetanks.spigot.menu;

import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.tank.SpigotTankType;
import io.musician101.minetanks.spigot.tank.SpigotTankTypes;
import io.musician101.musicianlibrary.java.minecraft.spigot.menu.AbstractSpigotChestMenu;
import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

//TODO add description to banner icon in MainSelectionMenu
class SelectTankTypeMenu extends AbstractSpigotChestMenu<SpigotMineTanks> {

    private final SpigotBattleField field;

    public SelectTankTypeMenu(SpigotBattleField field, Player player) {
        super(player, 9, "Select Tank Type", SpigotMineTanks.instance());
        //TODO add GUI for all commands
        this.field = field;
    }

    @Override
    protected void build() {
        for (int slot = 0; slot < inventory.getSize() - 1; slot++) {
            try {
                SpigotTankType spigotTankType = SpigotTankTypes.getValues().get(slot);
                set(slot, spigotTankType.getItem(), (player, clickType) -> new SelectTankMenu(field, null, spigotTankType, player));
            }
            catch (IndexOutOfBoundsException e) {
                set(slot, new ItemStack(Material.THIN_GLASS));
            }
        }

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Back");
        backMeta.setLore(Collections.singletonList("Go back to the previous menu."));
        back.setItemMeta(backMeta);
        set(inventory.getSize() - 1, back, (player, clickType) -> new MainSelectionMenu(field, player));
    }
}
