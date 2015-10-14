package musician101.minetanks.sponge.tank;

import musician101.minetanks.common.tank.AbstractTank;
import musician101.minetanks.common.tank.Armor;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.tank.module.Engine;
import musician101.minetanks.sponge.tank.module.Radio;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.tank.module.tracks.Trackz;
import musician101.minetanks.sponge.tank.module.turret.Turret;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.potion.PotionEffectBuilder;
import org.spongepowered.api.potion.PotionEffectType;
import org.spongepowered.api.potion.PotionEffectTypes;
import org.spongepowered.api.text.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tank extends AbstractTank
{
    Armor armor;

    public Tank(String name, SpongeTankType type, int health, Armor armor, int speed, SpongeCannon cannon, Engine engine, Radio radio, Trackz tracks, Turret turret, String... description)
    {
        super(name, type, health, speed, cannon, engine, radio, tracks, turret, description);
        this.armor = armor;
    }

    private ItemStack[] parseArmor(double armor, int speed, Engine engine, Radio radio, Trackz tracks, Turret turret)
    {
        return new ItemStack[]{parseArmorValue(tracks.getItem(), armor), parseArmorValue(parseSpeedValue(engine.getIcon(), speed), armor), parseArmorValue(radio.getIcon(), armor), parseArmorValue(turret.getIcon(), (turret.getArmor().getArmorValue() != 0 ? turret.getArmor().getArmorValue() : armor))};
    }

    private ItemStack parseArmorValue(ItemStack item)
    {
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        EnchantmentData enchantments = gr.getManipulatorRegistry().getBuilder(CatalogItemData.ENCHANTMENT_DATA).get().create();
        enchantments.set(gr.createValueBuilder().createListValue(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, (int) Math.round(armor.getArmorValue())))));
        ItemStackBuilder isb = gr.createItemBuilder();
        isb.fromItemStack(item);
        isb.itemData(enchantments);
        return isb.build();
    }

    private ItemStack parseSpeedValue(ItemStack item)
    {
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        LoreData lore = gr.getManipulatorRegistry().getBuilder(CatalogItemData.LORE_DATA).get().create();
        lore.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, Arrays.asList(item.get(Keys.ITEM_LORE).get().get(0), Texts.of("Speed Value: " + getSpeed()))));
        ItemStackBuilder isb = gr.createItemBuilder();
        isb.fromItemStack(item);
        isb.itemData(lore);
        return isb.build();
    }

    //TODO left off here
    public ItemStack getHelmet()
    {
        return null;
    }

    public SpongeCannon getCannon()
    {
        return (SpongeCannon) cannon;
    }

    public List<ItemStack> getWeapons()
    {
        List<ItemStack> items = new ArrayList<>();
        items.add(getCannon().getItem());
        int ammo = getCannon().getAmmoCount();
        while (ammo > 0)
        {
            if (ammo > 64)
            {
                items.add(MTUtils.createCustomItem(ItemTypes.ARROW, 64));
                ammo =- 64;
            }
            else
                items.add(MTUtils.createCustomItem(ItemTypes.ARROW, ammo));
        }

        return items;
    }

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

        PotionEffectBuilder peb = SpongeMineTanks.getGame().getRegistry().createPotionEffectBuilder();
        peb.potionType(effect);
        peb.amplifier(amplifier);
        peb.duration(Integer.MAX_VALUE);
        peb.ambience(false);
        return peb.build();
    }

    public SpongeTankType getType()
    {
        return (SpongeTankType) type;
    }
}
