package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.CommonReference.CommonConfig;
import musician101.minetanks.common.CommonReference.CommonMessages;
import musician101.minetanks.common.CommonReference.CommonStorage;
import musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class BattleFieldStorage extends AbstractBattleFieldStorage<BattleField>
{
    private final MineTanks plugin;

    public BattleFieldStorage(MineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), CommonStorage.BATTLEFIELDS));
        this.plugin = plugin;
        loadFromFiles();
    }

    @Override
    public boolean canPlayerExit(UUID player)
    {
        for (String name : fields.keySet())
        {
            BattleField field = getField(name);
            if (field.getPlayers().containsKey(player))
                return field.canPlayerExit(player);
        }

        return false;
    }

    @Override
    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    private boolean createField(String name, boolean enabled, SpigotRegion spigotRegion, Location greenSpawn, Location redSpawn, Location spectators)
    {
        for (String field : fields.keySet())
            if (field.equals(name))
                return false;

        fields.put(name, new BattleField(plugin, name, enabled, spigotRegion, greenSpawn, redSpawn, spectators));
        return true;
    }

    @Override
    public boolean removeField(String field)
    {
        if (!fields.containsKey(field))
            return false;

        fields.remove(field);
        return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(field), "yml")).delete();
    }

    @Override
    public void loadFromFiles()
    {
        getStorageDir().mkdirs();
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".yml"))
            {
                YamlConfiguration field = YamlConfiguration.loadConfiguration(file);
                String name = file.getName().replace(".yml", "");
                boolean enabled = field.getBoolean(CommonConfig.ENABLED);
                SpigotRegion spigotRegion = null;
                Location greenSpawn = null;
                Location redSpawn = null;
                Location spectators = null;

                if (field.isSet(CommonConfig.REGION))
                    spigotRegion = new SpigotRegion(field.getConfigurationSection(CommonConfig.REGION).getValues(true));

                if (field.isSet(CommonConfig.GREEN_SPAWN))
                    greenSpawn = Location.deserialize(field.getConfigurationSection(CommonConfig.GREEN_SPAWN).getValues(true));

                if (field.isSet(CommonConfig.RED_SPAWN))
                    redSpawn = Location.deserialize(field.getConfigurationSection(CommonConfig.RED_SPAWN).getValues(true));

                if (field.isSet(CommonConfig.SPECTATORS))
                    spectators = Location.deserialize(field.getConfigurationSection(CommonConfig.SPECTATORS).getValues(true));

                if (!createField(name, enabled, spigotRegion, greenSpawn, redSpawn, spectators))
                    plugin.getLogger().warning(CommonMessages.fileLoadFailed(file));
            }
        }
    }

    @Override
    public void saveToFiles()
    {
        for (String name : fields.keySet())
            fields.get(name).saveToFile(getStorageDir());
    }
}
