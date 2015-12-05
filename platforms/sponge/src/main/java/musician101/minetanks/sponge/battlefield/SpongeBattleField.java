package musician101.minetanks.sponge.battlefield;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.handler.ReloadHandler;
import musician101.minetanks.sponge.lib.SpongeReference.SpongeMessages;
import musician101.minetanks.sponge.scoreboard.MTScoreboard;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.util.MTUtils;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SpongeBattleField extends AbstractBattleField
{
    private Location<World> greenSpawn;
    private Location<World> redSpawn;
    private Location<World> spectators;
    private MTScoreboard sb;
    private SpongeRegion region;

    public SpongeBattleField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        super(name, enabled);
        this.region = region;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.sb = new MTScoreboard();
    }

    public SpongePlayerTank getPlayer(UUID player)
    {
        return (SpongePlayerTank) getPlayers().get(player);
    }

    public SpongeRegion getRegion()
    {
        return region;
    }

    public void setRegion(SpongeRegion region)
    {
        this.region = region;
    }

    public Location getGreenSpawn()
    {
        return greenSpawn;
    }

    public void setGreenSpawn(Location<World> loc)
    {
        greenSpawn = loc;
    }

    public Location getRedSpawn()
    {
        return redSpawn;
    }

    public void setRedSpawn(Location<World> loc)
    {
        redSpawn = loc;
    }

    public Location getSpectators()
    {
        return spectators;
    }

    public void setSpectators(Location<World> loc)
    {
        spectators = loc;
    }

    @Override
    public boolean addPlayer(UUID playerId, MTTeam team)
    {
        if (!SpongeMineTanks.getInventoryStorage().save(playerId))
            return false;

        Player player = MTUtils.getPlayer(playerId);
        if (team == MTTeam.SPECTATOR)
        {
            player.setLocation(spectators);
            player.sendMessage(Texts.of(SpongeMessages.POSITIVE_PREFIX + "You are now spectating in " + getName() + "."));
        }
        else
        {
            player.getInventory().set(MTUtils.createCustomItem(ItemTypes.STICK, "Open Hangar", Arrays.asList("Tank: None")));
            player.getInventory().set(MTUtils.createCustomItem(ItemTypes.CLOCK, "Ready Up", Arrays.asList("You are currently not ready.")));
            unassigned++;
        }

        players.put(playerId, new SpongePlayerTank(playerId, team));
        return true;
    }

    @Override
    public boolean removePlayer(UUID playerId)
    {
        Game game = SpongeMineTanks.getGame();
        Player player = MTUtils.getPlayer(playerId);
        SpongePlayerTank pt = getPlayer(playerId);
        if (pt == null)
            return false;

        player.setRawData(game.getRegistry().getManipulatorRegistry().getBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        SpongeMineTanks.getInventoryStorage().load(player.getUniqueId());

        if (sb.isOnGreen(playerId) || sb.isOnRed(playerId))
            sb.playerDeath(playerId);

        players.remove(player.getUniqueId());
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    @Override
    public boolean isReady()
    {
        if (region == null)
            return false;

        if (greenSpawn == null)
            return false;

        if (redSpawn == null)
            return false;

        if (spectators == null)
            return false;

        if (!isEnabled())
            return false;

        return true;
    }

    @Override
    public void saveToFile(File storageDir)
    {
        File file = new File(storageDir, getName() + ".json");
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            SpongeMineTanks.getLogger().warn("Error: Failed to create file: " + file.getName());
            return;
        }

        SpongeJSONConfig field = new SpongeJSONConfig();
        if (region != null)
            field.set("region", region.serialize());

        if (greenSpawn != null)
        {
            field.set("greenSpawn.world", ((World) greenSpawn.getExtent()).getName());
            field.set("greenSpawn.x", greenSpawn.getPosition().getX());
            field.set("greenSpawn.y", greenSpawn.getPosition().getY());
            field.set("greenSpawn.z", greenSpawn.getPosition().getZ());
        }

        if (redSpawn != null)
        {
            field.set("redSpawn.world", ((World) redSpawn.getExtent()).getName());
            field.set("redSpawn.x", redSpawn.getPosition().getX());
            field.set("redSpawn.y", redSpawn.getPosition().getY());
            field.set("redSpawn.z", redSpawn.getPosition().getZ());
        }

        if (spectators != null)
        {
            field.set("spectators.world", ((World) spectators.getExtent()).getName());
            field.set("spectators.x", spectators.getPosition().getX());
            field.set("spectators.y", spectators.getPosition().getY());
            field.set("spectators.z", spectators.getPosition().getZ());
        }

        field.set("enabled", isEnabled());
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(field.toJSONString());
            fw.close();
        }
        catch (IOException e)
        {
            SpongeMineTanks.getLogger().warn("Error: Could not save " + file.getName());
        }
    }

    @Override
    public boolean canPlayerExit(UUID player)
    {
        SpongePlayerTank pt = getPlayer(player);
        if (pt != null)
            return pt.getTeam().canExit();

        return false;
    }

    @Override
    public void startMatch()
    {
        List<UUID> players = new ArrayList<>();
        for (UUID player : this.players.keySet())
        {
            if (!getPlayer(player).isReady())
                return;

            players.add(player);
        }

        Collections.shuffle(players);

        for (UUID uuid : players)
        {
            SpongePlayerTank pt = getPlayer(uuid);
            if (sb.getGreenTeamSize() == 0 && sb.getRedTeamSize() == 0 && unassigned < 2)
                return;

            if (sb.getGreenTeamSize() == sb.getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                unassigned--;
            }
            else if (sb.getGreenTeamSize() <= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                sb.addGreenPlayer(uuid);
                MTUtils.getPlayer(uuid).setRawData(pt.getTank().getSpeedEffect().toContainer());
                unassigned--;
            }
            else if (sb.getGreenTeamSize() >= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                sb.addRedPlayer(uuid);
                MTUtils.getPlayer(uuid).setRawData(pt.getTank().getSpeedEffect().toContainer());
                unassigned--;
            }
        }

        setInProgress(true);
        for (UUID uuid : players)
        {
            final SpongePlayerTank pt = getPlayer(uuid);
            final Tank tank = pt.getTank();
            final Player player = MTUtils.getPlayer(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (sb.isOnGreen(uuid))
                    player.setLocation(greenSpawn);

                if (sb.isOnRed(uuid))
                    player.setLocation(redSpawn);

                player.setScoreboard(sb.getScoreboard());
                sb.setPlayerHealth(uuid, tank.getHealth());
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
        if ((sb.getGreenTeamSize() != 0 && sb.getRedTeamSize() != 0) || !inProgress())
            return;

        if (sb.getGreenTeamSize() == 0 || sb.getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (UUID uuid : players.keySet())
            {
                Game game = SpongeMineTanks.getGame();
                Player player = game.getServer().getPlayer(uuid).get();
                player.setLocation(spectators);
                player.setHelmet(null);
                player.setChestplate(null);
                player.setLeggings(null);
                player.setBoots(null);
                player.getInventory().clear();
                player.setRawData(game.getRegistry().getManipulatorRegistry().getBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());

                SpongePlayerTank pt = getPlayer(uuid);
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                sb.playerDeath(uuid);
                if (forced)
                    player.sendMessage(Texts.of(SpongeMineTanks.getPrefix() + "The match has been forcibly ended by an admin."));
                else if (sb.getGreenTeamSize() == 0)
                    player.sendMessage(Texts.of(SpongeMessages.NEGATIVE_PREFIX + "Red team wins!"));
                else if (sb.getRedTeamSize() == 0)
                    player.sendMessage(Texts.of(SpongeMessages.POSITIVE_PREFIX + "Green team wins!"));
            }
        }
    }

    @Override
    public void playerKilled(UUID playerId)
    {
        getPlayer(playerId).killed();
        sb.playerDeath(playerId);
        Player player = MTUtils.getPlayer(playerId);
        player.setLocation(spectators);
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);
    }

    public MTScoreboard getScoreboard()
    {
        return sb;
    }
}
