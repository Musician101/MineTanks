package musician101.minetanks.spigot.scoreboard;

import musician101.minetanks.common.CommonReference.CommonScoreboard;
import musician101.minetanks.common.util.AbstractScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class SpigotMTScoreboard extends AbstractScoreboard<Scoreboard>
{
    private final Team green;
    private final Team red;
    private final Objective health;
    private final Objective teamCount;
    private final Score greenScore;
    private final Score redScore;

    public SpigotMTScoreboard()
    {
        super();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        green = scoreboard.registerNewTeam(CommonScoreboard.GREEN_ID);
        green.setDisplayName(CommonScoreboard.GREEN_ID);
        green.setPrefix(ChatColor.GREEN + "");
        red = scoreboard.registerNewTeam(CommonScoreboard.RED_ID);
        red.setDisplayName(CommonScoreboard.RED_NAME);
        red.setPrefix(ChatColor.RED + "");
        teamCount = scoreboard.registerNewObjective(CommonScoreboard.TEAM_COUNT_ID, CommonScoreboard.DUMMY);
        teamCount.setDisplaySlot(DisplaySlot.SIDEBAR);
        teamCount.setDisplayName(CommonScoreboard.TEAM_COUNT_NAME);
        greenScore = teamCount.getScore(ChatColor.GREEN + CommonScoreboard.GREEN_NAME);
        redScore = teamCount.getScore(ChatColor.RED + CommonScoreboard.RED_NAME);
        health = scoreboard.registerNewObjective(CommonScoreboard.HEALTH_ID, CommonScoreboard.DUMMY);
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        health.setDisplayName(CommonScoreboard.HEALTH_NAME);
    }

    @Override
    public void addGreenPlayer(UUID playerId)
    {
        green.addEntry(Bukkit.getPlayer(playerId).getName());
        greenScore.setScore(green.getSize());
    }

    @Override
    public void addRedPlayer(UUID playerId)
    {
        red.addEntry(Bukkit.getPlayer(playerId).getName());
        redScore.setScore(red.getSize());
    }

    @Override
    public void playerDeath(UUID playerId)
    {
        Player player = Bukkit.getPlayer(playerId);
        if (green.getEntries().contains(player.getName()))
            greenPlayerDeath(player);
        else if (red.getEntries().contains(player.getName()))
            redPlayerDeath(player);
    }

    private void greenPlayerDeath(Player player)
    {
        green.removeEntry(player.getName());
        greenScore.setScore(green.getSize());
        player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

    private void redPlayerDeath(Player player)
    {
        red.removeEntry(player.getName());
        redScore.setScore(red.getSize());
        player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public boolean isOnGreen(UUID playerId)
    {
        return green.getEntries().contains(Bukkit.getPlayer(playerId).getName());
    }

    @Override
    public boolean isOnRed(UUID playerId)
    {
        return red.getEntries().contains(Bukkit.getPlayer(playerId).getName());
    }

    @Override
    public int getGreenTeamSize()
    {
        return green.getSize();
    }

    @Override
    public int getRedTeamSize()
    {
        return red.getSize();
    }

    @Override
    public void setPlayerHealth(UUID player, int hp)
    {
        health.getScore(Bukkit.getOfflinePlayer(player).getName()).setScore(hp);
    }

    @Override
    public int getPlayerHealth(UUID player)
    {
        return health.getScore(Bukkit.getOfflinePlayer(player).getName()).getScore();
    }
}
