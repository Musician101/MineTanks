package io.musician101.minetanks.forge.scoreboard;

import com.google.common.collect.Sets;
import io.musician101.minetanks.common.CommonReference.CommonScoreboard;
import io.musician101.minetanks.common.util.AbstractScoreboard;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ForgeMTScoreboard extends AbstractScoreboard<EntityPlayerMP, ServerScoreboard>
{
    private final ScorePlayerTeam green;
    private final Score greenScore;
    private final ScoreObjective health;
    private final ScorePlayerTeam red;
    private final Score redScore;

    public ForgeMTScoreboard(MinecraftServer server)
    {
        super();
        this.scoreboard = new ServerScoreboard(server);
        green = scoreboard.createTeam(CommonScoreboard.GREEN_ID);
        green.setTeamName(CommonScoreboard.GREEN_NAME);
        green.setNamePrefix(TextFormatting.GREEN + "");
        red = scoreboard.createTeam(CommonScoreboard.RED_ID);
        red.setTeamName(CommonScoreboard.RED_NAME);
        red.setNamePrefix(TextFormatting.RED + "");
        ScoreObjective teamCount = scoreboard.addScoreObjective(CommonScoreboard.TEAM_COUNT_ID, IScoreCriteria.DUMMY);
        teamCount.setDisplayName(CommonScoreboard.TEAM_COUNT_NAME);
        scoreboard.setObjectiveInDisplaySlot(1, teamCount);
        greenScore = scoreboard.getOrCreateScore(TextFormatting.GREEN + CommonScoreboard.GREEN_NAME, teamCount);
        redScore = scoreboard.getOrCreateScore(TextFormatting.RED + CommonScoreboard.RED_NAME, teamCount);
        health = scoreboard.addScoreObjective(CommonScoreboard.HEALTH_ID, IScoreCriteria.HEALTH);
        health.setDisplayName(CommonScoreboard.HEALTH_NAME);
        scoreboard.setObjectiveInDisplaySlot(0, health);
    }

    @Override
    public void addGreenPlayer(EntityPlayerMP player)
    {
        scoreboard.addPlayerToTeam(player.getName(), green.getTeamName());
        greenScore.setScorePoints(getGreenTeamSize());
    }

    @Override
    public void addRedPlayer(EntityPlayerMP player)
    {
        scoreboard.addPlayerToTeam(player.getName(), red.getTeamName());
        redScore.setScorePoints(getRedTeamSize());
    }

    @Override
    public int getGreenTeamSize()
    {
        return green.getMembershipCollection().size();
    }

    @Override
    public int getPlayerHealth(EntityPlayerMP player)
    {
        return scoreboard.getOrCreateScore(player.getName(), health).getScorePoints();
    }

    @Override
    public int getRedTeamSize()
    {
        return red.getMembershipCollection().size();
    }

    private void greenPlayerDeath(EntityPlayerMP player)
    {
        scoreboard.removePlayerFromTeam(player.getName(), green);
        greenScore.setScorePoints(getGreenTeamSize());
    }

    @Override
    public boolean isOnGreen(EntityPlayerMP player)
    {
        return green.getMembershipCollection().contains(player.getName());
    }

    @Override
    public boolean isOnRed(EntityPlayerMP player)
    {
        return red.getMembershipCollection().contains(player.getName());
    }

    @Override
    public void playerDeath(EntityPlayerMP player)
    {
        if (isOnGreen(player))
            greenPlayerDeath(player);
        else if (isOnRed(player))
            redPlayerDeath(player);

        setPlayerScoreboard(player);
    }

    private void redPlayerDeath(EntityPlayerMP player)
    {
        scoreboard.removePlayerFromTeam(player.getName(), red);
        redScore.setScorePoints(getRedTeamSize());
    }

    @Override
    public void resetPlayerScoreboard(EntityPlayerMP player)
    {
        sendScoreboard(player, (ServerScoreboard) player.getWorldScoreboard());
    }

    @Override
    public void setPlayerHealth(EntityPlayerMP player, double hp)
    {
        scoreboard.getOrCreateScore(player.getName(), health).setScorePoints((int) hp);
    }

    @Override
    public void setPlayerScoreboard(EntityPlayerMP player)
    {
        sendScoreboard(player, scoreboard);
    }

    private void sendScoreboard(EntityPlayerMP player, ServerScoreboard scoreboard)
    {
        Set<ScoreObjective> set = Sets.newHashSet();
        scoreboard.getTeams().forEach(team -> new SPacketTeams(team, 0));
        for (int i = 0; i < 19; ++i)
        {
            ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(i);
            if (scoreobjective != null && !set.contains(scoreobjective))
            {
                scoreboard.getCreatePackets(scoreobjective).forEach(player.connection::sendPacket);
                set.add(scoreobjective);
            }
        }
    }
}
