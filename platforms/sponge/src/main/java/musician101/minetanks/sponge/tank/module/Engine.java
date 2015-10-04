package musician101.minetanks.sponge.tank.module;

import musician101.minetanks.sponge.tank.Tanks.TankTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;

public class Engine
{
    String name;
    ItemStack engine;

    public Engine(String name, TankTypes type)
    {
        this.name = name;
        parseEngine(type);
    }

    public String getName()
    {
        return name;
    }

    public ItemStack getEngine()
    {
        return engine;
    }

    @SuppressWarnings("serial")
    private void parseEngine(TankTypes type)
    {
        boolean parseError = false;
        final ItemType iType;
        if (type == TankTypes.LIGHT)
            iType = ItemTypes.LEATHER_LEGGINGS;
        else if (type == TankTypes.MEDIUM)
            iType = ItemTypes.IRON_LEGGINGS;
        else if (type == TankTypes.HEAVY)
            iType = ItemTypes.DIAMOND_LEGGINGS;
        else if (type == TankTypes.TD)
            iType = ItemTypes.CHAINMAIL_LEGGINGS;
        else if (type == TankTypes.ARTY)
            iType = ItemTypes.GOLDEN_LEGGINGS;
        else
        {
            iType = ItemTypes.LEATHER_BOOTS;
            parseError = true;
        }

        engine = new ItemStack()
        {
            @Override
            public int compareTo(ItemStack o)
            {
                return 0;
            }

            @Override
            public ItemType getItem()
            {
                return iType;
            }

            @Override
            public short getDamage()
            {
                return 0;
            }

            @Override
            public void setDamage(short damage)
            {
                //NOOP
            }

            @Override
            public int getQuantity()
            {
                return 1;
            }

            @Override
            public void setQuantity(int quantity) throws IllegalArgumentException
            {
                //NOOP
            }

            @Override
            public int getMaxStackQuantity()
            {
                return 0;
            }

            @Override
            public void setMaxStackQuantity(int quantity)
            {
                //NOOP
            }
        };
        ItemMeta meta = engine.getItemMeta();
        meta.setDisplayName(TextColors.GREEN + this.name);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Arrays.asList("Your Engine"));
        engine.setItemMeta(meta);
    }
}
