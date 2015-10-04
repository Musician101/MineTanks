package musician101.minetanks.sponge.handler;

import musician101.minetanks.sponge.MineTanks;
import musician101.minetanks.sponge.event.PlayerTankDeathEvent;
import musician101.minetanks.sponge.scoreboard.MTScoreboard;
import musician101.minetanks.sponge.battlefield.Battlefield;
import musician101.minetanks.sponge.tank.Tanks.TankTypes;

import java.util.UUID;

public class DamageHandler
{
    private int getModifier(TankTypes type)
    {
        if (type == TankTypes.LIGHT)
            return 1;
        else if (type == TankTypes.MEDIUM || type == TankTypes.TD)
            return 2;
        else if (type == TankTypes.ARTY)
            return 3;
        else if (type == TankTypes.HEAVY)
            return 5;

        return 0;
    }

    public void meleeHitEnemy(Battlefield field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * getModifier(field.getPlayer(rammed).getTank().getType());
        double rammedDmg = damage * getModifier(field.getPlayer(rammer).getTank().getType());
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitEnemy(Battlefield field, UUID dmgd, UUID dmgr, int damage)
    {
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
        if (sb.getPlayerHealth(dmgd) <= 0)
            MineTanks.getGame().getEventManager().post(new PlayerTankDeathEvent(field.getName(), MineTanks.getGame().getServer().get().getPlayer(dmgd).get(), MineTanks.getGame().getServer().get().getPlayer(dmgr).get()));
    }

    public void gravityHit(Battlefield field, UUID player, int damage)
    {
        double dmg = damage * 5;
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
        if (sb.getPlayerHealth(player) <= 0)
            MineTanks.getGame().getEventManager().post(new PlayerTankDeathEvent(field.getName(), MineTanks.getGame().getServer().get().getPlayer(player).get(), null));
    }

    public void meleeHitFriendly(Battlefield field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * 20;
        double rammedDmg = damage * 15;
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitFriendly(Battlefield field, UUID dmgd, UUID dmgr, int damage)
    {
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
        if (sb.getPlayerHealth(dmgd) <= 0)
            MineTanks.getGame().getEventManager().post(new PlayerTankDeathEvent(field.getName(), MineTanks.getGame().getServer().get().getPlayer(dmgd).get(), MineTanks.getGame().getServer().get().getPlayer(dmgr).get()));
    }
}
