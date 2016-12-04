package io.musician101.minetanks.common.battlefield;

import io.musician101.minetanks.common.util.AbstractFileStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;


public abstract class AbstractBattleFieldStorage<B extends AbstractBattleField> extends AbstractFileStorage
{
    protected final Map<String, B> fields = new HashMap<>();

    protected AbstractBattleFieldStorage(File file)
    {
        super(file);
    }

    public B getField(String fieldName)
    {
        for (Entry<String, B> entry : fields.entrySet())
            if (entry.getKey().equalsIgnoreCase(fieldName))
                return entry.getValue();

        return null;
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
