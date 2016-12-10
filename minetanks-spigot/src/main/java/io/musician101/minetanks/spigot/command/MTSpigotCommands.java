package io.musician101.minetanks.spigot.command;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonCommands;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.spigot.SpigotMineTanks;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleField;
import io.musician101.minetanks.spigot.battlefield.SpigotBattleFieldStorage;
import io.musician101.minetanks.spigot.menu.MainSelectionMenu;
import io.musician101.musicianlibrary.java.minecraft.command.AbstractCommandArgument.Syntax;
import io.musician101.musicianlibrary.java.minecraft.command.MLCommandResult;
import io.musician101.musicianlibrary.java.minecraft.spigot.SpigotRegion;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MTSpigotCommands {

    private MTSpigotCommands() {

    }

    private static SpigotCommand<SpigotMineTanks> create() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.CREATE_NAME).description(CommonCommands.CREATE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.CREATE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleFieldStorage fieldStorage = getFieldStorage();
                    String arg = args.get(0);
                    if (!fieldStorage.createField(arg)) {
                        player.sendMessage(ChatColor.RED + CommonMessages.FIELD_EXISTS);
                        return MLCommandResult.FAILED;
                    }

                    player.sendMessage(new String[]{ChatColor.GREEN + CommonMessages.fieldCreated(fieldStorage.getField(arg)), ChatColor.GREEN + CommonMessages.FIELD_CREATED_STATUS});
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> enable() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.ENABLE_NAME).description(CommonCommands.ENABLE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.ENABLE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    if (field.isEnabled())
                        field.setEnabled(false);
                    else
                        field.setEnabled(true);

                    sender.sendMessage(ChatColor.GREEN + CommonMessages.fieldEnabled(field));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> forceEnd() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.FORCE_END_NAME).description(CommonCommands.FORCE_END_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FORCE_END_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    getField(args.get(0)).endMatch(true);
                    sender.sendMessage(ChatColor.GREEN + CommonMessages.MATCH_TERMINATED);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotBattleField getField(String name) {
        return getFieldStorage().getField(name);
    }

    private static Set<String> getFieldNames() {
        return getFieldStorage().getFields().keySet();
    }

    private static SpigotBattleFieldStorage getFieldStorage() {
        return SpigotMineTanks.instance().getFieldStorage();
    }

    private static SpigotCommand<SpigotMineTanks> greenSpawn() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.GREEN_SPAWN_NAME).description(CommonCommands.GREEN_SPAWN_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.GREEN_SPAWN_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    Location loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
                        return MLCommandResult.FAILED;
                    }

                    field.setGreenSpawn(loc);
                    player.sendMessage(ChatColor.GREEN + CommonMessages.GREEN_SPAWN_SET);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> join() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.JOIN_NAME).description(CommonCommands.JOIN_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.JOIN_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isEnabled()) {
                        player.sendMessage(ChatColor.RED + CommonMessages.FIELD_DISABLED);
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isReady()) {
                        player.sendMessage(ChatColor.RED + CommonMessages.FIELD_NOT_READY);
                        return MLCommandResult.FAILED;
                    }

                    if (field.inProgress()) {
                        player.sendMessage(ChatColor.RED + CommonMessages.MATCH_IN_PROGRESS);
                        return MLCommandResult.FAILED;
                    }

                    field.addPlayer(player, MTTeam.UNASSIGNED);
                    new MainSelectionMenu(field, player);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> leave() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.LEAVE_NAME).description(CommonCommands.LEAVE_DESC)
                .usage(SpigotCommandUsage.of(SpigotCommandArgument.of(CommonCommands.MT_CMD),
                        SpigotCommandArgument.of(CommonCommands.LEAVE_NAME)))
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    for (String name : getFieldNames()) {
                        SpigotBattleField field = getField(name);
                        if (field.getPlayerTank(player.getUniqueId()) != null) {
                            if (!getFieldStorage().canPlayerExit(player.getUniqueId())) {
                                player.sendMessage(ChatColor.RED + CommonMessages.NO_PERMISSION);
                                return MLCommandResult.FAILED;
                            }

                            player.sendMessage(ChatColor.GREEN + CommonMessages.LEFT_FIELD);
                            field.removePlayer(player);
                            return MLCommandResult.SUCCESS;
                        }
                    }

                    player.sendMessage(ChatColor.RED + CommonMessages.NOT_IN_A_FIELD);
                    return MLCommandResult.FAILED;
                }).build();
    }

    public static SpigotCommand<SpigotMineTanks> mt() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonReference.ID).description(CommonReference.DESCRIPTION)
                .usage(SpigotCommandUsage.of(SpigotCommandArgument.of(CommonCommands.MT_CMD)))
                .permissions(SpigotCommandPermissions.blank())
                .addCommand(join()).addCommand(leave()).addCommand(spectate()).addCommand(remove()).addCommand(create())
                .addCommand(region()).addCommand(enable()).addCommand(forceEnd()).addCommand(greenSpawn())
                .addCommand(redSpawn()).addCommand(spectators()).addCommand(status()).build();
    }

    private static SpigotCommand<SpigotMineTanks> redSpawn() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.RED_SPAWN_NAME).description(CommonCommands.RED_SPAWN_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.RED_SPAWN_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    Location loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
                        return MLCommandResult.FAILED;
                    }

                    field.setRedSpawn(loc);
                    player.sendMessage(ChatColor.GREEN + CommonMessages.RED_SPAWN_SET);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> region() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.REGION_NAME).description(CommonCommands.REMOVE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(2).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.REGION_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.RADIUS, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    if (args.size() >= 2 && args.size() < 4) {
                        int radius;
                        try {
                            radius = Integer.valueOf(args.get(1));
                        }
                        catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + CommonMessages.notANumber(args.get(1)));
                            return MLCommandResult.FAILED;
                        }

                        field.setRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), radius));
                        player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
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
                            player.sendMessage(ChatColor.RED + CommonMessages.NOT_A_NUMBER_MULTI);
                            return MLCommandResult.FAILED;
                        }

                        field.setRegion(SpigotRegion.createFromLocationRadius(player.getLocation(), xRadius, yRadius, zRadius));
                        player.sendMessage(ChatColor.GREEN + CommonMessages.REGION_SET);
                        return MLCommandResult.SUCCESS;
                    }

                    player.sendMessage(ChatColor.RED + CommonMessages.NOT_ENOUGH_ARGS);
                    return MLCommandResult.FAILED;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> remove() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.REMOVE_NAME).description(CommonCommands.REMOVE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.REMOVE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    SpigotBattleField field = getField(args.get(0));
                    if (!getFieldStorage().removeField(field.getName())) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    sender.sendMessage(ChatColor.GREEN + CommonMessages.fieldDeleted(field));
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> spectate() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.SPECTATE_NAME).description(CommonCommands.SPECTATE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.PARTICIPATE_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        player.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    if (!field.isReady()) {
                        player.sendMessage(ChatColor.RED + CommonMessages.FIELD_NOT_READY);
                        return MLCommandResult.FAILED;
                    }

                    field.addPlayer(player, MTTeam.SPECTATOR);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> spectators() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.SPECTATE_NAME).description(CommonCommands.SPECTATE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(true).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    Player player = (Player) sender;
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    Location loc = player.getLocation();
                    if (field.getRegion() == null || !field.getRegion().isInRegion(loc)) {
                        player.sendMessage(ChatColor.RED + CommonMessages.LOCATION_NOT_IN_REGION);
                        return MLCommandResult.FAILED;
                    }

                    field.setSpectators(loc);
                    player.sendMessage(ChatColor.GREEN + CommonMessages.SPECTATORS_SPAWN_SET);
                    return MLCommandResult.SUCCESS;
                }).build();
    }

    private static SpigotCommand<SpigotMineTanks> status() {
        return SpigotCommand.<SpigotMineTanks>builder().name(CommonCommands.SPECTATE_NAME).description(CommonCommands.SPECTATE_DESC)
                .usage(SpigotCommandUsage.builder().minArgs(1).addArgument(SpigotCommandArgument.of(CommonCommands.MT_CMD))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.SPECTATE_NAME))
                        .addArgument(SpigotCommandArgument.of(CommonCommands.FIELD, Syntax.REPLACE, Syntax.REQUIRED)).build())
                .permissions(SpigotCommandPermissions.builder().permissionNode(CommonPermissions.EDIT_PERM)
                        .isPlayerOnly(false).noPermissionMessage(ChatColor.RED + CommonMessages.NO_PERMISSION)
                        .playerOnlyMessage(ChatColor.RED + CommonMessages.PLAYER_ONLY).build())
                .function((sender, args) -> {
                    SpigotBattleField field = getField(args.get(0));
                    if (field == null) {
                        sender.sendMessage(ChatColor.RED + CommonMessages.FIELD_DNE);
                        return MLCommandResult.FAILED;
                    }

                    sender.sendMessage(new String[]{ChatColor.GREEN + CommonMessages.statusOfField(field),
                            ChatColor.GREEN + CommonMessages.statusOfFieldEnabled(field),
                            ChatColor.GREEN + CommonMessages.statusOfFieldRegion(field),
                            ChatColor.GREEN + CommonMessages.statusOfFieldGreenSpawn(field),
                            ChatColor.GREEN + CommonMessages.statusOfFieldRedSpawn(field),
                            ChatColor.GREEN + CommonMessages.statusOfFieldSpectatorsSpawn(field)});

                    return MLCommandResult.SUCCESS;
                }).build();
    }
}
