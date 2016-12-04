package io.musician101.minetanks.forge.battlefield.player;

import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank;
import io.musician101.minetanks.forge.tank.ForgeTank;
import io.musician101.minetanks.forge.tank.modules.cannon.ForgeAutoLoader;
import io.musician101.minetanks.forge.tank.modules.cannon.ForgeCannon;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ForgePlayerTank extends AbstractPlayerTank<ForgeTank, Object>
{
    private final MinecraftServer server;

    public ForgePlayerTank(UUID uuid, MTTeam team, MinecraftServer server)
    {
        super(uuid, team);
        this.server = server;
    }

    @Override
    public void cancelReload()
    {
        if (reloadTaskID != null)
        {
            MinecraftForge.EVENT_BUS.unregister(this);
            reloadTaskID = null;
        }
    }

    @Override
    public boolean isReloading()
    {
        @SuppressWarnings("ConstantConditions")
        EntityPlayerMP player = DimensionManager.getWorld(0).getMinecraftServer().getPlayerList().getPlayerByUUID(getPlayerId());
        if (player.experienceLevel > 0)
            return true;

        ForgeCannon cannon = getTank().getCannon();
        //TODO reload timers still not working for some reason
        //int time = (int) (!(cannon instanceof ForgeAutoLoader) || clipSize == 1 ? cannon.getReloadTime() : ((ForgeAutoLoader) cannon).getCycleTime());
        int time;
        if (clipSize == 1)
        {
            if (!(cannon instanceof ForgeAutoLoader))
                time = (int) (cannon.getReloadTime() * 2);
            else
                time = (int) (cannon.getReloadTime() / 2);
        }
        else
        {
            time = (int) (((ForgeAutoLoader) cannon).getCycleTime() * 2);
        }

        player.removeExperienceLevel(player.experienceLevel);
        player.addExperienceLevel(time);
        MinecraftForge.EVENT_BUS.register(this);
        return false;
    }

    //TODO may not work, currently a substitute for Forge not having a scheduler
    @SubscribeEvent
    public void onTick(ServerTickEvent event)
    {
        EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(getPlayerId());
        player.removeExperienceLevel(1);
        if (player.experienceLevel < 1)
            cancelReload();
    }

    @Override
    public void setTank(ForgeTank tank)
    {
        super.setTank(tank);
        if (tank == null)
            clipSize = 0;
        else if (tank.getCannon() instanceof ForgeAutoLoader)
            this.clipSize = ((ForgeAutoLoader) tank.getCannon()).getClipSize();
        else
            this.clipSize = 1;
    }
}
