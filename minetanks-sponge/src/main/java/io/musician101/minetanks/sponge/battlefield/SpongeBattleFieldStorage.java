package io.musician101.minetanks.sponge.battlefield;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonStorage;
import io.musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import io.musician101.minetanks.sponge.SpongeMineTanks;
import io.musician101.minetanks.sponge.util.SpongeRegion;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.LocatableSnapshot;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.DataTranslators;
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

    private static Location<World> deserializeLocation(ConfigurationNode node)
    {
        DataTranslator<ConfigurationNode> dt = DataTranslators.CONFIGURATION_NODE;
        DataView dv = dt.translate(node);
        Optional<DataBuilder<LocatableSnapshot>> dataBuilderOptional = Sponge.getDataManager().getBuilder(LocatableSnapshot.class);
        if (!dataBuilderOptional.isPresent())
            return null;

        Optional<LocatableSnapshot> locatableSnapshotOptional = dataBuilderOptional.get().build(dv);
        if (!locatableSnapshotOptional.isPresent())
            return null;

        /* It complains about raw types despite the method currently returning Optional<Location<World>>. */
        @SuppressWarnings("unchecked")
        Optional<Location<World>> olw = locatableSnapshotOptional.get().getLocation();
        return olw.orElse(null);

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
    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    @Override
    public void loadFromFiles()
    {
        //noinspection ResultOfMethodCallIgnored
        getStorageDir().mkdirs();//NOSONAR
        //noinspection ConstantConditions
        for (File file : getStorageDir().listFiles())//NOSONAR
        {
            if (file.getName().endsWith(".cfg"))
            {
                Logger logger = SpongeMineTanks.instance().getLogger();
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

                    if (!field.getNode(CommonConfig.REGION).isVirtual())//NOSONAR
                        region = new SpongeRegion(field.getNode(CommonConfig.REGION));

                    if (!field.getNode(CommonConfig.GREEN_SPAWN).isVirtual())//NOSONAR
                        greenSpawn = deserializeLocation(field.getNode(CommonConfig.GREEN_SPAWN));

                    if (!field.getNode(CommonConfig.RED_SPAWN).isVirtual())//NOSONAR
                        redSpawn = deserializeLocation(field.getNode(CommonConfig.RED_SPAWN));

                    if (!field.getNode(CommonConfig.SPECTATORS).isVirtual())//NOSONAR
                        spectators = deserializeLocation(field.getNode(CommonConfig.SPECTATORS));

                    if (!createField(name, enabled, region, greenSpawn, redSpawn, spectators))//NOSONAR
                        logger.warn(CommonMessages.fileLoadFailed(file));
                }
                catch (IOException e)//NOSONAR
                {
                    logger.warn(CommonMessages.fileLoadFailed(file));
                }
            }
        }
    }

    @Override
    public boolean removeField(String field)
    {
        for (String name : fields.keySet())//NOSONAR
        {
            if (name.equalsIgnoreCase(field))
            {
                fields.remove(name);
                return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(name))).delete();
            }
        }

        return false;
    }

    @Override
    public void saveToFiles()
    {
        for (String name : fields.keySet())//NOSONAR
            fields.get(name).saveToFile(getStorageDir());
    }
}
