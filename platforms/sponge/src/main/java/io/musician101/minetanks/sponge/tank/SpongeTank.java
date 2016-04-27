package io.musician101.minetanks.sponge.tank;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.tank.AbstractTank;
import io.musician101.minetanks.common.tank.Armor;
import io.musician101.minetanks.sponge.tank.module.SpongeEngine;
import io.musician101.minetanks.sponge.tank.module.SpongeModules;
import io.musician101.minetanks.sponge.tank.module.SpongeRadio;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import io.musician101.minetanks.sponge.tank.module.tracks.SpongeTrackz;
import io.musician101.minetanks.sponge.tank.module.turret.SpongeTurret;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStack.Builder;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpongeTank extends AbstractTank<SpongeCountry, SpongeTankType, SpongeModules, SpongeCannon, SpongeEngine, SpongeRadio, SpongeTrackz, SpongeTurret, ItemStack>
{
    SpongeTank(String name, SpongeCountry country, SpongeTankType type, int health, Armor armor, int speed, SpongeModules modules)
    {
        super(name, country, type, health, armor, speed, modules);
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item)
    {
        return parseArmorValue(item, armor);
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item, Armor armor)
    {
        EnchantmentData enchantments = Sponge.getDataManager().getManipulatorBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(Sponge.getGame().getRegistry().getValueFactory().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, (int) Math.round(armor.getArmorValue())))));
        ItemStack.Builder isb = ItemStack.builder();
        isb.fromItemStack(item);
        isb.itemData(enchantments);
        return isb.build();
    }

    @Override
    protected ItemStack parseSpeedValue(ItemStack item)
    {
        LoreData lore = Sponge.getDataManager().getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        List<Text> loreList = lore.lore().get();
        loreList.add(Text.of(CommonItemText.speedValue(getSpeed())));
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
    public void applySpeedEffect(UUID uuid)
    {
        int amplifier;
        PotionEffectType effect;
        if (getSpeed() < 6)
        {
            effect = PotionEffectTypes.SLOWNESS;
            amplifier = -getSpeed() + 6;
        }
        else
        {
            effect = PotionEffectTypes.SPEED;
            amplifier = getSpeed() - 5;
        }

        PotionEffect.Builder peb = PotionEffect.builder();
        peb.from(PotionEffect.of(effect, amplifier, Integer.MAX_VALUE));
        peb.ambience(false);
        peb.particles(false);
        Optional<Player> playerOptional = Sponge.getServer().getPlayer(uuid);
        if (!playerOptional.isPresent())
            return;

        playerOptional.get().offer(Keys.POTION_EFFECTS, Collections.singletonList(peb.build()));
    }
}
