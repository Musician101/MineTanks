package musician101.minetanks.spigot.scoreboards;

import musician101.minetanks.common.CommonReference.CommonScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class MTScoreboard
{
    private final Scoreboard board;
    private final Team green;
    private final Team red;
    private final Objective health;
    private final Objective teamCount;
    private final Score greenScore;
    private final Score redScore;

    public MTScoreboard()
    {
        ScoreboardManager sbm = Bukkit.getScoreboardManager();
        board = sbm.getNewScoreboard();
        green = board.registerNewTeam(CommonScoreboard.GREEN_ID);
        green.setDisplayName(CommonScoreboard.GREEN_ID);
        green.setPrefix(ChatColor.GREEN + "");
        red = board.registerNewTeam(CommonScoreboard.RED_ID);
        red.setDisplayName(CommonScoreboard.RED_NAME);
        red.setPrefix(ChatColor.RED + "");
        teamCount = board.registerNewObjective(CommonScoreboard.TEAM_COUNT_ID, CommonScoreboard.DUMMY);
        teamCount.setDisplaySlot(DisplaySlot.SIDEBAR);
        teamCount.setDisplayName(CommonScoreboard.TEAM_COUNT_NAME);
        greenScore = teamCount.getScore(ChatColor.GREEN + CommonScoreboard.GREEN_NAME);
        redScore = teamCount.getScore(ChatColor.RED + CommonScoreboard.RED_NAME);
        health = board.registerNewObjective(CommonScoreboard.HEALTH_ID, CommonScoreboard.DUMMY);
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        health.setDisplayName(CommonScoreboard.HEALTH_NAME);
    }

    public Scoreboard getScoreboard()
    {
        return board;
    }

    public void addGreenPlayer(Player player)
    {
        green.addEntry(player.getName());
        greenScore.setScore(green.getSize());
    }

    public void addRedPlayer(Player player)
    {
        red.addEntry(player.getName());
        redScore.setScore(red.getSize());
    }

    public void playerDeath(Player player)
    {
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

    public boolean isOnGreen(Player player)
    {
        return green.getEntries().contains(player.getName());
    }

    public boolean isOnRed(Player player)
    {
        return red.getEntries().contains(player.getName());
    }

    public int getGreenTeamSize()
    {
        return green.getSize();
    }

    public int getRedTeamSize()
    {
        return red.getSize();
    }

    public void setPlayerHealth(UUID player, int hp)
    {
        health.getScore(Bukkit.getOfflinePlayer(player).getName()).setScore(hp);
    }

    public int getPlayerHealth(UUID player)
    {
        return health.getScore(Bukkit.getOfflinePlayer(player).getName()).getScore();
    }
}
