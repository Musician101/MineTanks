package musician101.minetanks.spigot.menu;

import musician101.minetanks.spigot.SpigotMineTanks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

//TODO might need to move this to Common
//TODO check ServSat menu version
public class AbstractMenu implements Listener
{
    private ClickHandler handler;
    protected Inventory inv;

    protected AbstractMenu(SpigotMineTanks plugin, Inventory inv, ClickHandler handler)
    {
        this.inv = inv;
        this.handler = handler;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void setOption(int slot, ItemStack itemStack)
    {
        setOption(slot, itemStack, " ");
    }

    public void setOption(int slot, ItemStack itemStack, String name)
    {
        setOption(slot, itemStack, name, new String[0]);
    }

    public void setOption(int slot, ItemStack itemStack, String name, String... description)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasDisplayName())
            itemMeta.setDisplayName(name);
        if (!itemMeta.hasLore())
            itemMeta.setLore(Arrays.asList(description));

        itemStack.setItemMeta(itemMeta);
        inv.setItem(slot, itemStack);
    }

    public void open(Player player)
    {
        player.openInventory(inv);
    }

    private void destroy()
    {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event)
    {
        if (!(event.getWhoClicked() instanceof Player))
            return;

        if (!inv.equals(event.getInventory()))
            return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return;

        int slot = event.getRawSlot();
        ClickEvent clickEvent = new ClickEvent(player, itemStack, slot);
        handler.handle(clickEvent);
        if (clickEvent.willClose())
            player.closeInventory();

        if (clickEvent.willDestroy())
            destroy();
    }

    public interface ClickHandler
    {
        void handle(ClickEvent event);
    }

    public class ClickEvent
    {
        private boolean close = true;
        private boolean destroy = true;
        private int position;
        private ItemStack itemStack;
        private Player player;

        public ClickEvent(Player player, ItemStack itemStack, int slot)
        {
            this.player = player;
            this.itemStack = itemStack;
            this.position = slot;
        }

        public boolean willClose()
        {
            return close;
        }

        public void setWillClose(boolean close)
        {
            this.close = close;
        }

        public boolean willDestroy()
        {
            return destroy;
        }

        public void setWillDestroy(boolean destroy)
        {
            this.destroy = destroy;
        }

        public int getSlot()
        {
            return position;
        }

        public ItemStack getItem()
        {
            return itemStack;
        }

        public Player getPlayer()
        {
            return player;
        }
    }

    protected void addBackButton()
    {
        setOption(inv.getSize() - 1, new ItemStack(Material.ARROW), "Back", "Go back to the previous menu.");
    }
}
