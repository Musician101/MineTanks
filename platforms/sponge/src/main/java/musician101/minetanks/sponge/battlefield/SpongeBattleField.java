package musician101.minetanks.sponge.battlefield;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonItemText;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.battlefield.AbstractBattleField;
import musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.battlefield.player.SpongePlayerTank;
import musician101.minetanks.sponge.handler.ReloadHandler;
import musician101.minetanks.sponge.scoreboard.MTScoreboard;
import musician101.minetanks.sponge.tank.Tank;
import musician101.minetanks.sponge.util.MTUtils;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.catalog.CatalogEntityData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SpongeBattleField extends AbstractBattleField<SpongePlayerTank, SpongeRegion, MTScoreboard, Location<World>>
{
    public SpongeBattleField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new MTScoreboard());
    }

    @Override
    public boolean addPlayer(UUID playerId, MTTeam team)
    {
        if (!SpongeMineTanks.getInventoryStorage().save(playerId))
            return false;

        Player player = MTUtils.getPlayer(playerId);
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

        players.put(playerId, new SpongePlayerTank(playerId, team));
        return true;
    }

    @Override
    public boolean removePlayer(UUID playerId)
    {
        Game game = Sponge.getGame();
        Player player = MTUtils.getPlayer(playerId);
        SpongePlayerTank pt = getPlayerTank(playerId);
        if (pt == null)
            return false;

        player.setRawData(game.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());
        player.getInventory().clear();
        player.setHelmet(null);
        player.setChestplate(null);
        player.setLeggings(null);
        player.setBoots(null);

        SpongeMineTanks.getInventoryStorage().load(player.getUniqueId());
        if (getScoreboard().isOnGreen(playerId) || getScoreboard().isOnRed(playerId))
            getScoreboard().playerDeath(playerId);

        players.remove(player.getUniqueId());
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File storageDir)
    {
        File file = new File(storageDir, CommonConfig.battlefieldFile(this, "json"));
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            SpongeMineTanks.getLogger().warn(CommonMessages.fileLoadFailed(file));
            return;
        }

        SpongeJSONConfig field = new SpongeJSONConfig();
        if (getRegion() != null)
            field.set(CommonConfig.REGION, getRegion().serialize());

        if (getGreenSpawn() != null)
            field.set(CommonConfig.GREEN_SPAWN, getGreenSpawn().toContainer().toString());

        if (getRedSpawn() != null)
            field.set(CommonConfig.RED_SPAWN, getRedSpawn().toContainer().toString());

        if (getSpectators() != null)
            field.set(CommonConfig.SPECTATORS, getSpectators().toContainer().toString());

        field.set(CommonConfig.ENABLED, isEnabled());
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(field.toJSONString());
            fw.close();
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
            final Tank tank = pt.getTank();
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
                Game game = Sponge.getGame();
                Player player = game.getServer().getPlayer(uuid).get();
                player.setLocation(getSpectators());
                player.setHelmet(null);
                player.setChestplate(null);
                player.setLeggings(null);
                player.setBoots(null);
                player.getInventory().clear();
                player.setRawData(game.getDataManager().getManipulatorBuilder(CatalogEntityData.POTION_EFFECT_DATA).get().create().toContainer());

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
}
