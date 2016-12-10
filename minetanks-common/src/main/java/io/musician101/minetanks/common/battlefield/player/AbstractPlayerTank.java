package io.musician101.minetanks.common.battlefield.player;

import io.musician101.minetanks.common.tank.AbstractTank;
import java.util.UUID;


public abstract class AbstractPlayerTank<T extends AbstractTank, I> {

    private final UUID uuid;
    protected int clipSize = 1;
    protected I reloadTaskID;
    private boolean isReady = false;
    private T tank;
    private MTTeam team;

    protected AbstractPlayerTank(UUID uuid, MTTeam team) {
        this.uuid = uuid;
        this.team = team;
    }

    public abstract void cancelReload();

    public int getClipSize() {
        return clipSize;
    }

    public void setClipSize(int clipSize) {
        this.clipSize = clipSize;
    }

    public UUID getPlayerId() {
        return uuid;
    }

    public T getTank() {
        return tank;
    }

    public void setTank(T tank) {
        this.tank = tank;
    }

    public MTTeam getTeam() {
        return team;
    }

    public void setTeam(MTTeam team) {
        this.team = team;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public abstract boolean isReloading();

    public void killed() {
        this.team = MTTeam.SPECTATOR;
        tank = null;
        cancelReload();
    }

    public enum MTTeam {
        ASSIGNED(false),
        SPECTATOR(),
        UNASSIGNED();

        final boolean canExit;

        MTTeam() {
            this(true);
        }

        MTTeam(boolean canExit) {
            this.canExit = canExit;
        }

        public boolean canExit() {
            return canExit;
        }
    }
}
