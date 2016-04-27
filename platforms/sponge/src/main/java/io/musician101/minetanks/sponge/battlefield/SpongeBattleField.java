package io.musician101.minetanks.sponge.battlefield;

import io.musician101.common.java.minecraft.sponge.TextUtils;
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
import io.musician101.minetanks.sponge.util.MTUtils;
import io.musician101.minetanks.sponge.util.SpongeRegion;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.block.ChangeBlockEvent.Place;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Move.TargetPlayer;
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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpongeBattleField extends AbstractBattleField<SpongeMineTanks, SpongePlayerTank, SpongeRegion, SpongeMTScoreboard, ItemType, Location<World>, ChangeBlockEvent.Break, ChangeBlockEvent.Place, InteractBlockEvent.Secondary, DropItemEvent.Dispense, ClientConnectionEvent.Join, ClientConnectionEvent.Disconnect, DisplaceEntityEvent.Move.TargetPlayer, DisplaceEntityEvent.Teleport.TargetPlayer, LaunchProjectileEvent, DamageEntityEvent, CollideBlockEvent, ExplosionEvent.Detonate>
{
    public SpongeBattleField(SpongeMineTanks plugin, String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        super(plugin, name, enabled, region, greenSpawn, redSpawn, spectators, new SpongeMTScoreboard());
    }

    @Override
    protected boolean menuItem(ItemType itemType)
    {
        return false;
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
                Player player = Sponge.getGame().getServer().getPlayer(uuid).get();
                player.setLocation(getSpectators());
                player.setHelmet(null);
                player.setChestplate(null);
                player.setLeggings(null);
                player.setBoots(null);
                player.getInventory().clear();
                player.setRawData(Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());

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
        //TODO left off here
    }

    @Override
    protected boolean handleWatch(UUID uuid)
    {
        return false;
    }

    @Override
    protected boolean processBlockInteract(UUID uuid)
    {
        return false;
    }

    @Override
    public void onBlockInteract(Secondary event)
    {

    }

    @Override
    public void onItemDrop(Dispense event)
    {

    }

    @Override
    public void onPlayerJoin(Join event)
    {

    }

    @Override
    public void onPlayerQuit(Disconnect event)
    {

    }

    @Override
    public void onPlayerMove(TargetPlayer event)
    {

    }

    @Override
    public void onPlayerTeleport(Teleport.TargetPlayer event)
    {

    }

    @Override
    protected void arrowIsDamager(UUID damager, double damage, UUID damaged)
    {

    }

    @Override
    protected void playerIsDamager(UUID damager, double damage, UUID damaged)
    {

    }

    @Override
    protected void blockExplosion(UUID damager, double damage, UUID damaged)
    {

    }

    @Override
    public void onBowShoot(LaunchProjectileEvent event)
    {

    }

    @Override
    public void onEntityDamage(DamageEntityEvent event)
    {

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
    protected void playerHit(UUID rammed, UUID rammer, double damage)
    {

    }

    @Override
    protected void triggerPlayerDeath(UUID killerId, UUID killedId)
    {

    }
}
