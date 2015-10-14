package musician101.minetanks.common.util;

import java.util.UUID;

public interface IMTScoreboard
{
    void addGreenPlayer(UUID playerId);

    void addRedPlayer(UUID playerId);

    void playerDeath(UUID playerId);

    boolean isOnGreen(UUID playerId);

    boolean isOnRed(UUID playerId);

    int getGreenTeamSize();

    int getRedTeamSize();

    int getPlayerHealth(UUID playerId);

    void setPlayerHealth(UUID playerId, int health);
}
