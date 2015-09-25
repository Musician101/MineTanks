package musician101.minetanks.spigot.handlers;

import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.battlefield.BattleField;
import musician101.minetanks.spigot.events.PlayerTankDeathEvent;
import musician101.minetanks.spigot.scoreboards.MTScoreboard;
import musician101.minetanks.spigot.tank.TankTypes;
import org.bukkit.Bukkit;

import java.util.UUID;

public class DamageHandler
{
    MineTanks plugin;

    public DamageHandler(MineTanks plugin)
    {
        this.plugin = plugin;
    }

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

    public void meleeHitEnemy(BattleField field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * getModifier(field.getPlayer(rammed).getTank().getType());
        double rammedDmg = damage * getModifier(field.getPlayer(rammer).getTank().getType());
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitEnemy(BattleField field, UUID dmgd, UUID dmgr, int damage)
    {
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
        if (sb.getPlayerHealth(dmgd) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(dmgd), Bukkit.getPlayer(dmgr)));
    }

    public void gravityHit(BattleField field, UUID player, int damage)
    {
        double dmg = damage * 5;
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
        if (sb.getPlayerHealth(player) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(player), null));
    }

    public void meleeHitFriendly(BattleField field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * 20;
        double rammedDmg = damage * 15;
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitFriendly(BattleField field, UUID dmgd, UUID dmgr, int damage)
    {
        MTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(dmgd, sb.getPlayerHealth(dmgd) - ((int) (damage * 2) * 20));
        if (sb.getPlayerHealth(dmgd) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(dmgd), Bukkit.getPlayer(dmgr)));
    }
}