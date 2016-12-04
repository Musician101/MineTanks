package io.musician101.minetanks.forge.util;

import java.util.Arrays;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;

public class MTUtils
{
    private MTUtils()
    {

    }

    public static ItemStack createCustomItem(Item item, String displayName, String... description)
    {
        ItemStack itemStack = new ItemStack(item, 1);
        itemStack.setStackDisplayName(TextFormatting.GREEN + displayName);
        NBTTagCompound display = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        Arrays.asList(description).forEach(string -> lore.appendTag(new NBTTagString()));
        display.setTag("Lore", lore);
        itemStack.setTagInfo("display", display);
        return itemStack;
    }
}
