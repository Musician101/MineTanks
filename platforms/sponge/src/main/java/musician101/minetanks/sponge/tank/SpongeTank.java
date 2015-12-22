package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.sponge.tank.module.SpongeEngine;
import musician101.minetanks.sponge.tank.module.SpongeRadio;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.tank.module.tracks.SpongeTrackz;
import musician101.minetanks.sponge.tank.module.turret.SpongeTurret;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStack.Builder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpongeTank extends AbstractTank<SpongeTankType, SpongeCannon, SpongeEngine, SpongeRadio, SpongeTrackz, SpongeTurret, ItemStack, PotionEffect>
{
    public SpongeTank(String name, SpongeTankType type, int health, Armor armor, int speed, SpongeCannon cannon, SpongeEngine engine, SpongeRadio radio, SpongeTrackz tracks, SpongeTurret turret)
    {
        super(name, type, health, armor, speed, cannon, engine, radio, tracks, turret);
    }

    private ItemStack parseArmorValue(ItemStack item)
    {
        return parseArmorValue(item, armor);
    }

    private ItemStack parseArmorValue(ItemStack item, Armor armor)
    {
        EnchantmentData enchantments = Sponge.getDataManager().getManipulatorBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, (int) Math.round(armor.getArmorValue())))));
        ItemStack.Builder isb = ItemStack.builder();
        isb.fromItemStack(item);
        isb.itemData(enchantments);
        return isb.build();
    }

    private ItemStack parseSpeedValue(ItemStack item)
    {
        LoreData lore = Sponge.getDataManager().getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        List<Text> loreList = lore.lore().get();
        loreList.add(Texts.of(CommonItemText.speedValue(getSpeed())));
        lore.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.ITEM_LORE, loreList));
        Builder isb = ItemStack.builder();
        isb.fromItemStack(item);
        isb.itemData(lore);
        return isb.build();
    }

    @Override
    public ItemStack getHelmet()
    {
        return parseArmorValue(turret.getItem(), armor);
    }

    @Override
    public ItemStack getChestplate()
    {
        return parseArmorValue(radio.getItem());
    }

    @Override
    public ItemStack getLeggings()
    {
        return parseArmorValue(parseSpeedValue(engine.getItem()));
    }

    @Override
    public ItemStack getBoots()
    {
        return parseArmorValue(tracks.getItem());
    }

    @Override
    public List<ItemStack> getWeapons()
    {
        List<ItemStack> items = new ArrayList<>();
        items.add(getCannon().getItem());
        int ammo = getCannon().getAmmoCount();
        while (ammo > 0)
        {
            if (ammo > 64)
            {
                items.add(ItemStack.of(ItemTypes.ARROW, 64));
                ammo =- 64;
            }
            else
                items.add(ItemStack.of(ItemTypes.ARROW, ammo));
        }

        return items;
    }

    @Override
    public PotionEffect getSpeedEffect()
    {
        int amplifier = 0;
        PotionEffectType effect;
        if (getSpeed() < 6)
        {
            effect = PotionEffectTypes.SLOWNESS;
            if (getSpeed() == 1)
                amplifier = 5;
            else if (getSpeed() == 2)
                amplifier = 4;
            else if (getSpeed() == 3)
                amplifier = 3;
            else if (getSpeed() == 4)
                amplifier = 2;
            else if (getSpeed() == 5)
                amplifier = 1;
        }
        else
        {
            effect = PotionEffectTypes.SPEED;
            if (getSpeed() == 6)
                amplifier = 1;
            else if (getSpeed() == 7)
                amplifier = 2;
            else if (getSpeed() == 8)
                amplifier = 3;
            else if (getSpeed() == 9)
                amplifier = 4;
            else if (getSpeed() == 10)
                amplifier = 5;
        }

        PotionEffect.Builder peb = PotionEffect.builder();
        peb.from(PotionEffect.of(effect, amplifier, Integer.MAX_VALUE));
        peb.ambience(false);
        peb.particles(false);
        return peb.build();
    }
}
