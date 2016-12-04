package io.musician101.minetanks.spigot.scoreboard;

import io.musician101.minetanks.common.CommonReference.CommonScoreboard;
import io.musician101.minetanks.common.util.AbstractScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SpigotMTScoreboard extends AbstractScoreboard<Player, Scoreboard>
{
    private final Team green;
    private final Score greenScore;
    private final Objective health;
    private final Team red;
    private final Score redScore;

    public SpigotMTScoreboard()
    {
        super();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        green = scoreboard.registerNewTeam(CommonScoreboard.GREEN_ID);
        green.setDisplayName(CommonScoreboard.GREEN_NAME);
        green.setPrefix(ChatColor.GREEN + "");
        red = scoreboard.registerNewTeam(CommonScoreboard.RED_ID);
        red.setDisplayName(CommonScoreboard.RED_NAME);
        red.setPrefix(ChatColor.RED + "");
        Objective teamCount = scoreboard.registerNewObjective(CommonScoreboard.TEAM_COUNT_ID, CommonScoreboard.DUMMY);
        teamCount.setDisplaySlot(DisplaySlot.SIDEBAR);
        teamCount.setDisplayName(CommonScoreboard.TEAM_COUNT_NAME);
        greenScore = teamCount.getScore(ChatColor.GREEN + CommonScoreboard.GREEN_NAME);
        redScore = teamCount.getScore(ChatColor.RED + CommonScoreboard.RED_NAME);
        health = scoreboard.registerNewObjective(CommonScoreboard.HEALTH_ID, CommonScoreboard.DUMMY);
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        health.setDisplayName(CommonScoreboard.HEALTH_NAME);
    }

    @Override
    public void addGreenPlayer(Player player)
    {
        green.addEntry(player.getName());
        greenScore.setScore(green.getSize());
    }

    @Override
    public void addRedPlayer(Player player)
    {
        red.addEntry(player.getName());
        redScore.setScore(red.getSize());
    }

    @Override
    public int getGreenTeamSize()
    {
        return green.getSize();
    }

    @Override
    public int getPlayerHealth(Player player)
    {
        return health.getScore(player.getName()).getScore();
    }

    @Override
    public int getRedTeamSize()
    {
        return red.getSize();
    }

    private void greenPlayerDeath(Player player)
    {
        green.removeEntry(player.getName());
        greenScore.setScore(green.getSize());
    }

    @Override
    public boolean isOnGreen(Player player)
    {
        return green.getEntries().contains(player.getName());
    }

    @Override
    public boolean isOnRed(Player player)
    {
        return red.getEntries().contains(player.getName());
    }

    @Override
    public void playerDeath(Player player)
    {
        if (isOnGreen(player))
            greenPlayerDeath(player);
        else if (isOnRed(player))
            redPlayerDeath(player);

        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    private void redPlayerDeath(Player player)
    {
        red.removeEntry(player.getName());
        redScore.setScore(red.getSize());
    }

    @Override
    public void resetPlayerScoreboard(Player player)
    {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @Override
    public void setPlayerHealth(Player player, double hp)
    {
        health.getScore(player.getName()).setScore((int) hp);
    }

    @Override
    public void setPlayerScoreboard(Player player)
    {
        player.setScoreboard(scoreboard);
    }
}
