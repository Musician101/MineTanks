package io.musician101.minetanks.forge.tank;

import io.musician101.minetanks.common.CommonReference.CommonCountries;
import io.musician101.minetanks.common.tank.AbstractInfoStorage;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;

public class ForgeCountries extends AbstractInfoStorage<ForgeCountry>
{
    //TODO create custom ItemStackBuilder
    private ForgeCountries()
    {
        super(new ForgeCountry(CommonCountries.CHINA, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.CHINA);
                        meta.setBaseColor(DyeColor.RED);
                        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.SQUARE_TOP_RIGHT));
                        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.BORDER));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.CZECH, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.CZECH);
                        meta.setBaseColor(DyeColor.WHITE);
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL));
                        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.TRIANGLE_TOP));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.FRANCE, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.FRANCE);
                        meta.setBaseColor(DyeColor.WHITE);
                        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_TOP));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.GERMANY, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.GERMANY);
                        meta.setBaseColor(DyeColor.RED);
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
                        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRAIGHT_CROSS));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.JAPAN, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.JAPAN);
                        meta.setBaseColor(DyeColor.WHITE);
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.UK, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setBaseColor(DyeColor.BLUE);
                        meta.setDisplayName(CommonCountries.UK);
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.CROSS));
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.USA, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.USA);
                        meta.setBaseColor(DyeColor.RED);
                        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
                        meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_RIGHT));
                        setItemMeta(meta);
                    }
                }),
                new ForgeCountry(CommonCountries.USSR, new ItemStack(Material.BANNER)
                {
                    {
                        BannerMeta meta = (BannerMeta) getItemMeta();
                        meta.setDisplayName(CommonCountries.USSR);
                        meta.setBaseColor(DyeColor.RED);
                        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.CROSS));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.DIAGONAL_LEFT_MIRROR));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.FLOWER));
                        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_DOWNLEFT));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.SQUARE_TOP_LEFT));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.SQUARE_TOP_RIGHT));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.DIAGONAL_LEFT));
                        meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.MOJANG));
                        meta.addPattern(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
                        setItemMeta(meta);
                    }
                }));
    }

    public static ForgeCountry getCountry(String name)
    {
        return new ForgeCountries().get(name);
    }

    public static List<ForgeCountry> getValues()
    {
        return new ForgeCountries().values();
    }
}
