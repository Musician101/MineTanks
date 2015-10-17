package musician101.minetanks.sponge.util;

import musician101.minetanks.sponge.SpongeMineTanks;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorRegistry;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.inventory.ChangeInventoryEvent.Click;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventories;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.item.inventory.custom.CustomInventoryBuilder;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.service.scheduler.TaskBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Literal;
import org.spongepowered.api.text.Texts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class IconMenu
{
    private String name;
    private OptionClickEventHandler handler;
    private Map<String, ItemStack> options = new HashMap<>();

    public IconMenu(String name, OptionClickEventHandler handler)
    {
        this.name = name;
        this.handler = handler;
    }

    public IconMenu setOption(ItemType icon, Text name, String... info)
    {
        options.put(((Literal) name).getContent(), setItemNameAndLore(icon, name, info));
        return this;
    }

    public void open(Player player)
    {
        CustomInventoryBuilder cib = Inventories.customInventoryBuilder();
        OrderedInventory inventory = cib.build();
        for (String name : options.keySet())
            inventory.set(options.get(name));

        player.openInventory(inventory);
    }

    public void destroy()
    {
        handler = null;
        options = new HashMap<>();
    }

    @Listener
    public void onInventoryClick(Click event)
    {
        if (!event.getTargetInventory().getName().getTranslation().get(Locale.ENGLISH).equals(name))
            return;

        event.setCancelled(true);
        if (!event.getOriginalItemStack().isPresent())
            return;

        String name = ((Literal) event.getOriginalItemStack().get().get(Keys.DISPLAY_NAME).get()).getContent();
        if (!options.containsKey(name))
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        OptionClickEvent e = new OptionClickEvent(player, name);
        handler.onOptionClick(e);
        if (e.willClose())
        {
            final Player p = player;
            TaskBuilder tb = SpongeMineTanks.getGame().getScheduler().createTaskBuilder();
            tb.execute(task -> p.closeInventory());
            tb.name("SpongeMineTanks-IconMenu(" + name + ")-" + player.getName());
            tb.delayTicks(1L);
            tb.submit(SpongeMineTanks.getPluginContainer());
        }

        if (e.willDestroy())
            destroy();
    }

    public interface OptionClickEventHandler
    {
        void onOptionClick(OptionClickEvent event);
    }

    public class OptionClickEvent
    {
        private Player player;
        private String name;
        private boolean close;
        private boolean destroy;

        public OptionClickEvent(Player player, String name)
        {
            this.player = player;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }

        public Player getPlayer()
        {
            return player;
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

    private ItemStack setItemNameAndLore(ItemType type, Text name, String... lore)
    {
        List<Text> loreAsText = new ArrayList<>();
        for (String line : lore)
            loreAsText.add(Texts.of(line));

        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        DataManipulatorRegistry dmr = gr.getManipulatorRegistry();
        LoreData loreData = dmr.getBuilder(CatalogItemData.LORE_DATA).get().create();
        loreData.set(gr.createValueBuilder().createListValue(Keys.ITEM_LORE, loreAsText));

        DisplayNameData nameData = dmr.getBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        nameData.set(Keys.DISPLAY_NAME, name);

        ItemStackBuilder isb = SpongeMineTanks.getGame().getRegistry().createItemBuilder();
        return isb.itemType(type).itemData(loreData).itemData(nameData).build();
    }
}
