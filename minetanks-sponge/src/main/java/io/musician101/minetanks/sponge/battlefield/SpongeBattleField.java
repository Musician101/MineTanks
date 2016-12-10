package io.musician101.minetanks.sponge.battlefield;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.AbstractBattleField;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import io.musician101.minetanks.sponge.menu.SpongeMainSelectionMenu;
import io.musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.util.SpongeInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.sponge.SpongeRegion;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.block.ChangeBlockEvent.Place;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent.Dispense;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect;
import org.spongepowered.api.event.network.ClientConnectionEvent.Join;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.event.world.ExplosionEvent.Detonate;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;


public class SpongeBattleField extends AbstractBattleField<Player, SpongePlayerTank, SpongeRegion, SpongeMTScoreboard, ItemType, Location<World>, ChangeBlockEvent.Break, ChangeBlockEvent.Place, InteractBlockEvent.Secondary, DropItemEvent.Dispense, ClientConnectionEvent.Join, ClientConnectionEvent.Disconnect, MoveEntityEvent, MoveEntityEvent.Teleport, LaunchProjectileEvent, DamageEntityEvent, CollideBlockEvent.Impact, ExplosionEvent.Detonate> {

    private final Map<UUID, UUID> arrows = new HashMap<>();

    public SpongeBattleField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators) {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new SpongeMTScoreboard());
    }

    @Override
    public boolean addPlayer(Player player, MTTeam team) {
        if (!SpongeMineTanks.instance().getInventoryStorage().save(player))
            return false;

        if (team == MTTeam.SPECTATOR) {
            player.setLocation(getSpectators());
            player.sendMessage(TextUtils.greenText(CommonMessages.fieldSpectating(this)));
        }
        else {
            player.getInventory().offer(MTUtils.createCustomItem(ItemTypes.STICK, CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(null)));
            player.getInventory().offer(MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            unassigned++;
        }

        players.put(player.getUniqueId(), new SpongePlayerTank(player.getUniqueId(), team));
        return true;
    }

    @Override
    protected void arrowIsDamager(UUID damager, double damage, Player damaged) {
        getRegion().getWorld().getEntity(damager).ifPresent(entity ->
        {
            Arrow arrow = (Arrow) entity;
            if (arrow.getShooter() instanceof Player && players.containsKey(((Player) arrow.getShooter()).getUniqueId())) {
                Player shooter = (Player) arrow.getShooter();
                playerHit(shooter, damaged, damage);
                arrows.put(damager,
                        Task.builder()
                                .name("SpongeMineTanks-ArrowIsDamager-" + shooter.getName())
                                .delayTicks(1L)
                                .intervalTicks(Integer.MAX_VALUE)
                                .execute(() ->
                                {
                                    if (getRegion().isInRegion(arrow.getLocation()))
                                        Sponge.getScheduler().getTaskById(arrows.remove(damager))
                                                .ifPresent(task ->
                                                {
                                                    task.cancel();
                                                    arrow.remove();
                                                });
                                })
                                .submit(SpongeMineTanks.instance()).getUniqueId());
            }
        });
    }

    @Override
    protected boolean assignTeams(List<UUID> playersList) {
        Collections.shuffle(playersList);
        for (UUID uuid : playersList) {
            SpongePlayerTank pt = players.get(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return false;

            Sponge.getServer().getPlayer(uuid).ifPresent(player ->
            {
                if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1) {
                    pt.setTeam(MTTeam.SPECTATOR);
                    pt.setTeam(null);
                }
                else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize()) {
                    getScoreboard().addGreenPlayer(player);
                    pt.setTeam(MTTeam.ASSIGNED);
                    pt.getTank().applySpeedEffect(player);
                }
                else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize()) {
                    pt.setTeam(MTTeam.ASSIGNED);
                    getScoreboard().addRedPlayer(player);
                    pt.getTank().applySpeedEffect(player);
                }

                unassigned--;
            });
        }

        return true;
    }

    @Override
    protected void blockExplosion(UUID damager, double damage, Player damaged) {
        playerHit(damaged, (Player) ((Arrow) damaged.getWorld().getEntity(damager).get()).getShooter(), damage);
        Sponge.getScheduler().getTaskById(arrows.get(damager)).ifPresent(Task::cancel);
        arrows.remove(damager);
    }

    @Override
    public void endMatch() {
        endMatch(false);
    }

    @Override
    public void endMatch(boolean forced) {
        if (!inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0) {
            setInProgress(false);
            for (UUID uuid : players.keySet()) {
                Sponge.getGame().getServer().getPlayer(uuid).ifPresent(player ->
                {
                    player.setLocation(getSpectators());
                    player.setHelmet(null);
                    player.setChestplate(null);
                    player.setLeggings(null);
                    player.setBoots(null);
                    player.getInventory().clear();
                    player.offer(Keys.POTION_EFFECTS, Collections.emptyList());

                    SpongePlayerTank pt = getPlayerTank(uuid);
                    pt.setTeam(MTTeam.SPECTATOR);
                    pt.setTank(null);
                    getScoreboard().playerDeath(player);
                    if (forced)
                        player.sendMessage(TextUtils.goldText(CommonMessages.MATCH_FORCE_ENDED));
                    else if (getScoreboard().getGreenTeamSize() == 0)
                        player.sendMessage(TextUtils.redText(CommonMessages.RED_WINS));
                    else if (getScoreboard().getRedTeamSize() == 0)
                        player.sendMessage(TextUtils.greenText(CommonMessages.GREEN_WINS));
                });
            }
        }
    }

    @Override
    protected void gravityHit(Player player, double damage) {
        double dmg = damage * 5;
        SpongeMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(player, (int) (sb.getPlayerHealth(player) - dmg));
        //TODO need to create a new message and custom code for accidental/purposeful suicide
        getPlayers().keySet().stream()
                .map(Sponge.getServer()::getPlayer)
                .filter(Optional::isPresent)
                .forEach(p -> p.get().sendMessage(Text.of("need a message here")));
    }

    @Override
    protected boolean handleWatch(Player player) {
        SpongePlayerTank pt = players.get(player.getUniqueId());
        if (pt.getTank() == null) {
            //TODO need to make this a CommonMessage
            player.sendMessage(TextUtils.redText(CommonMessages.PREFIX + "You cannot ready until you have selected a tank."));
            return false;
        }
        else if (pt.isReady()) {
            pt.setReady(false);
            ((OrderedInventory) player.getInventory()).set(new SlotIndex(1), MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            return true;
        }

        pt.setReady(false);
        ((OrderedInventory) player.getInventory()).set(new SlotIndex(1), MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.UNREADY, CommonItemText.READY));
        startMatch();
        return true;
    }

    @Override
    protected boolean isMenuItem(ItemType itemType) {
        //Stick is the default item if a player hasn't chosen a tank.
        return Arrays.asList(ItemTypes.STICK, ItemTypes.WOODEN_SWORD, ItemTypes.STONE_SWORD, ItemTypes.IRON_SWORD, ItemTypes.GOLDEN_SWORD, ItemTypes.DIAMOND_SWORD, ItemTypes.CLOCK).contains(itemType);
    }

    @Override
    protected void meleeHit(Player rammed, Player rammer, double damage) {
        double rammerDmg = damage * players.get(rammer.getUniqueId()).getTank().getType().getRamModifier();
        double rammedDmg = damage * players.get(rammed.getUniqueId()).getTank().getType().getRamModifier();
        if (rammerDmg > 0)
            playerHit(rammed, rammer, rammerDmg);

        if (rammedDmg > 0)
            playerHit(rammer, rammed, rammedDmg);
    }

    @Override
    public void onBlockBreak(Break event) {
        event.getCause().first(Player.class)
                .filter(player -> getRegion().isInRegion(player.getLocation()) && player.hasPermission(CommonPermissions.EDIT_PERM) && isEnabled())
                .ifPresent(player -> event.setCancelled(true));
    }

    @Override
    public void onBlockExplode(Detonate event) {
        if (!event.isCancelled() && getRegion().isInRegion(event.getExplosion().getLocation()))
            event.getAffectedLocations().clear();
    }

    @Override
    public void onBlockInteract(Secondary event) {
        event.getCause().first(Player.class)
                .filter(player -> players.containsKey(player.getUniqueId()))
                .ifPresent(player ->
                        player.getItemInHand(HandTypes.MAIN_HAND)
                                .filter(itemStack -> isMenuItem(itemStack.getItem()) && itemStack.getItem() == ItemTypes.CLOCK && processBlockInteract(player))
                                .ifPresent(itemStack -> event.setCancelled(true)));
    }

    @Override
    public void onBlockPlace(Place event) {
        event.getCause().first(Player.class)
                .filter(player -> getRegion().isInRegion(player.getLocation()) && player.hasPermission(CommonPermissions.EDIT_PERM) && isEnabled())
                .ifPresent(player -> event.setCancelled(true));
    }

    @Override
    public void onBowShoot(LaunchProjectileEvent event) {
        Projectile projectile = event.getTargetEntity();
        if (projectile.getShooter() instanceof Player) {
            Player player = (Player) projectile.getShooter();
            if (players.containsKey(player.getUniqueId())) {
                SpongePlayerTank pt = players.get(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR) {
                    SpongeTank tank = pt.getTank();
                    event.setCancelled(pt.isReloading());
                    if (event.isCancelled()) {
                        if (tank.getCannon() instanceof SpongeAutoLoader) {
                            Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
                            if (!itemStackOptional.isPresent())
                                return;

                            SpongeAutoLoader cannon = (SpongeAutoLoader) tank.getCannon();
                            if (pt.getClipSize() < 1)
                                pt.setClipSize(cannon.getClipSize());
                            else
                                pt.setClipSize(pt.getClipSize() - 1);

                            ItemStack itemStack = itemStackOptional.get();
                            itemStack.offer(Keys.ITEM_LORE, Arrays.asList(Text.of(CommonItemText.CANNON),
                                    Text.of(CommonItemText.clipSize(pt.getClipSize(), cannon.getClipSize())),
                                    Text.of(CommonItemText.cycleTime(cannon.getCycleTime())),
                                    Text.of(CommonItemText.clipReloadTime(cannon.getReloadTime()))));
                        }

                        player.getInventory().query(ItemTypes.ARROW).first().peek().ifPresent(arrow ->
                        {
                            if (arrow.getItem() == ItemTypes.ARROW)
                                arrow.setQuantity(arrow.getQuantity() - 1);
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onEntityDamage(DamageEntityEvent event) {
        if (inProgress() && event.getTargetEntity() instanceof Player) {
            Player damaged = (Player) event.getTargetEntity();
            if (players.containsKey(damaged.getUniqueId())) {
                SpongePlayerTank damagedPT = players.get(damaged.getUniqueId());
                if (damagedPT.getTeam() == MTTeam.ASSIGNED) {
                    event.getCause().get(NamedCause.SOURCE, EntityDamageSource.class).ifPresent(entityDamageSource ->
                    {
                        DamageType damageType = entityDamageSource.getType();
                        if (Arrays.asList(DamageTypes.ATTACK, DamageTypes.EXPLOSIVE, DamageTypes.FALL).contains(damageType)) {
                            double damage = event.getBaseDamage();
                            if (entityDamageSource.getSource() instanceof Arrow) {
                                arrowIsDamager(entityDamageSource.getSource().getUniqueId(), damage, damaged);
                                event.setCancelled(true);
                            }
                            else if (entityDamageSource.getSource() instanceof Player) {
                                playerIsDamager((Player) entityDamageSource.getSource(), damage, damaged);
                                event.setCancelled(true);
                            }
                            else if (damageType == DamageTypes.FALL) {
                                gravityHit(damaged, damage);
                                event.setCancelled(true);
                            }
                            else if (damageType == DamageTypes.EXPLOSIVE) {
                                blockExplosion(entityDamageSource.getSource().getUniqueId(), damage, damaged);
                                event.setCancelled(true);
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onItemDrop(Dispense event) {
        event.getCause().first(Player.class).ifPresent(player ->
                event.setCancelled(players.containsKey(player.getUniqueId())));
    }

    @Override
    public void onPlayerJoin(Join event) {
        Player player = event.getTargetEntity();
        SpongeInventoryStorage inventoryStorage = SpongeMineTanks.instance().getInventoryStorage();
        File file = inventoryStorage.getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        inventoryStorage.load(player);
        player.sendMessage(TextUtils.greenText(CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED));
    }

    @Override
    public void onPlayerMove(MoveEntityEvent event) {
        if (event.getTargetEntity().getType() != EntityTypes.PLAYER)
            return;

        Player player = (Player) event.getTargetEntity();
        SpongePlayerTank pt = players.get(player.getUniqueId());
        if (pt == null)
            return;

        SpongeRegion region = getRegion();
        if (region.isInRegion(player.getLocation()))
            return;

        player.sendMessage(TextUtils.redText(CommonMessages.OUT_OF_BOUNDS));
        //TODO test to see if this works
        /*int x = 0;
        int z = 0;
        Location location = player.getLocation();
        if (location.getX() > region.getMaxX())
            x = -1;
        if (location.getX() < region.getMinX())
            x = 1;
        if (location.getZ() > region.getMaxZ())
            z = -1;
        if (location.getZ() < region.getMinZ())
            z = 1;

        player.setVelocity(new Vector3d(x, 0, z));*/
        player.setLocation(event.getFromTransform().getLocation());
    }

    @Override
    public void onPlayerQuit(Disconnect event) {
        Player player = event.getTargetEntity();
        if (getPlayers().containsKey(player.getUniqueId()))
            removePlayer(player);
    }

    @Override
    public void onPlayerTeleport(MoveEntityEvent.Teleport event) {
        //TODO test this to see if this works
        event.getCause().first(Player.class).ifPresent(player ->
                event.setCancelled(players.containsKey(event.getTargetEntity().getUniqueId())));
    }

    @Override
    public void onProjectileHit(CollideBlockEvent.Impact event) {
        event.getCause().first(Arrow.class)
                .filter(arrow -> arrow.getShooter() instanceof Player && arrows.containsKey(arrow.getUniqueId()))
                .ifPresent(arrow ->
                {
                    Player player = (Player) arrow.getShooter();
                    if (!players.containsKey(player.getUniqueId()))
                        return;

                    arrow.getLocation().getExtent().triggerExplosion(
                            Explosion.builder().location(arrow.getLocation()).shouldBreakBlocks(false).radius(1F).build(),
                            Cause.of(NamedCause.of("SpongeBattleField", this)));
                    Sponge.getScheduler().getTaskById(arrows.get(arrow.getUniqueId())).ifPresent(Task::cancel);
                    arrow.remove();
                });
    }

    @Override
    protected void playerHit(Player damager, Player damaged, double damage) {
        SpongeMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(damaged, (int) (sb.getPlayerHealth(damaged) - (damage * 2) * 20));
        if (sb.getPlayerHealth(damaged) <= 0)
            triggerPlayerDeath(damager, damaged);
    }

    @Override
    protected void playerIsDamager(Player damager, double damage, Player damaged) {
        if (players.containsKey(damager.getUniqueId()))
            meleeHit(damaged, damager, damage);
    }

    @Override
    public void playerKilled(Player player) {
        getPlayerTank(player.getUniqueId()).killed();
        getScoreboard().playerDeath(player);
        player.setLocation(getSpectators());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);
    }

    @Override
    protected boolean processBlockInteract(Player player) {
        return player.getItemInHand(HandTypes.MAIN_HAND).map(itemStack ->
        {
            SpongePlayerTank pt = players.get(player.getUniqueId());
            if (itemStack.getItem() == ItemTypes.CLOCK)
                return handleWatch(player);
            else if (pt.isReady()) {
                player.sendMessage(TextUtils.redText(CommonMessages.MUST_UNREADY));
                return false;
            }

            new SpongeMainSelectionMenu(this, player);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean removePlayer(Player player) {
        SpongePlayerTank pt = getPlayerTank(player.getUniqueId());
        if (pt != null) {
            player.offer(Keys.POTION_EFFECTS, Collections.emptyList());
            player.getInventory().clear();
            player.setHelmet(null);
            player.setChestplate(null);
            player.setLeggings(null);
            player.setBoots(null);

            SpongeMineTanks.instance().getInventoryStorage().load(player);
            if (getScoreboard().isOnGreen(player) || getScoreboard().isOnRed(player))
                getScoreboard().playerDeath(player);

            players.remove(player.getUniqueId());
            if (unassigned > 0)
                unassigned--;

            return true;
        }

        return false;
    }

    @Override
    public void saveToFile(File storageDir) {
        File file = new File(storageDir, CommonConfig.battlefieldFile(this));
        ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
        ConfigurationNode field;
        Logger logger = SpongeMineTanks.instance().getLogger();
        try {
            file.createNewFile();
            field = cl.load();
        }
        catch (IOException e) {
            logger.warn(CommonMessages.fileLoadFailed(file));
            return;
        }

        if (getRegion() != null)
            field.getNode(CommonConfig.REGION).setValue(getRegion().serialize());

        if (getGreenSpawn() != null)
            field.getNode(CommonConfig.GREEN_SPAWN).setValue(getGreenSpawn().toContainer().toString());

        if (getRedSpawn() != null)
            field.getNode(CommonConfig.RED_SPAWN).setValue(getRedSpawn().toContainer().toString());

        if (getSpectators() != null)
            field.getNode(CommonConfig.SPECTATORS).setValue(getSpectators().toContainer().toString());

        field.getNode(CommonConfig.ENABLED).setValue(isEnabled());
        try {
            cl.save(field);
        }
        catch (IOException e) {
            logger.warn(CommonMessages.fileSaveFailed(file));
        }
    }

    @Override
    protected void setPlayerScoreboards() {
        players.keySet().stream()
                .map(Sponge.getServer()::getPlayer)
                .filter(Optional::isPresent)
                .forEach(player -> getScoreboard().setPlayerScoreboard(player.get()));
    }

    @Override
    protected void setUpPlayers(List<UUID> playersList) {
        for (UUID uuid : playersList) {
            SpongePlayerTank pt = players.get(uuid);
            SpongeTank tank = pt.getTank();
            Sponge.getServer().getPlayer(uuid).ifPresent(player ->
            {
                if (pt.getTeam() != MTTeam.SPECTATOR) {
                    if (getScoreboard().isOnGreen(player))
                        player.setLocation(getGreenSpawn());
                    else if (getScoreboard().isOnRed(player))
                        player.setLocation(getRedSpawn());

                    getScoreboard().setPlayerHealth(player, tank.getHealth());
                    player.getInventory().clear();
                    tank.getWeapons().forEach(player.getInventory()::offer);
                    player.setHelmet(tank.getHelmet());
                    player.setChestplate(tank.getChestplate());
                    player.setLeggings(tank.getLeggings());
                    player.setBoots(tank.getBoots());
                    pt.isReloading();
                }
                else {
                    pt.setTank(null);
                    player.getInventory().clear();
                }
            });

        }
    }

    @Override
    protected void triggerPlayerDeath(Player killer, Player killed) {
        SpongeMTScoreboard sb = getScoreboard();
        Text killedMsg = sb.isOnGreen(killed) ? TextUtils.greenText(killed.getName()) : TextUtils.redText(killed.getName());
        Text killerMsg = sb.isOnGreen(killer) ? TextUtils.greenText(killer.getName()) : TextUtils.redText(killer.getName());
        getPlayers().keySet().stream()
                .map(Sponge.getServer()::getPlayer)
                .filter(Optional::isPresent)
                .forEach(player ->
                        player.get().sendMessage(Text.join(TextUtils.goldText(CommonMessages.PREFIX), killedMsg, TextUtils.goldText(" was killed by "), killerMsg, TextUtils.goldText("."))));
        playerKilled(killed);
    }
}
