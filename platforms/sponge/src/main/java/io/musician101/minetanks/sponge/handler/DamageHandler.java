package io.musician101.minetanks.sponge.handler;

import io.musician101.minetanks.sponge.tank.SpongeTankType;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.event.PlayerTankDeathEvent;
import io.musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import io.musician101.minetanks.sponge.tank.SpongeTankTypes;
import org.spongepowered.api.Sponge;

import java.util.UUID;

public class DamageHandler
{
    private int getModifier(SpongeTankType type)
    {
        if (type == SpongeTankTypes.LIGHT)
            return 1;
        else if (type == SpongeTankTypes.MEDIUM || type == SpongeTankTypes.TD)
            return 2;
        else if (type == SpongeTankTypes.ARTY)
            return 3;
        else if (type == SpongeTankTypes.HEAVY)
            return 5;

        return 0;
    }

    public void meleeHitEnemy(SpongeBattleField field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * getModifier(field.getPlayerTank(rammed).getTank().getType());
        double rammedDmg = damage * getModifier(field.getPlayerTank(rammer).getTank().getType());
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitEnemy(SpongeBattleField field, UUID damaged, UUID damager, int damage)
    {
        SpongeMTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(damaged, sb.getPlayerHealth(damaged) - (damage * 2 * 20));
        if (sb.getPlayerHealth(damaged) <= 0)
            Sponge.getGame().getEventManager().post(new PlayerTankDeathEvent(field.getName(), MTUtils.getPlayer(damaged), MTUtils.getPlayer(damager)));
    }

    public void gravityHit(SpongeBattleField field, UUID player, int damage)
    {
        double dmg = damage * 5;
        SpongeMTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
        if (sb.getPlayerHealth(player) <= 0)
            Sponge.getGame().getEventManager().post(new PlayerTankDeathEvent(field.getName(), MTUtils.getPlayer(player), null));
    }
}
