package musician101.minetanks.sponge.util;

import musician101.minetanks.common.CommonReference;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.custom.CustomInventory;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Literal;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.translation.FixedTranslation;

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

    public IconMenu setOption(ItemType icon, Literal name, String... info)
    {
        options.put(name.getContent(), setItemNameAndLore(icon, name, info));
        return this;
    }

    public void open(Player player)
    {
        CustomInventory.Builder cib = CustomInventory.builder();
        cib.name(new FixedTranslation(name));
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
    public void onInventoryClick(ClickInventoryEvent event)
    {
        if (!event.getTargetInventory().getName().get(Locale.ENGLISH).equals(name))
            return;

        event.setCancelled(true);
        if (event.getTransactions().size() == 0)
            return;

        String name = ((Literal) event.getCursorTransaction().getOriginal().get(Keys.DISPLAY_NAME).get()).getContent();
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
            Task.Builder tb = Task.builder();
            tb.execute(task -> p.closeInventory());
            tb.name("SpongeMineTanks-IconMenu(" + name + ")-" + player.getName());
            tb.delayTicks(1L);
            tb.submit(Sponge.getGame().getPluginManager().getPlugin(CommonReference.ID));
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

        Game game = Sponge.getGame();
        DataManager dm = game.getDataManager();
        GameRegistry gr = game.getRegistry();
        LoreData loreData = dm.getManipulatorBuilder(CatalogItemData.LORE_DATA).get().create();
        loreData.set(gr.getValueFactory().createListValue(Keys.ITEM_LORE, loreAsText));

        DisplayNameData nameData = dm.getManipulatorBuilder(CatalogItemData.DISPLAY_NAME_DATA).get().create();
        nameData.set(Keys.DISPLAY_NAME, name);

        ItemStack.Builder isb = ItemStack.builder();
        return isb.itemType(type).itemData(loreData).itemData(nameData).build();
    }
}
