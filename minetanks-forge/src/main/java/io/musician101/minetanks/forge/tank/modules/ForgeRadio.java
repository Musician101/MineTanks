package io.musician101.minetanks.forge.tank.modules;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.modules.AbstractRadio;
import io.musician101.minetanks.forge.tank.ForgeTankType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;

public class ForgeRadio extends AbstractRadio<ItemStack, ForgeTankType>
{
    public ForgeRadio(String name, ForgeTankType type)
    {
        super(name);
        parseRadio(type);
    }

    @Override
    protected void parseRadio(ForgeTankType type)
    {
        ItemStack item = new ItemStack(type.getItem().getItem());
        item.setTagInfo("Unbreakable", new NBTTagInt(1));
        item.setStackDisplayName(TextFormatting.GREEN + getName());
        NBTTagCompound display = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        lore.appendTag(new NBTTagString(CommonItemText.RADIO));
        display.setTag("Lore", lore);
        item.setTagInfo("Display", display);
        setItem(item);
    }
}
