package io.musician101.minetanks.forge.command;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.command.edit.CreateForgeCommand;
import io.musician101.minetanks.forge.command.edit.EnableForgeCommand;
import io.musician101.minetanks.forge.command.edit.ForceEndForgeCommand;
import io.musician101.minetanks.forge.command.edit.GreenSpawnForgeCommand;
import io.musician101.minetanks.forge.command.edit.RedSpawnForgeCommand;
import io.musician101.minetanks.forge.command.edit.RegionForgeCommand;
import io.musician101.minetanks.forge.command.edit.RemoveForgeCommand;
import io.musician101.minetanks.forge.command.edit.SpectatorsForgeCommand;
import io.musician101.minetanks.forge.command.edit.StatusForgeCommand;
import io.musician101.minetanks.forge.command.participate.JoinForgeCommand;
import io.musician101.minetanks.forge.command.participate.LeaveForgeCommand;
import io.musician101.minetanks.forge.command.participate.SpectateForgeCommand;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class MTForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public MTForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonReference.ID, CommonReference.DESCRIPTION, CommonCommands.MT_CMD, 0);
    }

    @Override
    protected void build()
    {
        addArgument(new JoinForgeCommand());
        addArgument(new LeaveForgeCommand());
        addArgument(new RemoveForgeCommand());
        addArgument(new SpectateForgeCommand());
        addArgument(new CreateForgeCommand());
        addArgument(new RegionForgeCommand());
        addArgument(new EnableForgeCommand());
        addArgument(new ForceEndForgeCommand());
        addArgument(new GreenSpawnForgeCommand());
        addArgument(new RedSpawnForgeCommand());
        addArgument(new SpectatorsForgeCommand());
        addArgument(new StatusForgeCommand());
    }
}
