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
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class SpongeBattleFieldStorage extends AbstractBattleFieldStorage<SpongeMineTanks, SpongeBattleField>
{
    public SpongeBattleFieldStorage(SpongeMineTanks plugin, File configDir)
    {
        super(plugin, new File(configDir, CommonStorage.BATTLEFIELDS));
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

        SpongeBattleField field = new SpongeBattleField(plugin, name, enabled, region, greenSpawn, redSpawn, spectators);
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
                return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(name))).delete();
            }
        }

        return false;
    }

    @Override
    public void loadFromFiles()
    {
        getStorageDir().mkdirs();
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".cfg"))
            {
                Logger logger = plugin.getLogger();
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
        Optional<DataBuilder<LocatableSnapshot>> dataBuilderOptional = Sponge.getDataManager().getBuilder(LocatableSnapshot.class);
        if (!dataBuilderOptional.isPresent())
            return null;

        Optional<LocatableSnapshot> locatableSnapshotOptional = dataBuilderOptional.get().build(dv);
        if (!locatableSnapshotOptional.isPresent())
            return null;

        /** It complains about raw types despite the method currently returning Optional<Location<World>>. */
        @SuppressWarnings("unchecked")
        Optional<Location<World>> olw = locatableSnapshotOptional.get().getLocation();
        if (!olw.isPresent())
            return null;

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
