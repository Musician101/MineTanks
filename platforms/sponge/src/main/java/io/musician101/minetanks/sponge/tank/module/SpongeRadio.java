package io.musician101.minetanks.sponge.tank.module;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;
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
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Collections;

public class SpongeRadio extends AbstractRadio<ItemStack>
{
    public SpongeRadio(String name, SpongeTankType type)
    {
        super(name);
        parseRadio(type);
    }

    private void parseRadio(SpongeTankType type)
    {
        ItemType itemType = ItemTypes.STICK;
        if (type == SpongeTankTypes.LIGHT)
            itemType = ItemTypes.LEATHER_CHESTPLATE;
        else if (type == SpongeTankTypes.MEDIUM)
            itemType = ItemTypes.IRON_CHESTPLATE;
        else if (type == SpongeTankTypes.HEAVY)
            itemType = ItemTypes.DIAMOND_CHESTPLATE;
        else if (type == SpongeTankTypes.TD)
            itemType = ItemTypes.CHAINMAIL_CHESTPLATE;
        else if (type == SpongeTankTypes.ARTY)
            itemType = ItemTypes.GOLDEN_CHESTPLATE;

        DataManager dm = Sponge.getDataManager();
        GameRegistry gr = Sponge.getGame().getRegistry();

        DisplayNameData name = dm.getManipulatorBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        name.set(gr.getValueFactory().createValue(Keys.DISPLAY_NAME, Text.builder(getName()).color(TextColors.GREEN).build()));

        EnchantmentData enchantments = dm.getManipulatorBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(gr.getValueFactory().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, 10))));

        LoreData lore = dm.getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.RADIO))));

        ItemStack.Builder isb = ItemStack.builder();
        isb.itemType(itemType);
        isb.itemData(name);
        isb.itemData(enchantments);
        isb.itemData(lore);
        setItem(isb.build());
    }
}
