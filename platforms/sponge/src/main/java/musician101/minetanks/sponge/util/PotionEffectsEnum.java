package musician101.minetanks.sponge.util;

import org.spongepowered.api.potion.PotionEffectType;
import org.spongepowered.api.potion.PotionEffectTypes;

public enum PotionEffectsEnum
{
    SPEED(PotionEffectTypes.SPEED),
    SLOWNESS(PotionEffectTypes.SLOWNESS),
    HASTE(PotionEffectTypes.HASTE),
    MINING_FATIGUE(PotionEffectTypes.MINING_FATIGUE),
    STRENGTH(PotionEffectTypes.STRENGTH),
    INSTANT_HEALTH(PotionEffectTypes.INSTANT_HEALTH),
    INSTANT_DAMAGE(PotionEffectTypes.INSTANT_DAMAGE),
    JUMP_BOOST(PotionEffectTypes.JUMP_BOOST),
    NAUSEA(PotionEffectTypes.NAUSEA),
    REGENERATION(PotionEffectTypes.REGENERATION),
    RESISTANCE(PotionEffectTypes.RESISTANCE),
    FIRE_RESISTANCE(PotionEffectTypes.FIRE_RESISTANCE),
    WATER_BREATHING(PotionEffectTypes.WATER_BREATHING),
    INVISIBILITY(PotionEffectTypes.INVISIBILITY),
    BLINDNESS(PotionEffectTypes.BLINDNESS),
    NIGHT_VISION(PotionEffectTypes.NIGHT_VISION),
    HUNGER(PotionEffectTypes.HUNGER),
    WEAKNESS(PotionEffectTypes.WEAKNESS),
    POISON(PotionEffectTypes.POISON),
    WITHER(PotionEffectTypes.WITHER),
    HEALTH_BOOST(PotionEffectTypes.HEALTH_BOOST),
    ABSORPTION(PotionEffectTypes.ABSORPTION),
    SATURATION(PotionEffectTypes.SATURATION);

    PotionEffectType type;

    private PotionEffectsEnum(PotionEffectType type)
    {
        this.type = type;
    }

    public PotionEffectType getPotionEffectType()
    {
        return type;
    }
}
