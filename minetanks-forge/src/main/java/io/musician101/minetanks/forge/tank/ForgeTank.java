package io.musician101.minetanks.forge.tank;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.AbstractTank;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.forge.tank.modules.ForgeEngine;
import io.musician101.minetanks.forge.tank.modules.ForgeModules;
import io.musician101.minetanks.forge.tank.modules.ForgeRadio;
import io.musician101.minetanks.forge.tank.modules.cannon.ForgeCannon;
import io.musician101.minetanks.forge.tank.modules.tracks.ForgeTrackz;
import io.musician101.minetanks.forge.tank.modules.turret.ForgeTurret;
import io.musician101.minetanks.forge.util.MTUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ForgeTank extends AbstractTank<EntityPlayerMP, ForgeCountry, ForgeTankType, ForgeModules, ForgeCannon, ForgeEngine, ForgeRadio, ForgeTrackz, ForgeTurret, ItemStack>
{
    ForgeTank(String name, ForgeCountry country, ForgeTankType type, int health, Armor armor, int speed, ForgeModules modules)
    {
        super(name, country, type, health, armor, speed, modules);
        setItem(MTUtils.createCustomItem(Items.MINECART, name, CommonItemText.tankType(type)));
    }

    @Override
    public void applySpeedEffect(EntityPlayerMP player)
    {
        int amplifier;
        Potion effect;
        if (getSpeed() < 6)
        {
            effect = Potion.REGISTRY.getObject(new ResourceLocation("slowness"));
            amplifier = -getSpeed() + 6;
        }
        else
        {
            effect = Potion.REGISTRY.getObject(new ResourceLocation("speed"));
            amplifier = getSpeed() - 5;
        }

        //noinspection ConstantConditions
        player.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE, amplifier, false, false));
    }

    @Override
    public ItemStack getBoots()
    {
        return parseArmorValue(tracks.getItem());
    }

    @Override
    public ItemStack getChestplate()
    {
        return parseArmorValue(radio.getItem());
    }

    @Override
    public ItemStack getHelmet()
    {
        return parseArmorValue(turret.getItem(), armor);
    }

    @Override
    public ItemStack getLeggings()
    {
        return parseArmorValue(parseSpeedValue(engine.getItem()));
    }

    @Override
    public List<ItemStack> getWeapons()
    {
        List<ItemStack> items = new ArrayList<>();
        items.add(cannon.getItem());
        ItemStack ammo = MTUtils.createCustomItem(Items.ARROW, CommonItemText.AMMO, "");
        ammo.stackSize = cannon.getAmmoCount();
        items.add(ammo);
        return items;
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item)
    {
        return parseArmorValue(item, armor);
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item, Armor armor)
    {
        //noinspection ConstantConditions
        item.addEnchantment(Enchantment.getEnchantmentByLocation("protection"), (int) Math.round(armor.getArmorValue()));
        return item;
    }

    @Override
    protected ItemStack parseSpeedValue(ItemStack item)
    {
        NBTTagCompound tag = item.getTagCompound();
        //noinspection ConstantConditions
        NBTTagCompound display = tag.getCompoundTag("display");
        NBTTagList lore = display.getTagList("Lore", NBT.TAG_STRING);
        lore.appendTag(new NBTTagString(CommonItemText.speedValue(getSpeed())));
        display.setTag("Lore", lore);
        tag.setTag("display", display);
        item.setTagCompound(tag);
        return item;
    }
}
