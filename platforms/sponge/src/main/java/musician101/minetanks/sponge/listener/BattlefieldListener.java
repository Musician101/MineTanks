package musician101.minetanks.sponge.listener;

import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.event.AttemptMenuOpenEvent;
import musician101.minetanks.sponge.event.PlayerTankDamageEvent;
import musician101.minetanks.sponge.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.sponge.event.PlayerTankDeathEvent;
import musician101.minetanks.sponge.handler.DamageHandler;
import musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

public class BattlefieldListener
{
    @Listener
    public void onAttemptMenuOpen(AttemptMenuOpenEvent event)
    {
        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(event.getField());
        Player player = MTUtils.getPlayer(event.getPlayer());
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
            player.sendMessage(Texts.of(CommonMessages.MUST_UNREADY));
            return;
        }

        SpongeMineTanks.openTankMenu(player);
    }

    @Listener
    public void onPlayerDeath(PlayerTankDeathEvent event)
    {
        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(event.getField());
        Player killed = event.getKilled();
        Player killer = event.getKiller();
        SpongeMTScoreboard sb = field.getScoreboard();
        Text prefix = Texts.builder().append(Texts.of(CommonMessages.PREFIX)).color(TextColors.GREEN).build();
        Text damagedMsg = (sb.isOnGreen(killed.getUniqueId()) ? Texts.builder().append(Texts.of(killed.getName())).color(TextColors.GREEN).build() : Texts.builder().append(Texts.of(killed.getName())).color(TextColors.RED).build());
        Text damagerMsg = (sb.isOnGreen(killer.getUniqueId()) ? Texts.builder().append(Texts.of(killer.getName())).color(TextColors.GREEN).build() : Texts.builder().append(Texts.of(killer.getName())).color(TextColors.RED).build());
        Text damagedBy = Texts.builder().append(Texts.of("was damaged by")).color(TextColors.WHITE).build();
        Sponge.getGame().getServer().getOnlinePlayers().forEach(player -> {
            if (field.getPlayerTank(player.getUniqueId()) != null)
                player.sendMessage(Texts.join(Texts.of(" "), new Text[]{prefix, damagedMsg, damagedBy, damagerMsg}));
        });

        field.playerKilled(killed.getUniqueId());
        field.endMatch();
    }

    @Listener
    public void onPlayerDamageEvent(PlayerTankDamageEvent event)
    {
        DamageHandler dh = new DamageHandler();
        SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(event.getField());
        UUID damagedPlayer = event.getDamagedPlayer();
        if (event.getCause() == PlayerTankDamageCause.FALL)
            dh.gravityHit(field, damagedPlayer, event.getDamage());

        UUID damager = event.getDamager();
        int damage = event.getDamage();
        SpongeMTScoreboard sb = field.getScoreboard();
        if (sb.getPlayerHealth(damagedPlayer) <= 0 || sb.getPlayerHealth(damager) <= 0)
            return;

        if (event.getCause() == PlayerTankDamageCause.RAM)
            dh.meleeHitEnemy(field, damagedPlayer, damager, damage);

        if (event.getCause() == PlayerTankDamageCause.SPLASH)
            dh.playerHitEnemy(field, damagedPlayer, damager, damage);

        if (event.getCause() == PlayerTankDamageCause.PENETRATION)
            dh.playerHitEnemy(field, damagedPlayer, damager, damage);
    }
}
