package io.musician101.minetanks.sponge.scoreboard;

import io.musician101.minetanks.common.CommonReference.CommonScoreboard;
import io.musician101.minetanks.common.util.AbstractScoreboard;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class SpongeMTScoreboard extends AbstractScoreboard<Player, Scoreboard> {

    public SpongeMTScoreboard() {
        scoreboard = Scoreboard.builder().build();

        Objective.Builder ob = Objective.builder();
        ob.criterion(Criteria.DUMMY);
        ob.name(CommonScoreboard.HEALTH_ID);
        ob.displayName(Text.of(CommonScoreboard.HEALTH_NAME));
        ob.objectiveDisplayMode(ObjectiveDisplayModes.INTEGER);
        Objective healthObjective = ob.build();
        scoreboard.addObjective(ob.build());
        scoreboard.updateDisplaySlot(healthObjective, DisplaySlots.BELOW_NAME);

        ob.reset();
        ob.criterion(Criteria.DUMMY);
        ob.name(CommonScoreboard.TEAM_COUNT_ID);
        ob.displayName(Text.of(CommonScoreboard.TEAM_COUNT_NAME));
        ob.objectiveDisplayMode(ObjectiveDisplayModes.INTEGER);
        Objective teamCount = ob.build();
        Score greenScore = teamCount.getOrCreateScore(TextUtils.greenText(CommonScoreboard.GREEN_NAME));
        Score redScore = teamCount.getOrCreateScore(TextUtils.redText(CommonScoreboard.RED_NAME));
        teamCount.addScore(greenScore);
        teamCount.addScore(redScore);
        Objective teamCountObjective = ob.build();
        scoreboard.addObjective(teamCount);
        scoreboard.updateDisplaySlot(teamCountObjective, DisplaySlots.SIDEBAR);

        Team.Builder tb = Team.builder();
        tb.color(TextColors.GREEN);
        tb.displayName(Text.of(CommonScoreboard.GREEN_NAME));
        tb.name(CommonScoreboard.GREEN_ID);
        scoreboard.registerTeam(tb.build());

        tb.reset();
        tb.color(TextColors.RED);
        tb.displayName(Text.of(CommonScoreboard.RED_NAME));
        tb.name(CommonScoreboard.RED_ID).build();
        scoreboard.registerTeam(tb.build());
    }


    @Override
    public void addGreenPlayer(Player player) {
        addPlayer(player, CommonScoreboard.GREEN_ID);
    }

    private void addPlayer(Player player, String teamName) {
        scoreboard.getTeam(teamName).ifPresent(team -> team.addMember(Text.of(player.getName())));
        updateTeamSize(teamName);
    }

    @Override
    public void addRedPlayer(Player player) {
        addPlayer(player, CommonScoreboard.RED_ID);
    }

    @Override
    public int getGreenTeamSize() {
        return scoreboard.getTeam(CommonScoreboard.GREEN_ID).map(team -> team.getMembers().size()).orElse(0);
    }

    @Override
    public int getPlayerHealth(Player player) {
        return scoreboard.getObjective(CommonScoreboard.HEALTH_ID).map(objective ->
                objective.getScore(Text.of(player.getName())).map(Score::getScore).orElse(0)).orElse(0);
    }

    @Override
    public int getRedTeamSize() {
        return scoreboard.getTeam(CommonScoreboard.RED_ID).map(team -> team.getMembers().size()).orElse(0);
    }

    @Override
    public boolean isOnGreen(Player player) {
        return !scoreboard.getTeam(CommonScoreboard.GREEN_ID).flatMap(team ->
        {
            if (team.getMembers().contains(Text.of(player.getName())))
                return Optional.of(team);

            return Optional.empty();
        }).isPresent();
    }

    @Override
    public boolean isOnRed(Player player) {
        return !scoreboard.getTeam(CommonScoreboard.RED_ID).flatMap(team ->
        {
            if (team.getMembers().contains(Text.of(player.getName())))
                return Optional.of(team);

            return Optional.empty();
        }).isPresent();
    }

    @Override
    public void playerDeath(Player player) {
        Text member = Text.of(player.getName());
        scoreboard.getMemberTeam(member).ifPresent(team ->
        {
            team.removeMember(member);
            updateTeamSize(team.getName());
        });
        Sponge.getServer().getServerScoreboard().ifPresent(player::setScoreboard);
    }

    @Override
    public void resetPlayerScoreboard(Player player) {
        Sponge.getServer().getServerScoreboard().ifPresent(player::setScoreboard);
    }

    @Override
    public void setPlayerHealth(Player player, double hp) {
        scoreboard.getObjective(CommonScoreboard.HEALTH_ID).ifPresent(objective ->
                objective.getScore(Text.of(player.getName())).ifPresent(score ->
                        score.setScore((int) hp)));
    }

    @Override
    public void setPlayerScoreboard(Player player) {
        player.setScoreboard(getScoreboard());
    }

    private void updateTeamSize(String teamName) {
        scoreboard.getObjective(CommonScoreboard.TEAM_COUNT_ID).ifPresent(objective ->
                objective.getScore(Text.of(teamName)).ifPresent(score ->
                        score.setScore(scoreboard.getTeam(teamName.equalsIgnoreCase(CommonScoreboard.GREEN_ID) ? CommonScoreboard.GREEN_NAME : CommonScoreboard.RED_NAME)
                                .map(team -> team.getMembers().size()).orElse(0))));
    }
}
