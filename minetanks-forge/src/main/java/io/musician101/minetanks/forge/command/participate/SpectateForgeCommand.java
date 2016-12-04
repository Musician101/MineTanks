package io.musician101.minetanks.forge.command.participate;

import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.ForgeBattleField;
import io.musician101.musicianlibrary.java.minecraft.forge.TextComponentUtils;
import io.musician101.musicianlibrary.java.minecraft.forge.command.AbstractForgeCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class SpectateForgeCommand extends AbstractForgeCommand<ForgeMineTanks>
{
    public SpectateForgeCommand()
    {
        super(ForgeMineTanks.instance, CommonCommands.SPECTATE_NAME, CommonCommands.SPECTATE_DESC, CommonCommands.MT_CMD + " " + CommonCommands.SPECTATE_NAME + " <" + TextFormatting.ITALIC + CommonCommands.FIELD + TextFormatting.RESET + ">", 1);
    }

    @Override
    protected void build()//NOSONAR
    {
        setConsumer((server, sender, args) -> {//NOSONAR
            if (!checkPermission(server, sender))
            {
                return;
            }

            EntityPlayerMP player;
            try
            {
                player = CommandBase.getCommandSenderAsPlayer(sender);
            }
            catch (PlayerNotFoundException e)//NOSONAR
            {
                return;
            }

            if (!minArgsMet(player, args.size(), TextComponentUtils.redText(CommonMessages.FIELD_NOT_SPECIFIED)))
                return;

            ForgeBattleField field = ForgeMineTanks.instance.getFieldStorage().getField(args.get(0));
            if (field == null)
            {
                player.sendMessage(TextComponentUtils.redText(CommonMessages.FIELD_DNE));
                return;
            }

            if (!field.isReady())
            {
                player.sendMessage(TextComponentUtils.redText(CommonMessages.FIELD_NOT_READY));
                return;
            }

            field.addPlayer(player, MTTeam.SPECTATOR);
        });
    }
}
