package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.MineTanks;
import musician101.minetanks.handler.ReloadHandler;
import musician101.minetanks.lib.Reference.Messages;
import musician101.minetanks.scoreboard.MTScoreboard;
import musician101.minetanks.spigot.battlefield.player.PlayerTank;
import musician101.minetanks.spigot.battlefield.player.PlayerTank.MTTeam;
import musician101.minetanks.spigot.tank.Tanks;
import musician101.minetanks.spigot.util.JSONConfig;
import musician101.minetanks.spigot.util.MTUtils;
import musician101.minetanks.spigot.util.PotionEffectsEnum;
import musician101.minetanks.spigot.util.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.potion.PotionEffect;
import org.spongepowered.api.potion.PotionEffectBuilder;
import org.spongepowered.api.potion.PotionEffectTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Battlefield
{
    private String name;
    private Location greenSpawn, redSpawn, spectators;
    private Map<UUID, PlayerTank> players = new HashMap<UUID, PlayerTank>();
    private boolean enabled;
    private int unassigned = 0;
    private MTScoreboard sb;
    private boolean inProgress = false;
    private Region cuboid;

    public Battlefield(String name, boolean enabled, Region cuboid, Location greenSpawn, Location redSpawn, Location spectators)
    {
        this.name = name;
        this.enabled = enabled;
        this.cuboid = cuboid;
        this.greenSpawn = greenSpawn;
        this.redSpawn = redSpawn;
        this.spectators = spectators;
        this.sb = new MTScoreboard();
    }

    public Map<UUID, PlayerTank> getPlayers()
    {
        return players;
    }

    public PlayerTank getPlayer(UUID player)
    {
        return players.get(player);
    }

    public String getName()
    {
        return name;
    }

    public Region getRegion()
    {
        return cuboid;
    }

    public void setRegion(Region region)
    {
        this.cuboid = region;
    }

    public Location getGreenSpawn()
    {
        return greenSpawn;
    }

    public void setGreenSpawn(Location loc)
    {
        greenSpawn = loc;
    }

    public Location getRedSpawn()
    {
        return redSpawn;
    }

    public void setRedSpawn(Location loc)
    {
        redSpawn = loc;
    }

    public Location getSpectators()
    {
        return spectators;
    }

    public void setSpectators(Location loc)
    {
        spectators = loc;
    }

    @SuppressWarnings("unchecked")
    public boolean addPlayer(Player player, MTTeam team)
    {
        @SuppressWarnings("deprecation") File file = new File(MineTanks.inventoryStorage, player.getUniqueId().toString() + ".json");
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: An internal error has prevented you from joining the game.");
            return false;
        }

        JSONConfig playerJSON = new JSONConfig();
        //TODO Invnetory API is in the works still
        Inventory inv = player.getInventory();
        for (int slot = 0; slot < inv.getSize(); slot++)
            playerJSON.set("inventory." + slot, inv.getItem(slot));

        for (int slot = 0; slot < player.getInventory().getArmorContents().length; slot++)
            playerJSON.set("armor." + slot, player.getInventory().getArmorContents()[slot]);

        List<Map<String, Object>> effects = new ArrayList<Map<String, Object>>();
        for (PotionEffect effect : player.getPotionEffects())
        {
            Map<String, Object> pe = new HashMap<String, Object>();
            pe.put("ambient", effect.isAmbient());
            pe.put("type", effect.getType().toString());
            pe.put("duration", effect.getDuration());
            pe.put("amplifier", effect.getAmplifier());
            effects.add(pe);
            player.removePotionEffect(effect.getType());
        }

        playerJSON.put("effects", effects);
        Location loc = player.getLocation();
        Map<String, Object> pl = new HashMap<String, Object>();
        pl.put("world", player.getWorld().getName());
        pl.put("x", loc.getPosition().getX());
        pl.put("y", loc.getPosition().getY());
        pl.put("z", loc.getPosition().getZ());
        pl.put("yaw", player.getRotation().getX());
        pl.put("pitch", player.getRotation().getY());
        playerJSON.put("location", pl);
        //TODO Player experience API status is unknown
        playerJSON.put("xp", player.getExp());

        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(playerJSON.toJSONString());
            fw.close();
        }
        catch (IOException e)
        {
            player.sendMessage(Messages.NEGATIVE_PREFIX + "Error: An internal error has prevented you from joining the game.");
            for (Map<String, Object> effect : effects)
            {
                PotionEffectBuilder peb = MineTanks.getGame().getRegistry().getPotionEffectBuilder();
                peb.potionType(PotionEffectsEnum.valueOf(effect.get("type").toString()).getPotionEffectType());
                peb.ambience(Boolean.valueOf(effect.get("ambient").toString()));
                peb.amplifier(Integer.valueOf(effect.get("amplifier").toString()));
                peb.duration(Integer.valueOf(effect.get("duration").toString()));
                player.addPotionEffect(peb.build(), true);
            }

            file.delete();
            return false;
        }
        //TODO experience handling has not been implemented
        player.setExp(0F);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        if (team == MTTeam.SPECTATOR)
        {
            player.teleport(spectators);
            player.sendMessage(Messages.POSITIVE_PREFIX + "You are now spectating in " + name + ".");
        } else
        {
            player.getInventory().setItem(0, MTUtils.createCustomItem(ItemTypes.STICK, "Open Hangar", Arrays.asList("Tank: None")));
            player.getInventory().setItem(1, MTUtils.createCustomItem(ItemTypes.CLOCK, "Ready Up", Arrays.asList("You are currently not ready.")));
            unassigned++;
        }

        players.put(player.getUniqueId(), new PlayerTank(player.getUniqueId(), team));
        return true;
    }

    public boolean removePlayer(Player player)
    {
        PlayerTank pt = getPlayer(player.getUniqueId());
        if (pt == null)
            return false;

        player.removePotionEffect(PotionEffectTypes.SLOWNESS);
        player.removePotionEffect(PotionEffectTypes.SPEED);

        File file = new File(MineTanks.inventoryStorage, player.getUniqueId().toString() + ".yml");
        if (file.exists())
        {
            JSONParser parser = new JSONParser();
            //TODO need to create custom JSONObject class so that it's a lot more user friendly
            JSONObject playerJSON = (JSONObject) parser.parse(new FileReader(file));
            player.getInventory().clear();
            for (int slot = 0; slot < player.getInventory().getSize(); slot++)
                player.getInventory().setItem(slot, playerJSON.getItemStack("inventory." + slot));

            ItemStack[] armor = new ItemStack[4];
            for (int slot = 0; slot < armor.length; slot++)
                armor[slot] = playerJSON.getItemStack("armor." + slot);

            player.getInventory().setArmorContents(armor);

            JSONArray effects = (JSONArray) playerJSON.get("effects");
            for (Object effectObj : effects)
            {
                JSONObject effect = (JSONObject) effectObj;
                PotionEffectBuilder peb = MineTanks.getGame().getRegistry().getPotionEffectBuilder();
                peb.potionType(PotionEffectsEnum.valueOf(effect.get("type").toString()).getPotionEffectType());
                peb.ambience(Boolean.valueOf(effect.get("ambient").toString()));
                peb.amplifier(Integer.valueOf(effect.get("amplifier").toString()));
                peb.duration(Integer.valueOf(effect.get("duration").toString()));
                player.addPotionEffect(peb.build(), true);
            }

            file.delete();
        }

        if (sb.isOnGreen(player) || sb.isOnRed(player))
            sb.playerDeath(player);

        players.remove(player.getUniqueId());
        if (unassigned > 0)
            unassigned--;

        return true;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isReady()
    {
        if (cuboid == null)
            return false;

        if (greenSpawn == null)
            return false;

        if (redSpawn == null)
            return false;

        if (spectators == null)
            return false;

        if (!enabled)
            return false;

        return true;
    }

    @SuppressWarnings("unchecked")
    public void saveToFile()
    {
        File file = new File(MineTanks.battlefields, this.name + ".json");
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            MineTanks.getLogger().warn("Error: Failed to create file: " + file.getName());
            return;
        }

        JSONObject field = new JSONObject();
        if (cuboid != null)
            field.put("cuboid", cuboid.serialize());

        if (greenSpawn != null)
        {
            field.put("greenSpawn.world", ((World) greenSpawn.getExtent()).getName());
            field.put("greenSpawn.x", greenSpawn.getPosition().getX());
            field.put("greenSpawn.y", greenSpawn.getPosition().getY());
            field.put("greenSpawn.z", greenSpawn.getPosition().getZ());
        }

        if (redSpawn != null)
        {
            field.put("redSpawn.world", ((World) redSpawn.getExtent()).getName());
            field.put("redSpawn.x", redSpawn.getPosition().getX());
            field.put("redSpawn.y", redSpawn.getPosition().getY());
            field.put("redSpawn.z", redSpawn.getPosition().getZ());
        }

        if (spectators != null)
        {
            field.put("spectators.world", ((World) spectators.getExtent()).getName());
            field.put("spectators.x", spectators.getPosition().getX());
            field.put("spectators.y", spectators.getPosition().getY());
            field.put("spectators.z", spectators.getPosition().getZ());
        }

        field.put("enabled", enabled);
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(field.toJSONString());
            fw.close();
        }
        catch (IOException e)
        {
            MineTanks.getLogger().warn("Error: Could not save " + file.getName());
        }
    }

    public boolean canPlayerExit(UUID player)
    {
        PlayerTank pt = getPlayer(player);
        if (pt != null)
            return pt.getTeam().canExit();

        return false;
    }

    public void startMatch()
    {
        List<UUID> players = new ArrayList<UUID>();
        for (UUID player : this.players.keySet())
        {
            if (!getPlayer(player).isReady())
                return;

            players.add(player);
        }

        Collections.shuffle(players);

        for (UUID uuid : players)
        {
            PlayerTank pt = getPlayer(uuid);
            if (sb.getGreenTeamSize() == 0 && sb.getRedTeamSize() == 0 && unassigned < 2)
                return;

            if (sb.getGreenTeamSize() == sb.getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                unassigned--;
            } else if (sb.getGreenTeamSize() <= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                //TODO Waiting too see how scoreboards are implemented before I change this.
                sb.addGreenPlayer(Bukkit.getOfflinePlayer(uuid));
                MineTanks.getGame().getServer().get().getPlayer(uuid).get().addPotionEffect(pt.getTank().getSpeedEffect(), true);
                unassigned--;
            } else if (sb.getGreenTeamSize() >= sb.getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                sb.addRedPlayer(Bukkit.getOfflinePlayer(uuid));
                MineTanks.getGame().getServer().get().getPlayer(uuid).get().addPotionEffect(pt.getTank().getSpeedEffect(), true);
                unassigned--;
            }
        }

        inProgress = true;
        for (UUID uuid : players)
        {
            final PlayerTank pt = getPlayer(uuid);
            final Tanks tank = pt.getTank();
            final Player player = MineTanks.getGame().getServer().get().getPlayer(uuid).get();
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (sb.isOnGreen(player))
                    player.teleport(greenSpawn);

                if (sb.isOnRed(player))
                    player.teleport(redSpawn);

                player.setScoreboard(sb.getScoreboard());
                sb.setPlayerHealth(uuid, tank.getHealth());
                MineTanks.getGame().getScheduler().runTaskAfter(MineTanks.getPluginContainer(), new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                player.getInventory().setContents(tank.getWeapons().getContents());
                                player.getInventory().setArmorContents(tank.getArmor());
                            }
                        }, 1L);
                new ReloadHandler(player, tank.getCannonType(), tank.reloadTime(), tank.cycleTime(), pt.getClipSize(), tank.getClipSize()).isReloading();
            } else
            {
                pt.setTank(null);
                player.getInventory().clear();
            }
        }
    }

    public void endMatch()
    {
        if ((sb.getGreenTeamSize() != 0 && sb.getRedTeamSize() != 0) || !inProgress)
            return;

        if (sb.getGreenTeamSize() == 0 || sb.getRedTeamSize() == 0)
        {
            inProgress = false;
            for (UUID uuid : players.keySet())
            {
                Player player = MineTanks.getGame().getServer().get().getPlayer(uuid).get();
                player.teleport(spectators);
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.getInventory().clear();
                player.removePotionEffect(PotionEffectTypes.SLOWNESS);
                player.removePotionEffect(PotionEffectTypes.SPEED);
                PlayerTank pt = getPlayer(uuid);
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                sb.playerDeath(player);
                if (sb.getGreenTeamSize() == 0)
                    player.sendMessage(Messages.NEGATIVE_PREFIX + "Red team wins!");
                else if (sb.getRedTeamSize() == 0)
                    player.sendMessage(Messages.POSITIVE_PREFIX + "Green team wins!");
            }
        }
    }

    public boolean inProgress()
    {
        return inProgress;
    }

    public void setInProgress(boolean inProgress)
    {
        this.inProgress = inProgress;
    }

    public void playerKilled(UUID player)
    {
        getPlayer(player).killed();
        sb.playerDeath(MineTanks.getGame().getServer().get().getPlayer(player).get());
        MineTanks.getGame().getServer().get().getPlayer(player).get().teleport(spectators);
    }

    public MTScoreboard getScoreboard()
    {
        return sb;
    }
}
