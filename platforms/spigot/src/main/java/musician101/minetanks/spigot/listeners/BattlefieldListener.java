package musician101.minetanks.spigot.listeners;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import musician101.minetanks.spigot.events.AttemptMenuOpenEvent;
import musician101.minetanks.spigot.events.PlayerTankDamageEvent;
import musician101.minetanks.spigot.events.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.spigot.events.PlayerTankDeathEvent;
import musician101.minetanks.spigot.handlers.DamageHandler;
import musician101.minetanks.spigot.scoreboards.MTScoreboard;
import musician101.minetanks.spigot.util.MTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class BattlefieldListener implements Listener
{
    private final MineTanks plugin;

    public BattlefieldListener(MineTanks plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
    {
        BattleField field = plugin.getFieldStorage().getField(event.getField());
        Player player = Bukkit.getPlayer(event.getPlayer());
        PlayerTank pt = event.getPlayerTank();
        if (event.getMaterial() == Material.WATCH)
        {
            if (pt.isReady())
            {
                pt.setReady(false);
                player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Ready Up", "You are currently not ready."));
                return;
            }

            pt.setReady(true);
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, "Unready", "You are currently ready."));
            field.startMatch();
            return;
        }

        if (pt.isReady())
        {
            player.sendMessage(ChatColor.RED + plugin.getPrefix() + " You must unready to change your tank.");
            return;
        }

        plugin.getMenuHandler().openTankMenu(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerTankDeathEvent event)
    {
        BattleField field = plugin.getFieldStorage().getField(event.getField());
        Player killed = event.getKilled();
        Player killer = event.getKiller();
        MTScoreboard sb = field.getScoreboard();
        String damagedMsg = (sb.isOnGreen(killed) ? ChatColor.GREEN + killed.getName() : ChatColor.RED + killed.getName());
        String damagerMsg = (sb.isOnGreen(killer) ? ChatColor.GREEN + killer.getName() : ChatColor.RED + killer.getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (field.getPlayer(player.getUniqueId()) != null)
                player.sendMessage(ChatColor.GREEN + plugin.getPrefix() + ChatColor.RESET + " " + damagedMsg + ChatColor.RESET + " was killed by " + damagerMsg + ChatColor.RESET + ".");
        });

        killed.getInventory().clear();
        killed.getInventory().setHelmet(null);
        killed.getInventory().setChestplate(null);
        killed.getInventory().setLeggings(null);
        killed.getInventory().setBoots(null);
        field.playerKilled(killed.getUniqueId());
        field.endMatch();
    }

    @EventHandler
    public void onPlayerDamageEvent(PlayerTankDamageEvent event)
    {
        DamageHandler dh = new DamageHandler(plugin);
        BattleField field = plugin.getFieldStorage().getField(event.getField());
        UUID damaged = event.getDamagedPlayer();
        if (event.getCause() == PlayerTankDamageCause.FALL)
            dh.gravityHit(field, damaged, event.getDamage());

        UUID damager = event.getDamager();
        int damage = event.getDamage();
        MTScoreboard sb = field.getScoreboard();
        if (sb.getPlayerHealth(damaged) <= 0 || sb.getPlayerHealth(damager) <= 0)
            return;

        if ((sb.isOnGreen(Bukkit.getPlayer(damager)) && sb.isOnGreen(Bukkit.getPlayer(damaged))) || (sb.isOnRed(Bukkit.getPlayer(damager)) && sb.isOnRed(Bukkit.getPlayer(damaged))))
        {
            if (event.getCause() == PlayerTankDamageCause.RAM)
                dh.meleeHitFriendly(field, damager, damaged, damage);

            if (event.getCause() == PlayerTankDamageCause.SPLASH)
                dh.playerHitFriendly(field, damaged, damager, damage);

            if (event.getCause() == PlayerTankDamageCause.PENETRATION)
                dh.playerHitFriendly(field, damaged, damager, damage);

            return;
        }

        if (event.getCause() == PlayerTankDamageCause.RAM)
            dh.meleeHitEnemy(field, damaged, damager, damage);

        if (event.getCause() == PlayerTankDamageCause.SPLASH)
            dh.playerHitEnemy(field, damaged, damager, damage);

        if (event.getCause() == PlayerTankDamageCause.PENETRATION)
            dh.playerHitEnemy(field, damaged, damager, damage);
    }
}
