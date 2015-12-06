package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.util.AbstractStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleFieldStorage<B extends AbstractBattleField> extends AbstractStorage
{
    protected Map<String, B> fields = new HashMap<>();

    public AbstractBattleFieldStorage(File file)
    {
        super(file);
    }

    public B getField(String fieldName)
    {
        return fields.get(fieldName);
    }

    public Map<String, B> getFields()
    {
        return fields;
    }

    public abstract boolean canPlayerExit(UUID player);

    public abstract boolean createField(String name);

    public abstract boolean removeField(String field);

    public abstract void loadFromFiles();

    public abstract void saveToFiles();
}
