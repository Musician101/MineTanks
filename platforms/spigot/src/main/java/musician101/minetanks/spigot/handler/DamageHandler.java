package musician101.minetanks.spigot.handler;

import musician101.minetanks.spigot.battlefield.SpigotBattleField;
import musician101.minetanks.spigot.event.PlayerTankDeathEvent;
import musician101.minetanks.spigot.scoreboard.SpigotMTScoreboard;
import musician101.minetanks.spigot.tank.SpigotTankType;
import musician101.minetanks.spigot.tank.SpigotTankTypes;
import org.bukkit.Bukkit;

import java.util.UUID;

public class DamageHandler
{
    public DamageHandler()
    {

    }

    private int getModifier(SpigotTankType type)
    {
        if (type == SpigotTankTypes.LIGHT)
            return 1;
        else if (type == SpigotTankTypes.MEDIUM || type == SpigotTankTypes.TD)
            return 2;
        else if (type == SpigotTankTypes.ARTY)
            return 3;
        else if (type == SpigotTankTypes.HEAVY)
            return 5;

        return 0;
    }

    public void meleeHitEnemy(SpigotBattleField field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * getModifier(field.getPlayerTank(rammed).getTank().getType());
        double rammedDmg = damage * getModifier(field.getPlayerTank(rammer).getTank().getType());
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitEnemy(SpigotBattleField field, UUID damaged, UUID damager, int damage)
    {
        SpigotMTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(damaged, sb.getPlayerHealth(damaged) - (damage * 2) * 20);
        if (sb.getPlayerHealth(damaged) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(damaged), Bukkit.getPlayer(damager)));
    }

    public void gravityHit(SpigotBattleField field, UUID player, int damage)
    {
        double dmg = damage * 5;
        SpigotMTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(player, sb.getPlayerHealth(player) - (int) dmg);
        if (sb.getPlayerHealth(player) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(player), null));
    }

    public void meleeHitFriendly(SpigotBattleField field, UUID rammed, UUID rammer, int damage)
    {
        double rammerDmg = damage * 20;
        double rammedDmg = damage * 15;
        if (rammerDmg > 0)
            playerHitEnemy(field, rammed, rammer, (int) rammerDmg);

        if (rammedDmg > 0)
            playerHitEnemy(field, rammer, rammed, (int) rammedDmg);
    }

    public void playerHitFriendly(SpigotBattleField field, UUID damaged, UUID damager, int damage)
    {
        SpigotMTScoreboard sb = field.getScoreboard();
        sb.setPlayerHealth(damaged, sb.getPlayerHealth(damaged) - (damage * 2) * 20);
        if (sb.getPlayerHealth(damaged) <= 0)
            Bukkit.getPluginManager().callEvent(new PlayerTankDeathEvent(field.getName(), Bukkit.getPlayer(damaged), Bukkit.getPlayer(damager)));
    }
}
