package musician101.minetanks.sponge.battlefield;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class SpongeBattleFieldStorage extends AbstractBattleFieldStorage
{
    public SpongeBattleFieldStorage(File configDir)
    {
        super(new File(configDir, "battlefields"));
        loadFromFiles();
    }

    @Override
    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    public boolean createField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
    {
        for (String field : getFields().keySet())
            if (field.equalsIgnoreCase(name))
                return false;

        SpongeBattleField field = new SpongeBattleField(name, enabled, region, greenSpawn, redSpawn, spectators);
        getFields().put(name, field);
        return true;
    }

    @Override
    public boolean removeField(String field)
    {
        for (String name : fields.keySet())
        {
            if (name.equalsIgnoreCase(field))
            {
                fields.remove(field);
                return new File(getStorageDir(), field + ".json").delete();
            }
        }

        return false;
    }

    public SpongeBattleField getField(String name)
    {
        if (!fields.containsKey(name))
            return null;

        return (SpongeBattleField) fields.get(name);
    }

    @Override
    public void loadFromFiles()
    {
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".json"))
            {
                try
                {
                    SpongeJSONConfig field = (SpongeJSONConfig) new JSONParser().parse(new FileReader(file));
                    String name = file.getName().replace(".json", "");
                    boolean enabled = field.getBoolean("enabled");
                    SpongeRegion region = null;
                    Location<World> greenSpawn = null;
                    Location<World> redSpawn = null;
                    Location<World> spectators = null;

                    if (field.containsKey("region"))
                        region = new SpongeRegion(field.getSpongeJSONConfig("region"));

                    if (field.containsKey("greenSpawn"))
                        greenSpawn = deserializeLocation(field.getSpongeJSONConfig("greenSpawn"));

                    if (field.containsKey("redSpawn"))
                        redSpawn = deserializeLocation(field.getSpongeJSONConfig("redSpawn"));

                    if (field.containsKey("spectators"))
                        spectators = deserializeLocation(field.getSpongeJSONConfig("spectators"));

                    if (!createField(name, enabled, region, greenSpawn, redSpawn, spectators))
                        SpongeMineTanks.getLogger().warn("Failed to load " + file.getName());
                }
                catch (IOException | ParseException e)
                {
                    SpongeMineTanks.getLogger().warn("Failed to load " + file.getName());
                }
            }
        }
    }

    private static Location<World> deserializeLocation(SpongeJSONConfig locJSON)
    {
        World world = SpongeMineTanks.getGame().getServer().getWorld(locJSON.get("world").toString()).get();
        return new Location<>(world, locJSON.getInteger("x", 0), locJSON.getInteger("y", 0), locJSON.getInteger("z", 0));
    }

    @Override
    public void saveToFiles()
    {
        for (String name : fields.keySet())
            fields.get(name).saveToFile(getStorageDir());
    }

    @Override
    public boolean canPlayerExit(UUID player)
    {
        for (String name : fields.keySet())
        {
            SpongeBattleField field = getField(name);
            if (field.getPlayers().containsKey(player))
                return field.getPlayer(player).getTeam().canExit();
        }

        return false;
    }
}
