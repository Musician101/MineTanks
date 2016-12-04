package io.musician101.minetanks.forge.tank.modules.cannon;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractCannon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class ForgeCannon extends AbstractCannon<ItemStack>
{
    public ForgeCannon(String name, int ammoCount, double reloadTime)
    {
        super(name, ammoCount, reloadTime);
        parseCannon();
    }

    @Override
    protected void parseCannon()
    {
        ItemStack item = new ItemStack(Items.BOW);
        item.setStackDisplayName(TextFormatting.GREEN + getName());
        //noinspection ConstantConditions
        item.addEnchantment(Enchantment.REGISTRY.getObject(new ResourceLocation("infinity")), 1);
        item.setTagInfo("Unbreakable", new NBTTagInt(1));
        NBTTagCompound display = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        lore.appendTag(new NBTTagString(CommonItemText.CANNON));
        lore.appendTag(new NBTTagString(CommonItemText.reloadTime(getReloadTime())));
        display.setTag("Lore", lore);
        item.setTagInfo("Display", display);
        setItem(item);
    }
}
