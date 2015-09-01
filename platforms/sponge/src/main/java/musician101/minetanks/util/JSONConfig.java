package musician101.minetanks.util;

import musician101.minetanks.MineTanks;

import org.json.simple.JSONObject;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;

@SuppressWarnings("serial")
public class JSONConfig extends JSONObject
{
	public JSONConfig()
	{
		super();
	}
	
	public String getString(String key)
	{
		return containsKey(key) ? get(key).toString() : null;
	}
	
	public int getInt(String key)
	{
		return containsKey(key) ? Integer.valueOf(get(key).toString()) : null;
	}
	
	//TODO Update when Inventories come out
	/*public Inventory getInventory(String key)
	{
		
	}*/
	
	public ItemStack getItemStack(String key)
	{
		if (!containsKey(key))
			return null;
		
		
		ItemStackBuilder isb = MineTanks.getGame().getRegistry().getItemBuilder();
		isb.withItemType(getItemType("type"));
		isb.withDamage(getInt("damage"));
		isb.withQuantity(getInt("quantity"));
		//TODO update when metadata comes out
		//isb.withMetaData(metaData);
		return isb.build();
	}
	
	public ItemType getItemType(String key)
	{
		return containsKey(key) ? MineTanks.getGame().getRegistry().getItem(get(key).toString()).get() : null;
	}
}
