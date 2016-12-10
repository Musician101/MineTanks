package io.musician101.minetanks.sponge.command;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleField;
import io.musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import io.musician101.minetanks.sponge.menu.SpongeMainSelectionMenu;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.command.MLCommandResult;
import io.musician101.musicianlibrary.java.minecraft.sponge.SpongeRegion;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommand;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.sponge.command.SpongeCommandUsage;
import java.util.Set;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class MTSpongeCommands {

    private MTSpongeCommands() {

    }

    private static SpongeCommand<PluginContainer> create() {
        return SpongeCommand.builder().name(CommonCommands.CREATE_NAME).description(TextUtils.aquaText(CommonCommands.CREATE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.CREATE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleFieldStorage fieldStorage = getFieldStorage();
                    String arg = args.get(0);
                    if (!fieldStorage.createField(arg)) {
                        player.sendMessage(TextUtils.redText(CommonMessages.FIELD_EXISTS));
                        return MLCommandResult.FAILED;
                    }

                    player.sendMessages(TextUtils.greenText(CommonMessages.fieldCreated(fieldStorage.getField(arg))), TextUtils.greenText(CommonMessages.FIELD_CREATED_STATUS));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> enable() {
        return SpongeCommand.builder().name(CommonCommands.ENABLE_NAME).description(TextUtils.aquaText(CommonCommands.ENABLE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.ENABLE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    if (field.isEnabled())
                        field.setEnabled(false);
                    else
                        field.setEnabled(true);

                    sender.sendMessage(TextUtils.greenText(CommonMessages.fieldEnabled(field)));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> forceEnd() {
        return SpongeCommand.builder().name(CommonCommands.FORCE_END_NAME).description(TextUtils.aquaText(CommonCommands.FORCE_END_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FORCE_END_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    getField(args.get(0)).endMatch(true);
                    sender.sendMessage(TextUtils.greenText(CommonMessages.MATCH_TERMINATED));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeBattleField getField(String name) {
        return getFieldStorage().getField(name);
    }

    private static Set<String> getFieldNames() {
        return getFieldStorage().getFields().keySet();
    }

    private static SpongeBattleFieldStorage getFieldStorage() {
        return SpongeMineTanks.instance().getFieldStorage();
    }

    private static SpongeCommand<PluginContainer> greenSpawn() {
        return SpongeCommand.builder().name(CommonCommands.GREEN_SPAWN_NAME).description(TextUtils.aquaText(CommonCommands.GREEN_SPAWN_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.GREEN_SPAWN_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    Location<World> loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(TextUtils.redText(CommonMessages.LOCATION_NOT_IN_REGION));
                        return MLCommandResult.FAILED;
                    }

                    field.setGreenSpawn(loc);
                    player.sendMessage(TextUtils.greenText(CommonMessages.GREEN_SPAWN_SET));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> join() {
        return SpongeCommand.builder().name(CommonCommands.JOIN_NAME).description(TextUtils.aquaText(CommonCommands.JOIN_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.JOIN_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isEnabled()) {
                        player.sendMessage(TextUtils.redText(CommonMessages.FIELD_DISABLED));
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isReady()) {
                        player.sendMessage(TextUtils.redText(CommonMessages.FIELD_NOT_READY));
                        return MLCommandResult.FAILED;
                    }

                    if (field.inProgress()) {
                        player.sendMessage(TextUtils.redText(CommonMessages.MATCH_IN_PROGRESS));
                        return MLCommandResult.FAILED;
                    }

                    field.addPlayer(player, MTTeam.UNASSIGNED);
                    new SpongeMainSelectionMenu(field, player);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> leave() {
        return SpongeCommand.builder().name(CommonCommands.LEAVE_NAME).description(TextUtils.aquaText(CommonCommands.LEAVE_DESC))
                .usage(SpongeCommandUsage.of(SpongeCommandArgument.of(CommonCommands.MT_CMD),
                        SpongeCommandArgument.of(CommonCommands.LEAVE_NAME)))
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    for (String name : getFieldNames()) {
                        SpongeBattleField field = getField(name);
                        if (field.getPlayerTank(player.getUniqueId()) != null) {
                            if (!getFieldStorage().canPlayerExit(player.getUniqueId())) {
                                player.sendMessage(TextUtils.redText(CommonMessages.NO_PERMISSION));
                                return MLCommandResult.FAILED;
                            }

                            player.sendMessage(TextUtils.greenText(CommonMessages.LEFT_FIELD));
                            field.removePlayer(player);
                            return MLCommandResult.SUCCESS;
                        }
                    }

                    player.sendMessage(TextUtils.redText(CommonMessages.NOT_IN_A_FIELD));
                    return MLCommandResult.FAILED;
                }).build();
    }

    public static SpongeCommand<PluginContainer> mt() {
        return SpongeCommand.builder().name(CommonReference.ID).description(TextUtils.aquaText(CommonReference.DESCRIPTION))
                .usage(SpongeCommandUsage.of(SpongeCommandArgument.of(CommonCommands.MT_CMD)))
                .permissions(SpongeCommandPermissions.blank())
                .addCommand(join()).addCommand(leave()).addCommand(spectate()).addCommand(remove()).addCommand(create())
                .addCommand(region()).addCommand(enable()).addCommand(forceEnd()).addCommand(greenSpawn())
                .addCommand(redSpawn()).addCommand(spectators()).addCommand(status()).build();
    }

    private static SpongeCommand<PluginContainer> redSpawn() {
        return SpongeCommand.builder().name(CommonCommands.RED_SPAWN_NAME).description(TextUtils.aquaText(CommonCommands.RED_SPAWN_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.RED_SPAWN_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    Location<World> loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(TextUtils.redText(CommonMessages.LOCATION_NOT_IN_REGION));
                        return MLCommandResult.FAILED;
                    }

                    field.setRedSpawn(loc);
                    player.sendMessage(TextUtils.greenText(CommonMessages.RED_SPAWN_SET));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> region() {
        return SpongeCommand.builder().name(CommonCommands.REGION_NAME).description(TextUtils.aquaText(CommonCommands.REMOVE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(2).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.REGION_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    if (args.size() >= 2 && args.size() < 4) {
                        int radius;
                        try {
                            radius = Integer.valueOf(args.get(1));
                        }
                        catch (NumberFormatException e) {
                            player.sendMessage(TextUtils.redText(CommonMessages.notANumber(args.get(1))));
                            return MLCommandResult.FAILED;
                        }

                        field.setRegion(SpongeRegion.createFromLocationRadius(player.getLocation(), radius));
                        player.sendMessage(TextUtils.greenText(CommonMessages.REGION_SET));
                        return MLCommandResult.SUCCESS;
                    }
                    else if (args.size() >= 4) {
                        int xRadius;
                        int yRadius;
                        int zRadius;
                        try {
                            xRadius = Integer.valueOf(args.get(1));
                            yRadius = Integer.valueOf(args.get(2));
                            zRadius = Integer.valueOf(args.get(3));
                        }
                        catch (NumberFormatException e) {
                            player.sendMessage(TextUtils.redText(CommonMessages.NOT_A_NUMBER_MULTI));
                            return MLCommandResult.FAILED;
                        }

                        field.setRegion(SpongeRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
                        player.sendMessage(TextUtils.greenText(CommonMessages.REGION_SET));
                        return MLCommandResult.SUCCESS;
                    }

                    player.sendMessage(TextUtils.redText(CommonMessages.NOT_ENOUGH_ARGS));
                    return MLCommandResult.FAILED;
                }).build();
    }

    private static SpongeCommand<PluginContainer> remove() {
        return SpongeCommand.builder().name(CommonCommands.REMOVE_NAME).description(TextUtils.aquaText(CommonCommands.REMOVE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.REMOVE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    SpongeBattleField field = getField(args.get(0));
                    if (!getFieldStorage().removeField(field.getName())) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    sender.sendMessage(TextUtils.greenText(CommonMessages.fieldDeleted(field)));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> spectate() {
        return SpongeCommand.builder().name(CommonCommands.SPECTATE_NAME).description(TextUtils.aquaText(CommonCommands.SPECTATE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        player.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isReady()) {
                        player.sendMessage(TextUtils.redText(CommonMessages.FIELD_NOT_READY));
                        return MLCommandResult.FAILED;
                    }

                    field.addPlayer(player, MTTeam.SPECTATOR);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> spectators() {
        return SpongeCommand.builder().name(CommonCommands.SPECTATE_NAME).description(TextUtils.aquaText(CommonCommands.SPECTATE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    Location<World> loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(TextUtils.redText(CommonMessages.LOCATION_NOT_IN_REGION));
                        return MLCommandResult.FAILED;
                    }

                    field.setSpectators(loc);
                    player.sendMessage(TextUtils.greenText(CommonMessages.SPECTATORS_SPAWN_SET));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpongeCommand<PluginContainer> status() {
        return SpongeCommand.builder().name(CommonCommands.SPECTATE_NAME).description(TextUtils.aquaText(CommonCommands.SPECTATE_DESC))
                .usage(SpongeCommandUsage.builder().minArgs(1).addArgument(SpongeCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpongeCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpongeCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(TextUtils.redText(CommonMessages.NO_PERMISSION))
                        .playerOnlyMessage(TextUtils.redText(CommonMessages.PLAYER_ONLY)).build())
                .function((sender, args) -> {
                    SpongeBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(TextUtils.redText(CommonMessages.FIELD_DNE));
                        return MLCommandResult.FAILED;
                    }

                    sender.sendMessages(TextUtils.greenText(CommonMessages.statusOfField(field)),
                            TextUtils.greenText(CommonMessages.statusOfFieldEnabled(field)),
                            TextUtils.greenText(CommonMessages.statusOfFieldRegion(field)),
                            TextUtils.greenText(CommonMessages.statusOfFieldGreenSpawn(field)),
                            TextUtils.greenText(CommonMessages.statusOfFieldRedSpawn(field)),
                            TextUtils.greenText(CommonMessages.statusOfFieldSpectatorsSpawn(field)));

                    return MLCommandResult.SUCCESS;
                }).build();
    }
}
