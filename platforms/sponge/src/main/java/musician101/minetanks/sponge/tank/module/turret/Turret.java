package musician101.minetanks.sponge.tank.module.turret;

import musician101.common.java.util.ListUtil;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.common.tank.modules.AbstractTurret;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.tank.SpongeTankType;
import musician101.minetanks.sponge.tank.SpongeTankTypes;
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
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.Collections;

public class Turret extends AbstractTurret implements ItemRepresentation
{
    SpongeTankType type;

    public Turret(String name, SpongeTankType type, Armor armor)
    {
        super(name, armor);
        this.type = type;
    }

    @Override
    public ItemStack getItem()
    {
        ItemType itemType = null;
        if (type == SpongeTankTypes.LIGHT)
            itemType = ItemTypes.LEATHER_HELMET;
        else if (type == SpongeTankTypes.MEDIUM)
            itemType = ItemTypes.IRON_HELMET;
        else if (type == SpongeTankTypes.HEAVY)
            itemType = ItemTypes.DIAMOND_HELMET;
        else if (type == SpongeTankTypes.TD)
            itemType = ItemTypes.CHAINMAIL_HELMET;
        else if (type == SpongeTankTypes.ARTY)
            itemType = ItemTypes.GOLDEN_HELMET;

        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        DataManipulatorRegistry dmr = gr.getManipulatorRegistry();

        DisplayNameData name = dmr.getBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        name.set(gr.createValueBuilder().createValue(Keys.DISPLAY_NAME, Texts.builder(getName()).color(TextColors.GREEN).build()));

        EnchantmentData enchantments = dmr.getBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(gr.createValueBuilder().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, 10))));

        LoreData lore = dmr.getBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, new ListUtil<Text>(Texts.of("Your Turret"))));

        ItemStackBuilder isb = gr.createItemBuilder();
        isb.itemType(itemType);
        isb.itemData(name);
        isb.itemData(enchantments);
        isb.itemData(lore);
        return isb.build();
    }
}
