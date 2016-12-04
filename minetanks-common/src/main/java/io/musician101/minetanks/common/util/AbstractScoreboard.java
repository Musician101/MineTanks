package io.musician101.minetanks.common.util;


public abstract class AbstractScoreboard<P, S>
{
    protected S scoreboard;

    protected AbstractScoreboard()
    {

    }


    public abstract boolean isOnGreen(P player);


    public abstract boolean isOnRed(P player);

    public abstract int getGreenTeamSize();

    public abstract int getRedTeamSize();


    public abstract int getPlayerHealth(P player);


    public abstract void setPlayerScoreboard(P player);


    public abstract void resetPlayerScoreboard(P player);


    public S getScoreboard()
    {
        return scoreboard;
    }

    public abstract void addGreenPlayer(P player);

    public abstract void addRedPlayer(P player);


    public abstract void playerDeath(P player);


    public abstract void setPlayerHealth(P player, double health);
}
