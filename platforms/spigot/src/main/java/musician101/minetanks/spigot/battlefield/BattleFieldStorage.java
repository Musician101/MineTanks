package musician101.minetanks.spigot.battlefield;

import musician101.minetanks.common.AbstractStorage;
import musician101.minetanks.spigot.MineTanks;
import musician101.minetanks.spigot.util.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BattleFieldStorage extends AbstractStorage
{
    MineTanks plugin;
    Map<String, BattleField> fields = new HashMap<>();

    public BattleFieldStorage(MineTanks plugin)
    {
        super(new File(plugin.getDataFolder(), "battlefields"));
        this.plugin = plugin;
        loadFromFiles();
    }

    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    public boolean createField(String name, boolean enabled, Cuboid cuboid, Location greenSpawn, Location redSpawn, Location spectators)
    {
        for (String field : fields.keySet())
            if (field.equals(name))
                return false;

        BattleField field = new BattleField(plugin, name, enabled, cuboid, greenSpawn, redSpawn, spectators);
        fields.put(name, field);
        return true;
    }

    public boolean removeField(String field)
    {
        if (!fields.containsKey(field))
            return false;

        fields.remove(field);
        return new File(getStorageDir(), field + ".yml").delete();
    }

    public BattleField getField(String name)
    {
        for (String field : fields.keySet())
            if (field.equalsIgnoreCase(name))
                return fields.get(name);

        return null;
    }

    public Map<String, BattleField> getFields()
    {
        return fields;
    }

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
                Cuboid cuboid = null;
                Location greenSpawn = null;
                Location redSpawn = null;
                Location spectators = null;

                if (field.isSet("cuboid"))
                    cuboid = Cuboid.deserialize(field.getConfigurationSection("cuboid").getValues(false));

                if (field.isSet("greenSpawn.x"))
                    greenSpawn = new Location(Bukkit.getWorld(field.getString("greenSpawn.world")), field.getInt("greenSpawn.x"), field.getInt("greenSpawn.y"), field.getInt("greenSpawn.z"));

                if (field.isSet("redSpawn.x"))
                    redSpawn = new Location(Bukkit.getWorld(field.getString("redSpawn.world")), field.getInt("redSpawn.x"), field.getInt("redSpawn.y"), field.getInt("redSpawn.z"));

                if (field.isSet("spectators.x"))
                    spectators = new Location(Bukkit.getWorld(field.getString("spectators.world")), field.getInt("spectators.x"), field.getInt("spectators.y"), field.getInt("spectators.z"));

                if (!createField(name, enabled, cuboid, greenSpawn, redSpawn, spectators))
                    plugin.getLogger().warning("Failed to load " + file.getName());
            }
        }
    }

    public void saveToFiles()
    {
        for (String name : fields.keySet())
            fields.get(name).saveToFile(getStorageDir());
    }

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
}