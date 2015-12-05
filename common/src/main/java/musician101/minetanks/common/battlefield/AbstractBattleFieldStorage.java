package musician101.minetanks.common.battlefield;

import musician101.minetanks.common.util.AbstractStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractBattleFieldStorage extends AbstractStorage
{
    public AbstractBattleFieldStorage(File file)
    {
        super(file);
    }

    public abstract boolean canPlayerExit(UUID player);

    public abstract boolean createField(String name);

    public abstract boolean removeField(String field);

    public abstract Map<String, ? extends AbstractBattleField> getFields();

    public abstract void loadFromFiles();

    public abstract void saveToFiles();
}
