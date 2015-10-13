package musician101.minetanks.sponge.tank.module.cannon;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.common.tank.modules.AbstractCannon;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.ItemRepresentation;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorRegistry;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;

public class SpongeCannon extends AbstractCannon implements ItemRepresentation
{
    public SpongeCannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
    }

    @Override
    public ItemStack getItem()
    {
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        DataManipulatorRegistry dmr = gr.getManipulatorRegistry();
        DisplayNameData name = dmr.getBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        EnchantmentData enchantments = dmr.getBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        LoreData lore = dmr.getBuilder(CatalogItemData.LORE_DATA).get().create();
        ItemStackBuilder isb = gr.createItemBuilder();

        name.set(gr.createValueBuilder().createValue(Keys.DISPLAY_NAME, Texts.builder(getName()).color(TextColors.GREEN).build()));
        enchantments.set(gr.createValueBuilder().createListValue(Keys.ITEM_ENCHANTMENTS, Arrays.asList(new ItemEnchantment(Enchantments.UNBREAKING, 10), new ItemEnchantment(Enchantments.INFINITY, 1))));
        lore.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, new ListUtil<Text>(Texts.of("Your Cannon"), Texts.of("Reload Time: " + getReloadTime()))));

        isb.itemType(ItemTypes.BOW);
        isb.itemData(name);
        isb.itemData(enchantments);

        return isb.build();
    }
}
