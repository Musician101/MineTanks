package musician101.minetanks.sponge.battlefield;

import musician101.common.java.minecraft.sponge.config.SpongeJSONConfig;
import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonStorage;
import musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.SpongeRegion;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class SpongeBattleFieldStorage extends AbstractBattleFieldStorage<SpongeBattleField>
{
    public SpongeBattleFieldStorage(File configDir)
    {
        super(new File(configDir, CommonStorage.BATTLEFIELDS));
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
                fields.remove(name);
                return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(name), ".json")).delete();
            }
        }

        return false;
    }

    @Override
    public void loadFromFiles()
    {
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".json"))
            {
                Logger logger = SpongeMineTanks.getLogger();
                try
                {
                    SpongeJSONConfig field = (SpongeJSONConfig) new JSONParser().parse(new FileReader(file));
                    String name = file.getName().replace(".json", "");
                    boolean enabled = field.getBoolean(CommonConfig.ENABLED);
                    SpongeRegion region = null;
                    Location<World> greenSpawn = null;
                    Location<World> redSpawn = null;
                    Location<World> spectators = null;

                    if (field.containsKey(CommonConfig.REGION))
                        region = new SpongeRegion(field.getSpongeJSONConfig(CommonConfig.REGION));

                    if (field.containsKey(CommonConfig.GREEN_SPAWN))
                        greenSpawn = deserializeLocation(field.getSpongeJSONConfig(CommonConfig.GREEN_SPAWN));

                    if (field.containsKey(CommonConfig.RED_SPAWN))
                        redSpawn = deserializeLocation(field.getSpongeJSONConfig(CommonConfig.RED_SPAWN));

                    if (field.containsKey(CommonConfig.SPECTATORS))
                        spectators = deserializeLocation(field.getSpongeJSONConfig(CommonConfig.SPECTATORS));

                    if (!createField(name, enabled, region, greenSpawn, redSpawn, spectators))
                        logger.warn(CommonMessages.fileLoadFailed(file));
                }
                catch (IOException | ParseException e)
                {
                    logger.warn(CommonMessages.fileLoadFailed(file));
                }
            }
        }
    }

    /* Change json storage to HOCON */
    @Deprecated
    private static Location<World> deserializeLocation(SpongeJSONConfig locJSON)
    {
        World world = Sponge.getGame().getServer().getWorld(locJSON.get(CommonConfig.WORLD).toString()).get();
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
                return field.getPlayerTank(player).getTeam().canExit();
        }

        return false;
    }
}
