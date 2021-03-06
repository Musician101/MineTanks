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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongepowered.api.data.key.Keys;
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

//TODO cannon subtype instead of having it default to SpongeCannon, add this to Spigot version to see if this fixes the stupid reload bug
public class SpongeTank extends AbstractTank<SpongeCannon, SpongeEngine, ItemStack, SpongeModules, SpongeCountry, Player, SpongeRadio, SpongeTrackz, SpongeTurret, SpongeTankType> {

    SpongeTank(String name, SpongeCountry country, SpongeTankType type, int health, Armor armor, int speed, SpongeModules modules) {
        super(name, country, type, health, armor, speed, modules);
    }

    @Override
    public void applySpeedEffect(Player player) {
        int amplifier;
        PotionEffectType effect;
        if (getSpeed() < 6) {
            effect = PotionEffectTypes.SLOWNESS;
            amplifier = -getSpeed() + 6;
        }
        else {
            effect = PotionEffectTypes.SPEED;
            amplifier = getSpeed() - 5;
        }

        player.offer(Keys.POTION_EFFECTS, Collections.singletonList(PotionEffect.of(effect, amplifier, Integer.MAX_VALUE)));
    }

    @Override
    public List<ItemStack> getWeapons() {
        List<ItemStack> items = new ArrayList<>();
        items.add(getCannon().getItem());
        int ammo = getCannon().getAmmoCount();
        while (ammo > 0) {
            if (ammo > 64) {
                items.add(ItemStack.of(ItemTypes.ARROW, 64));
                ammo = -64;
            }
            else
                items.add(ItemStack.of(ItemTypes.ARROW, ammo));
        }

        return items;
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item) {
        return parseArmorValue(item, armor);
    }

    @Override
    protected ItemStack parseArmorValue(ItemStack item, Armor armor) {
        item.offer(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, (int) Math.round(armor.getArmorValue()))));
        return item;
    }

    @Override
    protected ItemStack parseSpeedValue(ItemStack item) {
        Builder isb = ItemStack.builder();
        isb.fromItemStack(item);
        isb.add(Keys.ITEM_LORE, Collections.singletonList(Text.of(CommonItemText.speedValue(getSpeed()))));
        return isb.build();
    }
}
