package musician101.minetanks.sponge.listener;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonPermissions;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.SpongeBattleField;
import musician101.minetanks.sponge.battlefield.SpongeBattleFieldStorage;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.event.AttemptMenuOpenEvent;
import musician101.minetanks.sponge.event.PlayerTankDamageEvent;
import musician101.minetanks.sponge.event.PlayerTankDamageEvent.PlayerTankDamageCause;
import musician101.minetanks.sponge.handler.ExplosionTracker;
import musician101.minetanks.sponge.handler.ReloadHandler;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.util.SpongeInventoryStorage;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.block.ChangeBlockEvent.Place;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.teleport.TeleportCause;
import org.spongepowered.api.event.cause.entity.teleport.TeleportTypes;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Move;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Teleport;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent.Dispense;
import org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect;
import org.spongepowered.api.event.network.ClientConnectionEvent.Join;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.explosion.Explosion;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MTListener
{
    private boolean isInField(UUID playerId)
    {
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
            if (SpongeMineTanks.getFieldStorage().getField(name).getPlayerTank(playerId) != null)
                return true;

        return false;
    }

    private boolean isSword(ItemType type)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return type == ItemTypes.STICK || type == ItemTypes.WOODEN_SWORD || type == ItemTypes.STONE_SWORD || type == ItemTypes.IRON_SWORD || type == ItemTypes.GOLDEN_SWORD || type == ItemTypes.DIAMOND_SWORD;
    }

    @Listener
    public void onBlockBreak(Break event)
    {
        onBlockChange(event);
    }

    @Listener
    public void onBlockPlace(Place event)
    {
        onBlockChange(event);
    }

    private void onBlockChange(ChangeBlockEvent event)
    {
        if (event.isCancelled())
            return;

        SpongeBattleFieldStorage fieldStorage = SpongeMineTanks.getFieldStorage();
        for (String name : fieldStorage.getFields().keySet())
        {
            SpongeBattleField field = fieldStorage.getField(name);
            for (Transaction<BlockSnapshot> transaction : event.getTransactions())
            {
                if (field.getRegion().isInRegion(transaction.getOriginal().getLocation().get()))
                {
                    Optional<Player> playerOptional = event.getCause().first(Player.class);
                    if (playerOptional.isPresent())
                    {
                        if (playerOptional.get().hasPermission(CommonPermissions.EDIT_PERM) && !field.isEnabled())
                            return;

                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @Listener
    public void onBlockInteract(Secondary event)
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        ItemType type = (player.getItemInHand().isPresent() ? player.getItemInHand().get().getItem() : null);
        if (type == null || type == ItemTypes.BOW)
            return;

        if (!isSword(type) && type != ItemTypes.CLOCK)
            return;

        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
            if (field.inProgress())
                return;

            if (field.getPlayerTank(player.getUniqueId()) != null)
                Sponge.getGame().getEventManager().post(new AttemptMenuOpenEvent(type, field.getName(), field.getPlayerTank(player.getUniqueId()), player.getUniqueId()));
        }
    }

    @Listener
    public void onItemDrop(Dispense event)
    {
        if (!event.getCause().containsType(Player.class))
            return;

        event.setCancelled(isInField(event.getCause().first(Player.class).get().getUniqueId()));
    }

    @Listener
    public void onPlayerJoin(Join event)
    {
        Player player = event.getTargetEntity();
        SpongeInventoryStorage sis = SpongeMineTanks.getInventoryStorage();
        File file = sis.getPlayerFile(player.getUniqueId());
        if (!file.exists())
            return;

        player.sendMessage(TextUtils.greenText(CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED));
        sis.load(player.getUniqueId());
    }

    @Listener
    public void onPlayerDisconnect(Disconnect event)
    {
        Player player = event.getTargetEntity();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            if (SpongeMineTanks.getFieldStorage().getField(name).getPlayerTank(player.getUniqueId()) != null)
            {
                SpongeMineTanks.getFieldStorage().getField(name).removePlayer(player.getUniqueId());
                return;
            }
        }
    }

    @Listener
    public void onPlayerMove(Move.TargetPlayer event)
    {
        Player player = event.getTargetEntity();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                SpongePlayerTank pt = field.getPlayerTank(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                SpongeRegion region = field.getRegion();
                if (!region.isInRegion(player.getLocation()))
                {
                    event.setCancelled(true);
                    player.sendMessage(TextUtils.redText(CommonMessages.OUT_OF_BOUNDS));
                    return;
                }
            }
        }
    }

    @Listener
    public void onPlayerTeleport(Teleport.TargetPlayer event)
    {
        if (event.getCause().containsType(TeleportCause.class))
            return;

        TeleportCause cause = event.getCause().first(TeleportCause.class).get();
        if (cause.getTeleportType() != TeleportTypes.PLUGIN)
            return;

        event.setCancelled(!isInField(event.getTargetEntity().getUniqueId()));
    }

    @Listener
    public void onBowShoot(LaunchProjectileEvent event)
    {
        Projectile projectile = event.getTargetEntity();
        if (!(projectile.getShooter() instanceof Player))
            return;

        Player player = (Player) projectile.getShooter();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
            if (field.getPlayerTank(player.getUniqueId()) != null)
            {
                SpongePlayerTank pt = field.getPlayerTank(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                SpongeCannon cannon = pt.getTank().getCannon();
                pt.setClipSize(pt.getClipSize() - 1);
                ReloadHandler reload = new ReloadHandler(player, cannon, pt.getClipSize());
                event.setCancelled(reload.isReloading());
                if (!event.isCancelled())
                {
                    Inventory inv = player.getInventory();
                    for (Inventory slot : inv.slots())
                    {
                        Optional<ItemStack> itemStackOptional = slot.peek();
                        if (itemStackOptional.isPresent())
                        {
                            ItemStack item = itemStackOptional.get();
                            pt.setClipSize(pt.getClipSize() - 1);
                            if (item.getItem() == ItemTypes.ARROW)
                                item.setQuantity(item.getQuantity() - 1);
                            else if (item.getItem() == ItemTypes.BOW && cannon instanceof SpongeAutoLoader)
                            {
                                SpongeAutoLoader autoLoader = (SpongeAutoLoader) cannon;
                                item.offer(Keys.ITEM_LORE, Arrays.asList(Text.of(CommonItemText.CANNON), Text.of(CommonItemText.clipSize(pt.getClipSize(), autoLoader.getClipSize())), Text.of(CommonItemText.cycleTime(autoLoader.getCycleTime())), Text.of(CommonItemText.clipReloadTime(autoLoader.getReloadTime()))));
                            }
                        }
                    }
                }

                return;
            }
        }
    }

    @Listener
    public void onEntityDamage(DamageEntityEvent event)
    {
        Entity damagedEntity = event.getTargetEntity();
        DamageSource damageSource = event.getCause().first(DamageSource.class).get();
        List<DamageType> validSources = Arrays.asList(DamageTypes.ATTACK, DamageTypes.EXPLOSIVE, DamageTypes.FALL, DamageTypes.PROJECTILE);
        if (!(damagedEntity instanceof Player) && !(damageSource.isExplosive() || validSources.contains(damageSource.getType())))
            return;

        UUID damagedEntityUniqueId = damagedEntity.getUniqueId();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
            if (field.getPlayerTank(damagedEntityUniqueId) != null)
            {
                int damage = (int) event.getBaseDamage() * 2;
                if (damageSource instanceof Arrow && ((Arrow) damageSource).getShooter() instanceof Player && field.getPlayerTank(((Player) ((Arrow) damageSource).getShooter()).getUniqueId()) != null)
                {
                    Arrow arrow = (Arrow) damageSource;
                    UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                    Sponge.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, damagedEntityUniqueId, damager, field, damage));
                    ExplosionTracker.addArrow(arrow);
                }
                else if (damageSource instanceof Player && field.getPlayerTank(((Player) damageSource).getUniqueId()) != null)
                {
                    UUID damager = ((Player) damageSource).getUniqueId();
                    Sponge.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, damagedEntityUniqueId, damager, field, damage));
                }
                else if (damageSource.isExplosive())
                {
                    Arrow arrow = null;
                    for (Arrow a : ExplosionTracker.getTracker())
                        for (Entity entity : damagedEntity.getNearbyEntities(1))
                            if (entity.getUniqueId() == a.getUniqueId())
                                arrow = a;

                    if (arrow == null)
                        return;

                    UUID damager = ((Arrow) arrow.getShooter()).getUniqueId();
                    Sponge.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.SPLASH, damagedEntityUniqueId, damager, field, damage));
                    ExplosionTracker.removeArrow(arrow);
                }
                else if (damageSource.getType() == DamageTypes.FALL)
                    Sponge.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.FALL, damagedEntityUniqueId, field, damage));

                event.setBaseDamage(0);
                return;
            }
        }
    }

    @Listener
    public void onArrowHitBlock(CollideEntityEvent event)
    {
        Arrow arrow = null;
        for (Entity entity : event.getEntities())
            if (entity instanceof Arrow)
                arrow = (Arrow) entity;

        if (arrow == null)
            return;

        if (!(arrow.getShooter() instanceof Player))
            return;

        Player player = (Player) arrow.getShooter();
        if (!isInField(player.getUniqueId()))
            return;

        ExplosionTracker.addArrow(arrow);
        Explosion.Builder eb = Explosion.builder();
        eb.canCauseFire(false);
        eb.origin(arrow.getLocation().getPosition());
        eb.radius(1F);
        eb.shouldBreakBlocks(false);
        eb.world(event.getTargetWorld());
        arrow.getWorld().triggerExplosion(eb.build());
        arrow.remove();
    }
}
