package musician101.minetanks.spigot.tank;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.Arrays;
import java.util.List;

public class SpigotCountries
{
    public static final SpigotCountry CHINA = new SpigotCountry("China", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("China");
            meta.setBaseColor(DyeColor.RED);
            meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.SQUARE_TOP_RIGHT));
            meta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.BORDER));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry CZECH = new SpigotCountry("Czechoslovakia", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("Czechoslovakia");
            meta.setBaseColor(DyeColor.WHITE);
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.DIAGONAL_LEFT_MIRROR));
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.DIAGONAL_RIGHT_MIRROR));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL));
            meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.TRIANGLE_BOTTOM));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry FRANCE = new SpigotCountry("China", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("China");
            meta.setBaseColor(DyeColor.WHITE);
            meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_TOP));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry GERMANY = new SpigotCountry("Germany", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("Germany");
            meta.setBaseColor(DyeColor.WHITE);
            meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_TOP));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry JAPAN = new SpigotCountry("Japan", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("Japan");
            meta.setBaseColor(DyeColor.WHITE);
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry UK = new SpigotCountry("U.K.", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setBaseColor(DyeColor.BLUE);
            meta.setDisplayName("U.K.");
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.CROSS));
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
            meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry USA = new SpigotCountry("U.S.A.", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("U.S.A.");
            meta.setBaseColor(DyeColor.RED);
            meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
            meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_RIGHT));
            setItemMeta(meta);
        }
    });
    public static final SpigotCountry USSR = new SpigotCountry("U.S.S.R.", new ItemStack(Material.BANNER)
    {
        {
            BannerMeta meta = (BannerMeta) getItemMeta();
            meta.setDisplayName("U.S.S.R.");
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
    });
    public static final List<SpigotCountry> ALL = Arrays.asList(CHINA, CZECH, FRANCE, GERMANY, JAPAN, UK, USA, USSR);

    public static SpigotCountry getByName(String name)
    {
        for (SpigotCountry country : ALL)
            if (name.equalsIgnoreCase(country.getName()))
                return country;

        return null;
    }
}
