package musician101.minetanks.common.util;

import java.io.File;
import java.util.UUID;

public abstract class AbstractInventoryStorage extends AbstractStorage
{
    protected AbstractInventoryStorage(File storageDir)
    {
        super(storageDir);
    }

    public abstract void load(UUID playerId);

    public abstract boolean save(UUID playerId);

    public File getPlayerFile(UUID playerId)
    {
        return new File(getStorageDir(), playerId.toString() + ".cfg");
    }
}
