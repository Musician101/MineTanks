package musician101.minetanks.util;

import java.util.Arrays;
import java.util.UUID;

import musician101.minetanks.MineTanks;
import musician101.minetanks.battlefield.Battlefield;
import musician101.minetanks.battlefield.player.PlayerTank;
import musician101.minetanks.scoreboards.MTScoreboard;
import musician101.minetanks.tankinfo.modules.Cannons;
import musician101.minetanks.tankinfo.modules.Engines;
import musician101.minetanks.tankinfo.modules.Radios;
import musician101.minetanks.tankinfo.modules.Tracks;
import musician101.minetanks.tankinfo.modules.Turrets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MTUtils
{
	public static double calculateArmorValue(double front, double side, double rear)
	{
		return (front + side + rear) / 3;
	}
	
	public static Inventory parseWeapons(Cannons cannon)
	{
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		inv.setItem(0, cannon.getCannon());
		inv.addItem(new ItemStack(Material.ARROW, cannon.getAmmoCount()));
		return inv;
	}
	
	public static ItemStack[] parseArmor(Engines engine, Turrets turret, Tracks tracks, Radios radio, double armor, int speed)
	{
		return new ItemStack[]{parseArmorValue(parseSpeedValue(engine.getEngine(), speed), armor),
				parseArmorValue(tracks.getTracks(), armor),
				parseArmorValue(radio.getRadio(), armor),
				parseArmorValue(turret.getHelmet(), armor)};
	}
	
	private static ItemStack parseArmorValue(ItemStack item, double armor)
	{
		int a = 0;
		if (armor >= 1 && armor < 2)
			a = 1;
		else if (armor >= 2 && armor < 3)
			a = 2;
		else if (armor >= 3 && armor < 4)
			a = 3;
		else if (armor >= 4 && armor < 5)
			a = 4;
		else if (armor >= 5 && armor < 6)
			a = 5;
		else if (armor >= 6 && armor < 7)
			a = 6;
		else if (armor >= 7 && armor < 8)
			a = 7;
		else if (armor >= 8 && armor < 9)
			a = 8;
		else if (armor >= 9 && armor < 10)
			a = 9;
		else if (armor >= 10)
			a = 10;
		else
			return item;
		
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, a, true);
		item.setItemMeta(meta);
		return item;
	}
	
	private static ItemStack parseSpeedValue(ItemStack item, int speed)
	{
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(meta.getLore().get(0), "Speed value: " + speed));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createCustomItem(Material material, String displayName)
	{
		return createCustomItem(material, displayName, "");
	}
	
	public static ItemStack createCustomItem(Material material, String displayName, String description)
	{
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§a" + displayName);
		if (!description.equals(""))
			meta.setLore(Arrays.asList(description));
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static void ammoExplosion(MineTanks plugin, Location location, int level, boolean hitPlayer)
	{
		float power = 0F;
		if (hitPlayer)
			power++;
		
		if (level >= 6)
			power++;
		
		location.getWorld().createExplosion(location, power);
	}
	
	public static void meleeHit(MineTanks plugin, Battlefield field, UUID player1, PlayerTank pt1, UUID player2, PlayerTank pt2)
	{
		double totalWeight = pt1.getTank().getWeight() + pt2.getTank().getWeight();
		double damage1 = 0.5 * totalWeight * ((pt1.getTank().getSpeed().getAmplifier() + pt2.getTank().getSpeed().getAmplifier())^2);
		double damage2 = (1 - (pt1.getTank().getWeight() / totalWeight)) * damage1;
		
		if (damage1 > 0)
			playerHit(plugin, field, player2, pt2, player1, pt1, (int) damage1);
		
		if (damage2 > 0)
			playerHit(plugin, field, player1, pt1, player2, pt2, (int) damage2);
	}
	
	public static void playerHit(MineTanks plugin, Battlefield field, UUID dmgd, PlayerTank ptdd, UUID dmgr, PlayerTank ptdr, int damage)
	{
		plugin.statStorage.getPlayer(dmgr).addMoneyFromHit(damage);
		plugin.statStorage.getPlayer(dmgr).addXpFromHit(ptdd, ptdr, damage);
		MTScoreboard sb = field.getScoreboard();
		sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - (int) (damage * 2));
		if (sb.getPlayerHealth(dmgd) <= 0)
		{
			Bukkit.getPlayer(dmgd).setHealth(0);
			for (Player player : Bukkit.getOnlinePlayers())
			{
				if (field.getPlayer(player.getUniqueId()) != null)
				{
					String dmgdMsg = (sb.isOnGreen(Bukkit.getPlayer(dmgd)) ? ChatColor.GREEN + Bukkit.getPlayer(dmgd).getName() : ChatColor.RED + Bukkit.getPlayer(dmgd).getName());
					String dmgrMsg = (sb.isOnGreen(Bukkit.getPlayer(dmgr)) ? ChatColor.GREEN + Bukkit.getPlayer(dmgr).getName() : ChatColor.RED + Bukkit.getPlayer(dmgr).getName());
					player.sendMessage(ChatColor.GREEN + plugin.prefix + ChatColor.RESET + " " + dmgdMsg + ChatColor.RESET + " was killed by " + dmgrMsg + ChatColor.RESET + ".");
				}
			}
		}
	}
	
	public static void gravityHit(MineTanks plugin, Battlefield field, UUID player, PlayerTank pt, int damage)
	{
		double dmg = 0.5 * pt.getTank().getWeight() * (pt.getTank().getSpeed().getAmplifier()^2);
		MTScoreboard sb = field.getScoreboard();
		sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
	}
}
