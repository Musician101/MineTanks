package io.musician101.minetanks.sponge.tank;

import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.PatternLayer;
import org.spongepowered.api.data.type.BannerPatternShapes;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SpongeCountries extends AbstractInfoStorage<SpongeCountry>
{
    private SpongeCountries()
    {
        super(new SpongeCountry("China",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("China"))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.RED)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.SQUARE_TOP_RIGHT, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_MIDDLE, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.HALF_HORIZONTAL_MIRROR, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.HALF_VERTICAL, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.TRIANGLE_BOTTOM, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.BORDER, DyeColors.RED))).build()),
                new SpongeCountry("Czechoslovakia",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("Czechoslovakia"))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.WHITE)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.DIAGONAL_LEFT_MIRROR, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.DIAGONAL_RIGHT_MIRROR, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.HALF_VERTICAL, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.TRIANGLE_BOTTOM, DyeColors.BLUE))).build()),
                new SpongeCountry("France",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("France"))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.WHITE)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.STRIPE_TOP, DyeColors.BLUE),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_TOP, DyeColors.RED))).build()),
                new SpongeCountry("Germany",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("Germany"))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.WHITE)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.STRIPE_TOP, DyeColors.BLUE),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_BOTTOM, DyeColors.RED))).build()),
                new SpongeCountry("Japan",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("Japan"))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.WHITE)
                                .add(Keys.BANNER_PATTERNS, Collections.singletonList(
                                        PatternLayer.of(BannerPatternShapes.CIRCLE_MIDDLE, DyeColors.RED))).build()),
                new SpongeCountry("U.K.",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("U.K."))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.BLUE)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.STRIPE_DOWNLEFT, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_DOWNRIGHT, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.CROSS, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_CENTER, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_MIDDLE, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.STRAIGHT_CROSS, DyeColors.RED))).build()),
                new SpongeCountry("U.S.A.",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("U.S.A."))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.RED)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.STRIPE_SMALL, DyeColors.WHITE),
                                        PatternLayer.of(BannerPatternShapes.SQUARE_TOP_RIGHT, DyeColors.BLUE))).build()),
                new SpongeCountry("U.S.S.R.",
                        ItemStack.builder().itemType(ItemTypes.BANNER).add(Keys.DISPLAY_NAME, Text.of("U.S.S.R."))
                                .add(Keys.BANNER_BASE_COLOR, DyeColors.RED)
                                .add(Keys.BANNER_PATTERNS, Arrays.asList(
                                        PatternLayer.of(BannerPatternShapes.CROSS, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.CIRCLE_MIDDLE, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.DIAGONAL_LEFT_MIRROR, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.FLOWER, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.STRIPE_DOWNLEFT, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.SQUARE_BOTTOM_LEFT, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.SQUARE_BOTTOM_RIGHT, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.DIAGONAL_LEFT, DyeColors.RED),
                                        PatternLayer.of(BannerPatternShapes.MOJANG, DyeColors.YELLOW),
                                        PatternLayer.of(BannerPatternShapes.CURLY_BORDER, DyeColors.RED))).build()));
    }


    public static SpongeCountry getCountry(String name)
    {
        return new SpongeCountries().get(name);
    }

    public static List<SpongeCountry> getValues()
    {
        return new SpongeCountries().values();
    }
}
