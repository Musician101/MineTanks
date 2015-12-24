package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.util.AbstractStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleFieldStorage<Battlefield extends AbstractBattleField> extends AbstractStorage
{
    protected final Map<String, Battlefield> fields = new HashMap<>();

    protected AbstractBattleFieldStorage(File file)
    {
        super(file);
    }

    public Battlefield getField(String fieldName)
    {
        for (String field : fields.keySet())
            if (field.equalsIgnoreCase(fieldName))
                return fields.get(field);

        return null;
    }

    public Map<String, Battlefield> getFields()
    {
        return fields;
    }

    public abstract boolean canPlayerExit(UUID player);

    public abstract boolean createField(String name);

    public abstract boolean removeField(String field);

    public abstract void loadFromFiles();

    public abstract void saveToFiles();
}
