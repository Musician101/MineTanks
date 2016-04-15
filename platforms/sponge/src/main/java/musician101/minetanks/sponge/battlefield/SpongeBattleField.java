package musician101.minetanks.sponge.battlefield;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.handler.ReloadHandler;
import musician101.minetanks.sponge.scoreboard.SpongeMTScoreboard;
import musician101.minetanks.sponge.tank.SpongeTank;
import musician101.minetanks.sponge.util.MTUtils;
import musician101.minetanks.sponge.util.SpongeRegion;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.block.ChangeBlockEvent.Place;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent.Move;
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
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SpongeBattleField extends AbstractBattleField<SpongePlayerTank, SpongeRegion, SpongeMTScoreboard, ItemType, Location<World>, ChangeBlockEvent.Break, ChangeBlockEvent.Place, InteractBlockEvent.Secondary, DropItemEvent.Dispense, ClientConnectionEvent.Join, ClientConnectionEvent.Disconnect, Move.TargetPlayer, Teleport.TargetPlayer, LaunchProjectileEvent, DamageEntityEvent, CollideBlockEvent, ExplosionEvent.Detonate>
{
    public SpongeBattleField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new SpongeMTScoreboard());
    }

    //TODO need to merge listeners
    @Override
    protected boolean isSword(ItemType itemType)
    {
        return false;
    }

    @Override
    public boolean addPlayer(UUID uuid, MTTeam team)
    {
        if (!SpongeMineTanks.getInventoryStorage().save(uuid))
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

        player.setRawData(Sponge.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        SpongeMineTanks.getInventoryStorage().load(player.getUniqueId());
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
            file.createNewFile();
            field = cl.load();
        }
        catch (IOException e)
        {
            SpongeMineTanks.getLogger().warn(CommonMessages.fileLoadFailed(file));
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
            SpongeMineTanks.getLogger().warn(CommonMessages.fileSaveFailed(file));
        }
    }

    @Override
    public void startMatch()
    {
        List<UUID> players = new ArrayList<>();
        for (UUID player : this.players.keySet())
        {
            if (!getPlayerTank(player).isReady())
                return;

            players.add(player);
        }

        Collections.shuffle(players);

        for (UUID uuid : players)
        {
            SpongePlayerTank pt = getPlayerTank(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return;

            if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addGreenPlayer(uuid);
                MTUtils.getPlayer(uuid).setRawData(pt.getTank().getSpeedEffect().toContainer());
                unassigned--;
            }
            else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addRedPlayer(uuid);
                MTUtils.getPlayer(uuid).setRawData(pt.getTank().getSpeedEffect().toContainer());
                unassigned--;
            }
        }

        setInProgress(true);
        for (UUID uuid : players)
        {
            final SpongePlayerTank pt = getPlayerTank(uuid);
            final SpongeTank tank = pt.getTank();
            final Player player = MTUtils.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(uuid))
                    player.setLocation(getGreenSpawn());

                if (getScoreboard().isOnRed(uuid))
                    player.setLocation(getRedSpawn());

                player.setScoreboard(getScoreboard().getScoreboard());
                getScoreboard().setPlayerHealth(uuid, tank.getHealth());
                player.getInventory().clear();
                for (ItemStack item : tank.getWeapons())
                    player.getInventory().set(item);

                player.setHelmet(tank.getHelmet());
                player.setChestplate(tank.getChestplate());
                player.setLeggings(tank.getLeggings());
                player.setBoots(tank.getBoots());
                new ReloadHandler(player, tank.getCannon(), pt.getClipSize()).isReloading();
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
        if ((getScoreboard().getGreenTeamSize() != 0 && getScoreboard().getRedTeamSize() != 0) || !inProgress())
            return;

        if (getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
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

    }

    @Override
    public void onBlockPlace(Place event)
    {

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
