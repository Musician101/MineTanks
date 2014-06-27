package musician101.minetanks.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
	Objective teamCount;
	Score greenScore;
	Score redScore;
	
	public MTScoreboard()
	{
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		board = sbm.getNewScoreboard();
		green = board.registerNewTeam("green");
		green.setDisplayName("Green Team");
		red = board.registerNewTeam("red");
		red.setDisplayName("Red Team");
		teamCount = board.registerNewObjective("teamcount", "dummy");
		teamCount.setDisplaySlot(DisplaySlot.SIDEBAR);
		teamCount.setDisplayName("Team Count");
		greenScore = teamCount.getScore(ChatColor.GREEN + "Green Team");
		redScore = teamCount.getScore(ChatColor.RED + "Red Team");
	}
	
	public Scoreboard getScoreboard()
	{
		return board;
	}
	
	public void addGreenPlayer(OfflinePlayer offlinePlayer)
	{
		green.addPlayer(offlinePlayer);
		greenScore.setScore(green.getSize());
	}
	
	public void addRedPlayer(OfflinePlayer offlinePlayer)
	{
		red.addPlayer(offlinePlayer);
		redScore.setScore(red.getSize());
	}
	
	public void playerDeath(Player player)
	{
		if (green.getPlayers().contains(player))
			greenPlayerDeath(player);
		else if (red.getPlayers().contains(player))
			redPlayerDeath(player);
	}
	
	private void greenPlayerDeath(Player player)
	{
		green.removePlayer(player);
		greenScore.setScore(green.getSize());
	}
	
	private void redPlayerDeath(Player player)
	{
		red.removePlayer(player);
		redScore.setScore(red.getSize());
	}
	
	public boolean isOnGreen(Player player)
	{
		return green.getPlayers().contains(player);
	}
	
	public boolean isOnRed(Player player)
	{
		return red.getPlayers().contains(player);
	}
	
	public int getGreenTeamSize()
	{
		return green.getSize();
	}
	
	public int getRedTeamSize()
	{
		return red.getSize();
	}
}
