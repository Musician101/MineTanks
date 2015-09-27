package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.util.SpigotRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class BattleFieldStorage extends AbstractBattleFieldStorage
{
    private final MineTanks plugin;

    public BattleFieldStorage(MineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), "battlefields"));
        this.plugin = plugin;
        loadFromFiles();
    }

    @Override
    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    private boolean createField(String name, boolean enabled, SpigotRegion spigotRegion, Location greenSpawn, Location redSpawn, Location spectators)
    {
        for (String field : getFields().keySet())
            if (field.equals(name))
                return false;

        BattleField field = new BattleField(plugin, name, enabled, spigotRegion, greenSpawn, redSpawn, spectators);
        getFields().put(name, field);
        return true;
    }

    @Override
    public boolean removeField(String field)
    {
        if (!getFields().containsKey(field))
            return false;

        getFields().remove(field);
        return new File(getStorageDir(), field + ".yml").delete();
    }

    public BattleField getField(String name)
    {
        for (String field : getFields().keySet())
            if (field.equalsIgnoreCase(name))
                return (BattleField) getFields().get(name);

        return null;
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
                boolean enabled = field.getBoolean("enabled");
                SpigotRegion spigotRegion = null;
                Location greenSpawn = null;
                Location redSpawn = null;
                Location spectators = null;

                if (field.isSet("region"))
                    spigotRegion = new SpigotRegion(field.getConfigurationSection("region").getValues(false));

                if (field.isSet("greenSpawn.x"))
                    greenSpawn = new Location(Bukkit.getWorld(field.getString("greenSpawn.world")), field.getInt("greenSpawn.x"), field.getInt("greenSpawn.y"), field.getInt("greenSpawn.z"));

                if (field.isSet("redSpawn.x"))
                    redSpawn = new Location(Bukkit.getWorld(field.getString("redSpawn.world")), field.getInt("redSpawn.x"), field.getInt("redSpawn.y"), field.getInt("redSpawn.z"));

                if (field.isSet("spectators.x"))
                    spectators = new Location(Bukkit.getWorld(field.getString("spectators.world")), field.getInt("spectators.x"), field.getInt("spectators.y"), field.getInt("spectators.z"));

                if (!createField(name, enabled, spigotRegion, greenSpawn, redSpawn, spectators))
                    plugin.getLogger().warning("Failed to load " + file.getName());
            }
        }
    }

    @Override
    public void saveToFiles()
    {
        for (String name : getFields().keySet())
            getFields().get(name).saveToFile(getStorageDir());
    }

    @Override
    public boolean canPlayerExit(UUID player)
    {
        for (String name : getFields().keySet())
        {
            BattleField field = getField(name);
            if (field.getPlayers().containsKey(player))
                return field.canPlayerExit(player);
        }

        return false;
    }
}
