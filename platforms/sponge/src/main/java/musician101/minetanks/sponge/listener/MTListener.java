package musician101.minetanks.sponge.listener;

import com.flowpowered.math.vector.Vector3d;
import musician101.common.java.util.ListUtil;
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
import musician101.minetanks.sponge.lib.Reference.Messages;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.tank.Tanks;
import musician101.minetanks.sponge.tank.module.cannon.SpongeAutoLoader;
import musician101.minetanks.sponge.tank.module.cannon.SpongeCannon;
import musician101.minetanks.sponge.util.SpongeInventoryStorage;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.block.BlockTransaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogItemData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.explosive.Explosive;
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
import org.spongepowered.api.event.inventory.DropItemEvent.Dispense;
import org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect;
import org.spongepowered.api.event.network.ClientConnectionEvent.Join;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.world.explosion.ExplosionBuilder;

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
            if (SpongeMineTanks.getFieldStorage().getField(name).getPlayer(playerId) != null)
                return true;

        return false;
    }

    private boolean isSword(ItemType type)
    {
        //It's technically a sword without a blade.
        //Stick is the default item if a player hasn't chosen a tank.
        if (type == ItemTypes.STICK)
            return true;

        if (type == ItemTypes.WOODEN_SWORD)
            return true;

        if (type == ItemTypes.STONE_SWORD)
            return true;

        if (type == ItemTypes.IRON_SWORD)
            return true;

        if (type == ItemTypes.GOLDEN_SWORD)
            return true;

        if (type == ItemTypes.DIAMOND_SWORD)
            return true;

        return false;
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
            for (BlockTransaction blockTransaction : event.getTransactions())
            {
                if (field.getRegion().isInRegion(blockTransaction.getOriginal().getLocation().get()))
                {
                    Optional<Player> playerOptional = event.getCause().first(Player.class);
                    if (playerOptional.isPresent())
                    {
                        if (playerOptional.get().hasPermission("minetanks.edit") && !field.isEnabled())
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

            if (field.getPlayer(player.getUniqueId()) != null)
                SpongeMineTanks.getGame().getEventManager().post(new AttemptMenuOpenEvent(type, field.getName(), field.getPlayer(player.getUniqueId()), player.getUniqueId()));
        }
    }

    @Listener
    public void onItemDrop(Dispense event)
    {
        if (!event.getCause().any(Player.class))
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

        player.sendMessage(Texts.of(Messages.POSITIVE_PREFIX + "You logged off with items still stored away. They will now be returned to you."));
        sis.load(player.getUniqueId());
    }

    @Listener
    public void onPlayerDisconnect(Disconnect event)
    {
        Player player = event.getTargetEntity();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            if (SpongeMineTanks.getFieldStorage().getField(name).getPlayer(player.getUniqueId()) != null)
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
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                SpongePlayerTank pt = field.getPlayer(player.getUniqueId());
                if (pt.getTeam() == MTTeam.SPECTATOR)
                    return;

                SpongeRegion region = field.getRegion();
                if (!region.isInRegion(player.getLocation()))
                {
                    event.setCancelled(true);
                    player.sendMessage(Texts.of(Messages.NEGATIVE_PREFIX + "Out of bounds!"));
                    return;
                }
            }
        }
    }

    @Listener
    public void onPlayerTeleport(Teleport.TargetPlayer event)
    {
        if (!(event.getCause() instanceof TeleportCause))
            return;

        TeleportCause cause = (TeleportCause) event.getCause();
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
            if (field.getPlayer(player.getUniqueId()) != null)
            {
                SpongePlayerTank pt = field.getPlayer(player.getUniqueId());
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
                        Optional<ItemStack> iso = slot.peek();
                        if (iso.isPresent())
                        {
                            ItemStack item = iso.get();
                            pt.setClipSize(pt.getClipSize() - 1);
                            if (item.getItem() == ItemTypes.ARROW)
                                item.setQuantity(item.getQuantity() - 1);
                            else if (item.getItem() == ItemTypes.BOW && cannon instanceof SpongeAutoLoader)
                            {
                                LoreData lore = item.get(CatalogItemData.LORE_DATA).get();
                                lore.set(SpongeMineTanks.getGame().getRegistry().createValueBuilder().createListValue(Keys.ITEM_LORE, new ListUtil<Text>(Texts.of("Your Cannon"), Texts.of("Clip Size: " + pt.getClipSize() + "/" + ((SpongeAutoLoader) cannon).getClipSize()), Texts.of("Clip Reload Time: " + cannon.getReloadTime()))));
                                item.setRawData(lore.toContainer());
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
        if (!(damagedEntity instanceof Player) && !(damageSource.isExplosion() || validSources.contains(damageSource.getDamageType())))
            return;

        UUID damagedEntityUniqueId = damagedEntity.getUniqueId();
        for (String name : SpongeMineTanks.getFieldStorage().getFields().keySet())
        {
            SpongeBattleField field = SpongeMineTanks.getFieldStorage().getField(name);
            if (field.getPlayer(damagedEntityUniqueId) != null)
            {
                int damage = (int) event.getBaseDamage() * 2;
                if (damageSource instanceof Arrow && ((Arrow) damageSource).getShooter() instanceof Player && field.getPlayer(((Player) ((Arrow) damageSource).getShooter()).getUniqueId()) != null)
                {
                    Arrow arrow = (Arrow) damageSource;
                    UUID damager = ((Player) arrow.getShooter()).getUniqueId();
                    SpongeMineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.PENETRATION, damagedEntityUniqueId, damager, field, damage));
                    ExplosionTracker.addArrow(arrow);
                }
                else if (damageSource instanceof Player && field.getPlayer(((Player) damageSource).getUniqueId()) != null)
                {
                    UUID damager = ((Player) damageSource).getUniqueId();
                    SpongeMineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.RAM, damagedEntityUniqueId, damager, field, damage));
                }
                else if (damageSource.isExplosion())
                {
                    Arrow arrow = null;
                    for (Arrow a : ExplosionTracker.getTracker())
                        if (((Arrow) damageSource).getUniqueId() == a.getUniqueId())
                            arrow = a;

                    UUID damager = ((Arrow) arrow.getShooter()).getUniqueId();
                    SpongeMineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.SPLASH, damagedEntityUniqueId, damager, field, damage));
                    ExplosionTracker.removeArrow(arrow);
                }
                else if (damageSource.getDamageType() == DamageTypes.FALL)
                    SpongeMineTanks.getGame().getEventManager().post(new PlayerTankDamageEvent(PlayerTankDamageCause.FALL, damagedEntityUniqueId, field, damage));

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
        ExplosionBuilder eb = SpongeMineTanks.getGame().getRegistry().createExplosionBuilder();
        eb.canCauseFire(false);
        eb.origin(arrow.getLocation().getPosition());
        eb.radius(1F);
        eb.shouldBreakBlocks(false);
        eb.world(event.getTargetWorld());
        arrow.getWorld().triggerExplosion(eb.build());
        arrow.remove();
    }
}
