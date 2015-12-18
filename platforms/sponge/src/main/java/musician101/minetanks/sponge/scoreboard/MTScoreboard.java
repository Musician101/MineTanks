package musician101.minetanks.sponge.scoreboard;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.minetanks.common.CommonReference.CommonScoreboard;
import musician101.minetanks.common.util.AbstractScoreboard;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.UUID;

public class MTScoreboard extends AbstractScoreboard<Scoreboard>
{
    public MTScoreboard()
    {
        scoreboard = Scoreboard.builder().build();

        Objective.Builder ob = Objective.builder();
        ob.criterion(Criteria.DUMMY);
        ob.name(CommonScoreboard.HEALTH_ID);
        ob.displayName(Texts.of(CommonScoreboard.HEALTH_NAME));
        ob.objectiveDisplayMode(ObjectiveDisplayModes.INTEGER);
        scoreboard.addObjective(ob.build(), DisplaySlots.BELOW_NAME);

        ob.reset();
        ob.criterion(Criteria.DUMMY);
        ob.name(CommonScoreboard.TEAM_COUNT_ID);
        ob.displayName(Texts.of(CommonScoreboard.TEAM_COUNT_NAME));
        ob.objectiveDisplayMode(ObjectiveDisplayModes.INTEGER);
        Objective teamCount = ob.build();
        Score greenScore = teamCount.getScore(TextUtils.greenText(CommonScoreboard.GREEN_NAME));
        Score redScore = teamCount.getScore(TextUtils.redText(CommonScoreboard.RED_NAME));
        teamCount.addScore(greenScore);
        teamCount.addScore(redScore);
        scoreboard.addObjective(teamCount, DisplaySlots.SIDEBAR);

        Team.Builder tb = Team.builder();
        tb.color(TextColors.GREEN);
        tb.displayName(Texts.of(CommonScoreboard.GREEN_NAME));
        tb.name(CommonScoreboard.GREEN_ID);
        scoreboard.addTeam(tb.build());

        tb.reset();
        tb.color(TextColors.RED);
        tb.displayName(Texts.of(CommonScoreboard.RED_NAME));
        tb.name(CommonScoreboard.RED_ID).build();
        scoreboard.addTeam(tb.build());
    }

    @Override
    public void addGreenPlayer(UUID playerId)
    {
        addPlayer(playerId, CommonScoreboard.GREEN_ID);
    }

    @Override
    public void addRedPlayer(UUID playerId)
    {
        addPlayer(playerId, CommonScoreboard.RED_ID);
    }

    private void addPlayer(UUID playerId, String teamName)
    {
        scoreboard.getTeam(teamName).get().addMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
        updateTeamSize(teamName);
    }

    @Override
    public void playerDeath(UUID playerId)
    {
        if (scoreboard.getTeam(CommonScoreboard.GREEN_ID).get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName())))
        {
            scoreboard.getTeam(CommonScoreboard.GREEN_ID).get().removeMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
            updateTeamSize(CommonScoreboard.GREEN_ID);
        }
        else if (scoreboard.getTeam(CommonScoreboard.RED_ID).get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName())))
        {
            scoreboard.getTeam(CommonScoreboard.RED_ID).get().removeMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
            updateTeamSize(CommonScoreboard.RED_ID);
        }

        Player player = MTUtils.getPlayer(playerId);
        player.setScoreboard(player.getWorld().getScoreboard());
    }

    @Override
    public boolean isOnGreen(UUID playerId)
    {
        return scoreboard.getTeam(CommonScoreboard.GREEN_ID).get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName()));
    }

    @Override
    public boolean isOnRed(UUID playerId)
    {
        return scoreboard.getTeam(CommonScoreboard.RED_ID).get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName()));
    }

    @Override
    public int getGreenTeamSize()
    {
        return scoreboard.getTeam(CommonScoreboard.GREEN_ID).get().getMembers().size();
    }

    @Override
    public int getRedTeamSize()
    {
        return scoreboard.getTeam(CommonScoreboard.RED_ID).get().getMembers().size();
    }

    @Override
    public int getPlayerHealth(UUID playerId)
    {
        return scoreboard.getObjective(CommonScoreboard.HEALTH_ID).get().getScore(Texts.of(MTUtils.getPlayer(playerId).getName())).getScore();
    }

    @Override
    public void setPlayerHealth(UUID playerId, int hp)
    {
        scoreboard.getObjective(CommonScoreboard.HEALTH_ID).get().getScore(Texts.of(MTUtils.getPlayer(playerId).getName())).setScore(hp);
    }

    private void updateTeamSize(String teamName)
    {
        scoreboard.getObjective(CommonScoreboard.TEAM_COUNT_ID).get().getScore(Texts.of(teamName)).setScore(scoreboard.getTeam(teamName.equalsIgnoreCase(CommonScoreboard.GREEN_ID) ? CommonScoreboard.GREEN_NAME : CommonScoreboard.RED_NAME).get().getMembers().size());
    }
}
