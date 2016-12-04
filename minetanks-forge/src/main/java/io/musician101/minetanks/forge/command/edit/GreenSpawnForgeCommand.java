package io.musician101.minetanks.forge.command.edit;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.forge.TextComponentUtils;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;
import io.musician101.musicianlibrary.java.minecraft.forge.command.ForgeCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.forge.command.ForgeCommandUsage;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class GreenSpawnForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public GreenSpawnForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.GREEN_SPAWN_NAME, CommonCommands.GREEN_SPAWN_DESC, new ForgeCommandUsage(1, new ForgeCommandArgument(CommonCommands.MT_CMD), new ForgeCommandArgument(CommonCommands.GREEN_SPAWN_NAME), new ForgeCommandArgument(CommonCommands.FIELD, Syntax.REQUIRED, Syntax.REPLACE)));
    }

    @Override
    public void build()
    {
        setConsumer((server, sender, args) -> {
            if (!checkPermission(server, sender)) {
                sender.sendMessage(TextComponentUtils.redText(CommonMessages.NO_PERMISSION));
                return;
            }

            if (!minArgsMet(sender, args.size(), TextComponentUtils.redText(CommonMessages.FIELD_NOT_SPECIFIED)))
                return;

            EntityPlayerMP player;
            try {
                player = getCommandSenderAsPlayer(sender);
            }
            catch (PlayerNotFoundException e) {
                sender.sendMessage(TextComponentUtils.redText(CommonMessages.PLAYER_ONLY));
                return;
            }

            ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args.get(0));
            if (field == null)
            {
                sender.sendMessage(TextComponentUtils.redText(CommonMessages.FIELD_DNE));
                return;
            }

            BlockPos bp = player.getPosition();
            if (field.getRegion() == null || !field.getRegion().isInRegion(bp, player.dimension))
            {
                player.sendMessage(TextComponentUtils.redText(CommonMessages.LOCATION_NOT_IN_REGION));
                return;
            }

            field.setGreenSpawn(bp);
            player.sendMessage(TextComponentUtils.greenText(CommonMessages.GREEN_SPAWN_SET));
        });
    }
}
