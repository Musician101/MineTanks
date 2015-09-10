package musician101.minetanks.spigot.util;

import java.util.List;

import musician101.minetanks.MineTanks;
import musician101.minetanks.lib.Reference;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.util.event.Subscribe;

/**
 * @author nisovin on BukkitDev
 * @note Ported to Sponge by Musician101
 */
public class IconMenu
{
	//TODO entire class might need rewriting
    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private String[] optionNames;
    private ItemStack[] optionIcons;
   
    public IconMenu(String name, int size, OptionClickEventHandler handler)
    {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
    }
    
    //Original arguments were int position, ItemStack icon, String name, String... info
    public IconMenu setOption(int position, ItemType icon, String name, List<String> info)
    {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
   
    public void open(Player player)
    {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++)
            if (optionIcons[i] != null)
                inventory.setItem(i, optionIcons[i]);
        
        player.openInventory(inventory);
    }
   
    public void destroy()
    {
        handler = null;
        optionNames = null;
        optionIcons = null;
    }
   
    @Subscribe
    void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getInventory().getTitle().equals(name))
        {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null)
            {
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if (e.willClose())
                {
                    final Player p = (Player)event.getWhoClicked();
                    MineTanks.getGame().getScheduler().runTaskAfter(MineTanks.getGame().getPluginManager().getPlugin(Reference.ID).get(), new Runnable()
                    {
                        public void run()
                        {
                            p.closeInventory();
                        }
                    }, 1);
                }
                
                if (e.willDestroy())
                    destroy();
            }
        }
    }
    
    public interface OptionClickEventHandler
    {
        public void onOptionClick(OptionClickEvent event);       
    }
    
    public class OptionClickEvent
    {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name)
        {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
       
        public Player getPlayer()
        {
            return player;
        }
       
        public int getPosition()
        {
            return position;
        }
       
        public String getName()
        {
            return name;
        }
       
        public boolean willClose()
        {
            return close;
        }
       
        public boolean willDestroy()
        {
            return destroy;
        }
       
        public void setWillClose(boolean close)
        {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy)
        {
            this.destroy = destroy;
        }
    }
    
    //Original arguments were ItemStack item, String name, String[] lore
    private ItemStack setItemNameAndLore(ItemType type, String name, List<String> lore)
    {
    	ItemStackBuilder isb = MineTanks.getGame().getRegistry().getItemBuilder();
    	isb.withItemType(ItemTypes.MINECART);
    	
    	//TODO no item meta data support
        ItemMeta im = type.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        type.setItemMeta(im);
        return isb.build();
    }
   
}
