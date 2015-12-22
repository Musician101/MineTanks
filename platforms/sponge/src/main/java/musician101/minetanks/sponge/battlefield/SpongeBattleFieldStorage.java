package musician101.minetanks.sponge.battlefield;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonStorage;
import musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import musician101.minetanks.sponge.SpongeMineTanks;
import musician101.minetanks.sponge.util.SpongeRegion;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.LocateableSnapshot;
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
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

    private boolean createField(String name, boolean enabled, SpongeRegion region, Location<World> greenSpawn, Location<World> redSpawn, Location<World> spectators)
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
                return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(name), ".cfg")).delete();
            }
        }

        return false;
    }

    @Override
    public void loadFromFiles()
    {
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".cfg"))
            {
                Logger logger = SpongeMineTanks.getLogger();
                try
                {
                    ConfigurationLoader<CommentedConfigurationNode> cl = HoconConfigurationLoader.builder().setFile(file).build();
                    ConfigurationNode field = cl.load();
                    String name = file.getName().replace(".cfg", "");
                    boolean enabled = field.getNode(CommonConfig.ENABLED).getBoolean();
                    SpongeRegion region = null;
                    Location<World> greenSpawn = null;
                    Location<World> redSpawn = null;
                    Location<World> spectators = null;

                    if (!field.getNode(CommonConfig.REGION).isVirtual())
                        region = new SpongeRegion(field.getNode(CommonConfig.REGION));

                    if (!field.getNode(CommonConfig.GREEN_SPAWN).isVirtual())
                        greenSpawn = deserializeLocation(field.getNode(CommonConfig.GREEN_SPAWN));

                    if (!field.getNode(CommonConfig.RED_SPAWN).isVirtual())
                        redSpawn = deserializeLocation(field.getNode(CommonConfig.RED_SPAWN));

                    if (!field.getNode(CommonConfig.SPECTATORS).isVirtual())
                        spectators = deserializeLocation(field.getNode(CommonConfig.SPECTATORS));

                    if (!createField(name, enabled, region, greenSpawn, redSpawn, spectators))
                        logger.warn(CommonMessages.fileLoadFailed(file));
                }
                catch (IOException e)
                {
                    logger.warn(CommonMessages.fileLoadFailed(file));
                }
            }
        }
    }

    private static Location<World> deserializeLocation(ConfigurationNode node)
    {
        ConfigurateTranslator ct = ConfigurateTranslator.instance();
        DataView dv = ct.translateFrom(node);
        /** It complains about raw types despite the method currently not relying on it. */
        @SuppressWarnings("unchecked")
        Optional<Location<World>> olw = Sponge.getGame().getDataManager().getBuilder(LocateableSnapshot.class).get().build(dv).get().getLocation();
        return olw.get();
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
