package io.musician101.minetanks.sponge.listener;

import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import io.musician101.minetanks.sponge.event.PlayerTankDamageEvent;
import io.musician101.minetanks.sponge.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import io.musician101.minetanks.sponge.handler.DamageHandler;
import io.musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.event.AttemptMenuOpenEvent;
import io.musician101.minetanks.sponge.event.PlayerTankDeathEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

public class BattlefieldListener
{
    SpongeMineTanks plugin;

    @Listener
    public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
    {
        SpongeBattleField field = plugin.getFieldStorage().getField(event.getField());
        Player player = MTUtils.getPlayer(event.getPlayerId());
        SpongePlayerTank pt = event.getPlayerTank();
        if (event.getItemType() == ItemTypes.CLOCK)
        {
            if (pt.isReady())
            {
                pt.setReady(false);
                player.getInventory().set(MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
                return;
            }

            pt.setReady(true);
            player.getInventory().set(MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.UNREADY, CommonItemText.READY));
            field.startMatch();
            return;
        }

        if (pt.isReady())
        {
            player.sendMessage(Text.of(CommonMessages.MUST_UNREADY));
            return;
        }

        plugin.openTankMenu(player);
    }

    @Listener
    public void onPlayerDeath(PlayerTankDeathEvent event)
    {
        SpongeBattleField field = plugin.getFieldStorage().getField(event.getField());
        Player killed = event.getKilled();
        Player killer = event.getKiller();
        SpongeMTScoreboard sb = field.getScoreboard();
        Text prefix = Text.builder().append(Text.of(CommonMessages.PREFIX)).color(TextColors.GREEN).build();
        Text damagedMsg = (sb.isOnGreen(killed.getUniqueId()) ? Text.builder().append(Text.of(killed.getName())).color(TextColors.GREEN).build() : Text.builder().append(Text.of(killed.getName())).color(TextColors.RED).build());
        Text damagerMsg = (sb.isOnGreen(killer.getUniqueId()) ? Text.builder().append(Text.of(killer.getName())).color(TextColors.GREEN).build() : Text.builder().append(Text.of(killer.getName())).color(TextColors.RED).build());
        Text damagedBy = Text.builder().append(Text.of("was damaged by")).color(TextColors.WHITE).build();
        Sponge.getGame().getServer().getOnlinePlayers().forEach(player -> {
            if (field.getPlayerTank(player.getUniqueId()) != null)
                player.sendMessage(Text.join(Text.of(" "), prefix, damagedMsg, damagedBy, damagerMsg));
        });

        field.playerKilled(killed.getUniqueId());
        field.endMatch();
    }

    @Listener
    public void onPlayerDamageEvent(PlayerTankDamageEvent event)
    {
        DamageHandler dh = new DamageHandler();
        SpongeBattleField field = plugin.getFieldStorage().getField(event.getField());
        UUID damagedPlayer = event.getDamagedPlayer();
        if (event.getDamageType() == PlayerTankDamageCause.FALL)
            dh.gravityHit(field, damagedPlayer, event.getDamage());

        UUID damager = event.getDamager();
        int damage = event.getDamage();
        SpongeMTScoreboard sb = field.getScoreboard();
        if (sb.getPlayerHealth(damagedPlayer) <= 0 || sb.getPlayerHealth(damager) <= 0)
            return;

        if (event.getDamageType() == PlayerTankDamageCause.RAM)
            dh.meleeHitEnemy(field, damagedPlayer, damager, damage);

        if (event.getDamageType() == PlayerTankDamageCause.SPLASH)
            dh.playerHitEnemy(field, damagedPlayer, damager, damage);

        if (event.getDamageType() == PlayerTankDamageCause.PENETRATION)
            dh.playerHitEnemy(field, damagedPlayer, damager, damage);
    }
}
