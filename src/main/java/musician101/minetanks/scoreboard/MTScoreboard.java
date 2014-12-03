package musician101.minetanks.scoreboard;

import java.util.UUID;

import org.spongepowered.api.entity.player.Player;

//TODO no Scoreboard api
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
		player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}
	
	private void redPlayerDeath(Player player)
	{
		red.removePlayer(player);
		redScore.setScore(red.getSize());
		player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
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
	
	public void setPlayerHealth(UUID player, int hp)
	{
		health.getScore(Bukkit.getOfflinePlayer(player).getName()).setScore(hp);
	}
	
	public int getPlayerHealth(UUID player)
	{
		return health.getScore(Bukkit.getOfflinePlayer(player).getName()).getScore();
	}
}
