package io.musician101.minetanks.forge.tank.modules.turret;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.common.tank.modules.AbstractTurret;
import io.musician101.minetanks.forge.tank.ForgeTankType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;

public class ForgeTurret extends AbstractTurret<ItemStack, ForgeTankType>
{
    ForgeTurret(String name, ForgeTankType type, Armor armor)
    {
        super(name, armor);
        parseTurret(type);
    }

    @Override
    protected void parseTurret(ForgeTankType type)
    {
        ItemStack item = new ItemStack(type.getItem().getItem());
        item.setTagInfo("Unbreakable", new NBTTagInt(1));
        item.setStackDisplayName(TextFormatting.GREEN + getName());
        NBTTagCompound display = new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        lore.appendTag(new NBTTagString(CommonItemText.TURRET));
        display.setTag("Lore", lore);
        item.setTagInfo("Display", display);
        setItem(item);
    }
}
