package io.musician101.minetanks.sponge.battlefield;

import com.flowpowered.math.vector.Vector3d;
import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonPermissions;
import io.musician101.minetanks.common.battlefield.AbstractBattleField;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import io.musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import io.musician101.minetanks.sponge.tank.SpongeTank;
import io.musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.util.SpongeRegion;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
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
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Move;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Teleport;
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
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.OrderedInventory;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpongeBattleField extends AbstractBattleField<SpongeMineTanks, SpongePlayerTank, SpongeRegion, SpongeMTScoreboard, ItemType, Location<World>, ChangeBlockEvent.Break, ChangeBlockEvent.Place, InteractBlockEvent.Secondary, DropItemEvent.Dispense, ClientConnectionEvent.Join, ClientConnectionEvent.Disconnect, DisplaceEntityEvent.Move.TargetPlayer, DisplaceEntityEvent.Teleport.TargetPlayer, LaunchProjectileEvent, DamageEntityEvent, CollideBlockEvent, ExplosionEvent.Detonate>
{
    private final Map<UUID, UUID> arrows = new HashMap<>();

    public SpongeBattleField(SpongeMineTanks plugin, String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        super(plugin, name, enabled, region, greenSpawn, redSpawn, spectators, new SpongeMTScoreboard());
    }

    @Override
    protected boolean menuItem(ItemType itemType)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return Arrays.asList(ItemTypes.STICK, ItemTypes.WOODEN_SWORD, ItemTypes.STONE_SWORD, ItemTypes.IRON_SWORD, ItemTypes.GOLDEN_SWORD, ItemTypes.DIAMOND_SWORD, ItemTypes.CLOCK).contains(itemType);
    }

    @Override
    public boolean addPlayer(UUID uuid, MTTeam team)
    {
        if (!plugin.getInventoryStorage().save(uuid))
            return false;

        Player player = MTUtils.getPlayer(uuid);
        if (team == MTTeam.SPECTATOR)
        {
            player.setLocation(getSpectators());
            player.sendMessage(TextUtils.greenText(CommonMessages.fieldSpectating(this)));
        }
        else
        {
            player.getInventory().set(MTUtils.createCustomItem(ItemTypes.STICK, CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(null)));
            player.getInventory().set(MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            unassigned++;
        }

        players.put(uuid, new SpongePlayerTank(uuid, team));
        return true;
    }

    @Override
    public boolean removePlayer(UUID uuid)
    {
        Player player = MTUtils.getPlayer(uuid);
        SpongePlayerTank pt = getPlayerTank(uuid);
        if (pt == null)
            return false;

        player.offer(Keys.POTION_EFFECTS, Collections.emptyList());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        plugin.getInventoryStorage().load(player.getUniqueId());
        if (getScoreboard().isOnGreen(uuid) || getScoreboard().isOnRed(uuid))
            getScoreboard().playerDeath(uuid);

        players.remove(player.getUniqueId());
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File storageDir)
    {
        File file = new File(storageDir, CommonConfig.battlefieldFile(this));
        ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
        ConfigurationNode field;
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            field = cl.load();
        }
        catch (IOException e)
        {
            plugin.getLogger().warn(CommonMessages.fileLoadFailed(file));
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
        try
        {
            cl.save(field);
        }
        catch (IOException e)
        {
            plugin.getLogger().warn(CommonMessages.fileSaveFailed(file));
        }
    }

    @Override
    protected void setUpPlayers(List<UUID> playersList)
    {
        for (UUID uuid : playersList)
        {
            SpongePlayerTank pt = players.get(uuid);
            SpongeTank tank = pt.getTank();
            Optional<Player> playerOptional = Sponge.getServer().getPlayer(uuid);
            if (!playerOptional.isPresent())
                continue;

            Player player = playerOptional.get();
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(uuid))
                    player.setLocation(getGreenSpawn());
                else if (getScoreboard().isOnRed(uuid))
                    player.setLocation(getRedSpawn());

                getScoreboard().setPlayerHealth(uuid, tank.getHealth());
                player.getInventory().clear();
                tank.getWeapons().forEach(player.getInventory()::offer);
                player.setHelmet(tank.getHelmet());
                player.setChestplate(tank.getChestplate());
                player.setLeggings(tank.getLeggings());
                player.setBoots(tank.getBoots());
                pt.isReloading(plugin);
            }
            else
            {
                pt.setTank(null);
                player.getInventory().clear();
            }
        }
    }

    @Override
    public void endMatch()
    {
        endMatch(false);
    }

    @Override
    public void endMatch(boolean forced)
    {
        if (!inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (UUID uuid : players.keySet())
            {
                //noinspection OptionalGetWithoutIsPresent
                Player player = Sponge.getGame().getServer().getPlayer(uuid).get();
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
                getScoreboard().playerDeath(uuid);
                if (forced)
                    player.sendMessage(TextUtils.goldText(CommonMessages.MATCH_FORCE_ENDED));
                else if (getScoreboard().getGreenTeamSize() == 0)
                    player.sendMessage(TextUtils.redText(CommonMessages.RED_WINS));
                else if (getScoreboard().getRedTeamSize() == 0)
                    player.sendMessage(TextUtils.greenText(CommonMessages.GREEN_WINS));
            }
        }
    }

    @Override
    public void playerKilled(UUID playerId)
    {
        getPlayerTank(playerId).killed();
        getScoreboard().playerDeath(playerId);
        Player player = MTUtils.getPlayer(playerId);
        player.setLocation(getSpectators());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);
    }

    @Override
    public void onBlockBreak(Break event)
    {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        if (!getRegion().isInRegion(player.getLocation()))
            return;

        if (!player.hasPermission(CommonPermissions.EDIT_PERM) && isEnabled())
            return;

        event.setCancelled(true);
    }

    @Override
    public void onBlockPlace(Place event)
    {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        if (!getRegion().isInRegion(player.getLocation()))
            return;

        if (!player.hasPermission(CommonPermissions.EDIT_PERM) && isEnabled())
            return;

        event.setCancelled(true);
    }

    @Override
    protected boolean handleWatch(UUID uuid)
    {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Player player = Sponge.getServer().getPlayer(uuid).get();
        SpongePlayerTank pt = players.get(uuid);
        if (pt.getTank() == null)
        {
            //TODO need to make this a CommonMessage
            player.sendMessage(TextUtils.redText(CommonMessages.PREFIX + "You cannot ready until you have selected a tank."));
            return false;
        }
        else if (pt.isReady())
        {
            pt.setReady(false);
            ((OrderedInventory) player.getInventory()).set(new SlotIndex(1), MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            return true;
        }

        pt.setReady(false);
        ((OrderedInventory) player.getInventory()).set(new SlotIndex(1), MTUtils.createCustomItem(ItemTypes.CLOCK, CommonItemText.UNREADY, CommonItemText.READY));
        startMatch();
        return true;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    protected boolean processBlockInteract(UUID uuid)
    {
        Player player = Sponge.getServer().getPlayer(uuid).get();
        ItemType itemType = player.getItemInHand().get().getItem();
        SpongePlayerTank pt = players.get(uuid);
        if (itemType == ItemTypes.CLOCK)
            return handleWatch(uuid);
        else if (pt.isReady())
        {
            player.sendMessage(TextUtils.redText(CommonMessages.MUST_UNREADY));
            return false;
        }

        //TODO open menu here
        return false;
    }

    @Override
    public void onBlockInteract(Secondary event)
    {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;
        
        Player player = playerOptional.get();
        if (!players.containsKey(player.getUniqueId()))
            return;
        
        Optional<ItemStack> itemStackOptional = player.getItemInHand();
        if (!itemStackOptional.isPresent())
            return;
        
        ItemType type = itemStackOptional.get().getItem();
        if (!menuItem(type) && type != ItemTypes.CLOCK)
            return;

        if (!processBlockInteract(player.getUniqueId()))
            return;

        event.setCancelled(true);
    }

    @Override
    public void onItemDrop(Dispense event)
    {
        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        event.setCancelled(players.containsKey(playerOptional.get().getUniqueId()));
    }

    @Override
    public void onPlayerJoin(Join event)
    {
        Player player = event.getTargetEntity();
        File file = plugin.getInventoryStorage().getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        plugin.getInventoryStorage().load(player.getUniqueId());
        player.sendMessage(TextUtils.greenText(CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED));
    }

    @Override
    public void onPlayerQuit(Disconnect event)
    {
        UUID uuid = event.getTargetEntity().getUniqueId();
        if (getPlayers().containsKey(uuid))
            removePlayer(uuid);
    }

    @Override
    public void onPlayerMove(Move.TargetPlayer event)
    {
        Player player = event.getTargetEntity();
        SpongePlayerTank pt = players.get(player.getUniqueId());
        if (pt == null)
            return;

        SpongeRegion region = getRegion();
        if (region.isInRegion(player.getLocation()))
            return;

        player.sendMessage(TextUtils.redText(CommonMessages.OUT_OF_BOUNDS));
        int x = 0;
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

        player.setVelocity(new Vector3d(x, 0, z));
    }

    @Override
    public void onPlayerTeleport(Teleport.TargetPlayer event)
    {
        Cause cause = event.getCause();
        if (!cause.containsNamed(NamedCause.SOURCE))
            return;

        Object object = cause.getNamedCauses().get(NamedCause.SOURCE);
        if (!(object instanceof PluginContainer))
            return;

        PluginContainer plugin = (PluginContainer) object;
        if (plugin.getId().equals(CommonReference.ID))
            return;

        event.setCancelled(players.containsKey(event.getTargetEntity().getUniqueId()));
    }

    @Override
    protected void arrowIsDamager(UUID damager, double damage, UUID damaged)
    {
        Optional<Entity> entityOptional = getRegion().getWorld().getEntity(damager);
        if (!entityOptional.isPresent())
            return;

        Arrow arrow = (Arrow) entityOptional.get();
        if (arrow.getShooter() instanceof Player && players.containsKey(((Player) arrow.getShooter()).getUniqueId()))
        {
            playerHit(((Player) arrow.getShooter()).getUniqueId(), damaged, damage);
            arrows.put(damager,
                    Task.builder()
                        .async()
                        .delayTicks(1L)
                        .intervalTicks(Integer.MAX_VALUE)
                        .execute(() ->
                        {
                            if (getRegion().isInRegion(arrow.getLocation()))
                            {
                                Optional<Task> taskOptional = Sponge.getScheduler().getTaskById(arrows.remove(damaged));
                                if (!taskOptional.isPresent())
                                    return;

                                taskOptional.get().cancel();
                                arrow.remove();
                            }
                        })
                        .submit(Sponge.getPluginManager().getPlugin(CommonReference.ID)).getUniqueId());
        }
    }

    @Override
    protected void playerIsDamager(UUID damager, double damage, UUID damaged)
    {
        if (players.containsKey(damager))
            meleeHit(damaged, damager, damage);
    }

    @Override
    protected void blockExplosion(UUID damager, double damage, UUID damaged)
    {
        playerHit(damaged, damager, damage);
        Optional<Task> taskOptional = Sponge.getScheduler().getTaskById(arrows.get(damager));
        if (taskOptional.isPresent())
            taskOptional.get().cancel();

        arrows.remove(damager);
    }

    @Override
    public void onBowShoot(LaunchProjectileEvent event)
    {
        Projectile projectile = event.getTargetEntity();
        if (!(projectile.getShooter() instanceof Player))
            return;

        Player player = (Player) projectile.getShooter();
        if (!players.containsKey(player.getUniqueId()))
            return;

        SpongePlayerTank pt = players.get(player.getUniqueId());
        if (pt.getTeam() == MTTeam.SPECTATOR)
            return;

        SpongeTank tank = pt.getTank();
        event.setCancelled(pt.isReloading(plugin));
        if (event.isCancelled())
            return;

        if (tank.getCannon() instanceof SpongeAutoLoader)
        {
            Optional<ItemStack> itemStackOptional = player.getItemInHand();
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

        for (Inventory slot : player.getInventory())
        {
            Optional<ItemStack> iso = slot.peek();
            if (!iso.isPresent())
                continue;

            ItemStack arrows = iso.get();
            if (arrows.getItem() != ItemTypes.ARROW)
                continue;

            arrows.setQuantity(arrows.getQuantity() - 1);
            return;
        }
    }

    @Override
    public void onEntityDamage(DamageEntityEvent event)
    {
        if (!inProgress())
            return;

        if (!(event.getTargetEntity() instanceof Player))
            return;

        Cause cause = event.getCause();
        Optional<DamageSource> damageSourceOptional = cause.first(DamageSource.class);
        Optional<Entity> entityOptional = cause.first(Entity.class);
        if (!damageSourceOptional.isPresent() && !entityOptional.isPresent())
            return;

        DamageSource damageSource = damageSourceOptional.get();
        //TODO left off here because Explosion damage isn't implemented
        if (!(damageSource instanceof Arrow) && !(damageSource instanceof Player) && damageSource != DamageSources.FALLING && event.getCause() != DamageCause.FALL)
            return;

        UUID damaged = event.getTargetEntity().getUniqueId();
        if (!players.containsKey(damaged))
            return;

        SpongePlayerTank damagedPT = players.get(damaged);
        if (damagedPT.getTeam() != MTTeam.ASSIGNED)
            return;

        DamageCause cause = event.getCause();
        double damage = event.getDamage();
        if (damageSource instanceof Arrow)
            arrowIsDamager(damageSource.getUniqueId(), damage, damaged);
        else if (damageSource instanceof Player)
            playerIsDamager(damageSource.getUniqueId(), damage, damaged);
        else if (cause == DamageCause.FALL)
            gravityHit(damaged, damage);
        else if (cause == DamageCause.BLOCK_EXPLOSION)
            blockExplosion(damageSource.getUniqueId(), damage, damaged);

        event.setCancelled(true);
    }

    @Override
    public void onProjectileHit(CollideBlockEvent event)
    {

    }

    @Override
    public void onBlockExplode(Detonate event)
    {

    }

    @Override
    protected void gravityHit(UUID uuid, double damage)
    {

    }

    @Override
    protected void meleeHit(UUID rammed, UUID rammer, double damage)
    {

    }

    @Override
    protected void playerHit(UUID damager, UUID damaged, double damage)
    {

    }

    @Override
    protected void triggerPlayerDeath(UUID killerId, UUID killedId)
    {

    }
}
