package musician101.minetanks.sponge.util;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.tank.Tanks;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MTUtils
{
    public static ItemStack createCustomItem(ItemType type)
    {
        return SpongeMineTanks.getGame().getRegistry().createItemBuilder().itemType(type).build();
    }

    public static ItemStack createCustomItem(ItemType type, int quantity)
    {
        return SpongeMineTanks.getGame().getRegistry().createItemBuilder().itemType(type).quantity(quantity).build();
    }

    public static ItemStack createCustomItem(ItemType type, String displayName)
    {
        return createCustomItem(type, displayName, null);
    }

    public static ItemStack createCustomItem(ItemType type, String displayName, List<String> description)
    {
        return createCustomItem(type, 0, 1, displayName, description);
    }

    public static ItemStack createCustomItem(ItemType type, int damage, int quantity)
    {
        return createCustomItem(type, damage, quantity, "", null);
    }

    @SuppressWarnings("serial")
    public static ItemStack createCustomItem(ItemType type, int damage, int quantity, String displayName, List<String> description)
    {
        ItemStackBuilder isb = SpongeMineTanks.getGame().getRegistry().getItemBuilder();
        isb.withItemType(type);
        isb.withDamage(damage);
        isb.withQuantity(quantity);

        //TODO item metadata has not been implemented
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("\u00A7a" + displayName);
        if (!description.equals(""))
            meta.setLore(Arrays.asList(description));

        item.setItemMeta(meta);
        return isb.build();
    }

    public static int getMenuSize()
    {
        int rows = 0;
        while (rows * 9 < Tanks.values().length)
            rows++;

        return rows * 9;
    }

    public static Location<World> deserializeLocation(SpongeJSONConfig locJSON)
    {
        World world = SpongeMineTanks.getGame().getServer().getWorld(locJSON.get("world").toString()).get();
        return new Location<>(world, locJSON.getInteger("x", 0), locJSON.getInteger("y", 0), locJSON.getInteger("z", 0));
    }

    public static Player getPlayer(UUID playerId)
    {
        return SpongeMineTanks.getGame().getServer().getPlayer(playerId).get();
    }
}
