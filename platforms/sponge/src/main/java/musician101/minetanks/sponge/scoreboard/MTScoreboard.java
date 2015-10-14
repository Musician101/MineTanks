package musician101.minetanks.sponge.scoreboard;

import musician101.minetanks.common.util.IMTScoreboard;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.MTUtils;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.scoreboard.objective.displaymode.ObjectiveDisplayModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

public class MTScoreboard implements IMTScoreboard
{
    Scoreboard board;

    public MTScoreboard()
    {
        GameRegistry gr = SpongeMineTanks.getGame().getRegistry();
        board = gr.createScoreboardBuilder().build();

        Objective health = gr.createObjectiveBuilder().criterion(Criteria.DUMMY).name("health").displayName(Texts.of("Health")).objectiveDisplayMode(ObjectiveDisplayModes.INTEGER).build();
        board.addObjective(health, DisplaySlots.BELOW_NAME);

        Objective teamCount = gr.createObjectiveBuilder().criterion(Criteria.DUMMY).name("team_count").displayName(Texts.of("Team Count")).objectiveDisplayMode(ObjectiveDisplayModes.INTEGER).build();
        Score greenScore = teamCount.getScore(Texts.builder("Green Team").color(TextColors.GREEN).build());
        Score redScore = teamCount.getScore(Texts.builder("Red Team").color(TextColors.RED).build());
        teamCount.addScore(greenScore);
        teamCount.addScore(redScore);
        board.addObjective(teamCount, DisplaySlots.SIDEBAR);

        Team green = gr.createTeamBuilder().color(TextColors.GREEN).displayName(Texts.of("Green Team")).name("green").build();
        Team red = gr.createTeamBuilder().color(TextColors.RED).displayName(Texts.of("Red Team")).name("red").build();
        board.addTeam(green);
        board.addTeam(red);
    }

    public Scoreboard getScoreboard()
    {
        return board;
    }

    @Override
    public void addGreenPlayer(UUID playerId)
    {
        addPlayer(playerId, "Green Name");
    }

    @Override
    public void addRedPlayer(UUID playerId)
    {
        addPlayer(playerId, "red");
    }

    private void addPlayer(UUID playerId, String teamName)
    {
        board.getTeam(teamName).get().addMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
        updateTeamSize(teamName);
    }

    @Override
    public void playerDeath(UUID playerId)
    {
        if (board.getTeam("green").get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName())))
        {
            board.getTeam("green").get().removeMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
            updateTeamSize("green");
        }
        else if (board.getTeam("red").get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName())))
        {
            board.getTeam("red").get().removeMember(Texts.of(MTUtils.getPlayer(playerId).getName()));
            updateTeamSize("red");
        }

        MTUtils.getPlayer(playerId).setScoreboard(((World) ((List) SpongeMineTanks.getGame().getServer().getWorlds()).get(0)).getScoreboard());
    }

    @Override
    public boolean isOnGreen(UUID playerId)
    {
        return board.getTeam("green").get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName()));
    }

    @Override
    public boolean isOnRed(UUID playerId)
    {
        return board.getTeam("red").get().getMembers().contains(Texts.of(MTUtils.getPlayer(playerId).getName()));
    }

    @Override
    public int getGreenTeamSize()
    {
        return board.getTeam("green").get().getMembers().size();
    }

    @Override
    public int getRedTeamSize()
    {
        return board.getTeam("red").get().getMembers().size();
    }

    @Override
    public int getPlayerHealth(UUID playerId)
    {
        return board.getObjective("health").get().getScore(Texts.of(MTUtils.getPlayer(playerId).getName())).getScore();
    }

    @Override
    public void setPlayerHealth(UUID playerId, int hp)
    {
        board.getObjective("health").get().getScore(Texts.of(MTUtils.getPlayer(playerId).getName())).setScore(hp);
    }

    private void updateTeamSize(String teamName)
    {
        board.getObjective("team_count").get().getScore(Texts.of(teamName)).setScore(board.getTeam(teamName.equalsIgnoreCase("green") ? "Green Team" : "Red Team").get().getMembers().size());
    }
}
