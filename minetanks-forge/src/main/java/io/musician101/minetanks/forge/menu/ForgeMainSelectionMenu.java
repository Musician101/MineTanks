package io.musician101.minetanks.forge.menu;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.minetanks.forge.tank.ForgeCountries;
import io.musician101.minetanks.forge.tank.ForgeCountry;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ForgeMainSelectionMenu extends AbstractForgeChestMenu<ForgeMineTanks>
{
    private final ForgeBattleField field;

    public ForgeMainSelectionMenu(ForgeBattleField field, EntityPlayerMP player)
    {
        super(player, 9, CommonReference.TANK_SELECTION, ForgeMineTanks.instance());
        this.field = field;
    }

    @Override
    protected void build()
    {
        List<ForgeCountry> countries = ForgeCountries.getValues();
        ItemStack bannerStack = new ItemStack(countries.get(new Random().nextInt(countries.size() - 1)).getItem());
        BannerMeta bannerMeta = (BannerMeta) bannerStack.getItemMeta();
        bannerMeta.setDisplayName("Countries");
        bannerStack.setItemMeta(bannerMeta);
        set(0, bannerStack, (player, clickType) -> new ForgeSelectCountryMenu(field, player));

        ItemStack minecart = new ItemStack(Material.MINECART);
        ItemMeta minecartMeta = minecart.getItemMeta();
        minecartMeta.setDisplayName("Tank Types");
        minecartMeta.setLore(Arrays.asList("View tanks by their types.", "Light, Medium, etc."));
        minecart.setItemMeta(minecartMeta);
        set(1, minecart, (player, clickType) -> new ForgeSelectTankTypeMenu(field, player));

        for (int slot = 2; slot < inventory.getSize(); slot++)
            set(slot, new ItemStack(Material.THIN_GLASS));
    }
}
