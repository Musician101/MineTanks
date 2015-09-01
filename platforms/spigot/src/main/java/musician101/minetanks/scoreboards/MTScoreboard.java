package musician101.minetanks.scoreboards;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class MTScoreboard
{
	Scoreboard board;
	Team green;
	Team red;
	Objective health;
	Objective teamCount;
	Score greenScore;
	Score redScore;
	
	public MTScoreboard()
	{
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		board = sbm.getNewScoreboard();
		green = board.registerNewTeam("green");
		green.setDisplayName("Green Team");
		green.setPrefix(ChatColor.GREEN + "");
		red = board.registerNewTeam("red");
		red.setDisplayName("Red Team");
		red.setPrefix(ChatColor.RED + "");
		teamCount = board.registerNewObjective("teamcount", "dummy");
		teamCount.setDisplaySlot(DisplaySlot.SIDEBAR);
		teamCount.setDisplayName("Team Count");
		greenScore = teamCount.getScore(ChatColor.GREEN + "Green Team");
		redScore = teamCount.getScore(ChatColor.RED + "Red Team");
		health = board.registerNewObjective("health", "dummy");
		health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		health.setDisplayName("Health");
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
