package musician101.minetanks.spigot.listener;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.spigot.SpigotMineTanks;
import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.battlefield.player.SpigotPlayerTank;
import musician101.minetanks.spigot.event.AttemptMenuOpenEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent;
import musician101.minetanks.spigot.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.spigot.event.PlayerTankDeathEvent;
import musician101.minetanks.spigot.handler.DamageHandler;
import musician101.minetanks.spigot.scoreboard.MTScoreboard;
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
    private final SpigotMineTanks plugin;

    public BattlefieldListener(SpigotMineTanks plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
    {
        SpigotBattleField field = plugin.getFieldStorage().getField(event.getField());
        Player player = Bukkit.getPlayer(event.getPlayer());
        SpigotPlayerTank pt = event.getPlayerTank();
        if (event.getMaterial() == Material.WATCH)
        {
            if (pt.isReady())
            {
                pt.setReady(false);
                player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.READY_UP, CommonItemText.NOT_READY));
                return;
            }

            pt.setReady(true);
            player.getInventory().setItem(1, MTUtils.createCustomItem(Material.WATCH, CommonItemText.UNREADY, CommonItemText.READY));
            field.startMatch();
            return;
        }

        if (pt.isReady())
        {
            player.sendMessage(ChatColor.RED + CommonMessages.MUST_UNREADY);
            return;
        }

        plugin.getMenuHandler().openTankMenu(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerTankDeathEvent event)
    {
        SpigotBattleField field = plugin.getFieldStorage().getField(event.getField());
        Player killed = event.getKilled();
        Player killer = event.getKiller();
        MTScoreboard sb = field.getScoreboard();
        String damagedMsg = (sb.isOnGreen(killed.getUniqueId()) ? ChatColor.GREEN + killed.getName() : ChatColor.RED + killed.getName());
        String damagerMsg = (sb.isOnGreen(killer.getUniqueId()) ? ChatColor.GREEN + killer.getName() : ChatColor.RED + killer.getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (field.getPlayerTank(player.getUniqueId()) != null)
                player.sendMessage(ChatColor.GREEN + CommonMessages.PREFIX + " " + damagedMsg + ChatColor.RESET + " was killed by " + damagerMsg + ChatColor.RESET + ".");
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
        DamageHandler dh = new DamageHandler();
        SpigotBattleField field = plugin.getFieldStorage().getField(event.getField());
        UUID damaged = event.getDamagedPlayer();
        if (event.getCause() == PlayerTankDamageCause.FALL)
            dh.gravityHit(field, damaged, event.getDamage());

        UUID damager = event.getDamager();
        int damage = event.getDamage();
        MTScoreboard sb = field.getScoreboard();
        if (sb.getPlayerHealth(damaged) <= 0 || sb.getPlayerHealth(damager) <= 0)
            return;

        if ((sb.isOnGreen(damager) && sb.isOnGreen(damaged)) || (sb.isOnRed(damager) && sb.isOnRed(damaged)))
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
