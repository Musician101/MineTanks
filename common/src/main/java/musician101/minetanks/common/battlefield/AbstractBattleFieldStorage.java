package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.AbstractStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleFieldStorage extends AbstractStorage
{
    Map<String, AbstractBattleField> fields = new HashMap<>();

    public AbstractBattleFieldStorage(File file)
    {
        super(file);
    }

    public abstract boolean canPlayerExit(UUID player);

    public abstract boolean createField(String name);

    public abstract boolean removeField(String field);

    public Map<String, AbstractBattleField> getFields()
    {
        return fields;
    }

    public abstract void loadFromFiles();

    public abstract void saveToFiles();
}
