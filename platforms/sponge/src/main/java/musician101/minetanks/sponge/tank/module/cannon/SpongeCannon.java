package musician101.minetanks.sponge.tank.module.cannon;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.modules.AbstractCannon;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;

public class SpongeCannon extends AbstractCannon<ItemStack>
{
    public SpongeCannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
        parseCannon();
    }

    private void parseCannon()
    {
        DataManager dm = Sponge.getDataManager();
        GameRegistry gr = Sponge.getGame().getRegistry();

        DisplayNameData name = dm.getManipulatorBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        name.set(gr.getValueFactory().createValue(Keys.DISPLAY_NAME, Text.builder(getName()).color(TextColors.GREEN).build()));

        EnchantmentData enchantments = dm.getManipulatorBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(gr.getValueFactory().createListValue(Keys.ITEM_ENCHANTMENTS, Arrays.asList(new ItemEnchantment(Enchantments.UNBREAKING, 10), new ItemEnchantment(Enchantments.INFINITY, 1))));

        LoreData lore = dm.getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE, new ListUtil<>(Text.of(CommonItemText.CANNON), Text.of(CommonItemText.reloadTime(getReloadTime())))));

        ItemStack.Builder isb = ItemStack.builder();
        isb.itemType(ItemTypes.BOW);
        isb.itemData(name);
        isb.itemData(enchantments);
        isb.itemData(lore);
        setItem(isb.build());
    }
}
