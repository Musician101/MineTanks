package musician101.minetanks.sponge.tank.module.turret;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.common.tank.modules.AbstractTurret;
import musician101.minetanks.sponge.tank.SpongeTankType;
import musician101.minetanks.sponge.tank.SpongeTankTypes;
import org.spongepowered.api.Game;
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
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.Collections;

public class SpongeTurret extends AbstractTurret<ItemStack>
{
    public SpongeTurret(String name, SpongeTankType type, Armor armor)
    {
        super(name, armor);
        parseTurret(type);
    }

    private void parseTurret(SpongeTankType type)
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

        Game game = Sponge.getGame();
        DataManager dmr = game.getDataManager();
        GameRegistry gr = game.getRegistry();

        DisplayNameData name = dmr.getManipulatorBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        name.set(gr.getValueFactory().createValue(Keys.DISPLAY_NAME, Texts.builder(getName()).color(TextColors.GREEN).build()));

        EnchantmentData enchantments = dmr.getManipulatorBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(gr.getValueFactory().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, 10))));

        LoreData lore = dmr.getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE, Collections.singletonList(Texts.of(CommonItemText.TURRET))));

        ItemStack.Builder isb = ItemStack.builder();
        isb.itemType(itemType);
        isb.itemData(name);
        isb.itemData(enchantments);
        isb.itemData(lore);
        setItem(isb.build());
    }
}