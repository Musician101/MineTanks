package musician101.minetanks.sponge.command.edit;

import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.minetanks.common.CommonReference.CommonCommands;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.lib.SpongeReference.SpongeMessages;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ForceEndCommand extends AbstractSpongeCommand
{
    public ForceEndCommand()
    {
        super(CommonCommands.FORCE_END_NAME, CommonCommands.FORCE_END_DESC, Arrays.asList(Texts.of("/" + CommonCommands.MT), Texts.of("forceend"), Texts.of("field")), 1, "minetanks.edit", false, SpongeMessages.NO_PERMISSION, SpongeMessages.PLAYER_ONLY);
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        String[] args = splitArgs(arguments);
        if (!testPermission(source))
            return CommandResult.empty();

        if (minArgsMet(source, args.length, ChatColor.RED + plugin.getPrefix() + " Error: Field not specified."))
            return false;

        BattleField field = plugin.getFieldStorage().getField(args[0]);
        if (field == null)
        {
            source.sendMessage(ChatColor.RED + plugin.getPrefix() + " Sorry, that field doesn't exist.");
            return false;
        }

        field.endMatch(true);
        field.setInProgress(false);
        source.sendMessage(ChatColor.GREEN + plugin.getPrefix() + " Match terminated.");
        return true;
    }
}
