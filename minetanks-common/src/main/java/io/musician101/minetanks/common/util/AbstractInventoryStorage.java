package io.musician101.minetanks.common.util;

import java.io.File;
import java.util.UUID;


public abstract class AbstractInventoryStorage<P> extends AbstractFileStorage
{
    protected AbstractInventoryStorage(File storageDir)
    {
        super(storageDir);
    }


    public abstract void load(P playerId);


    public abstract boolean save(P playerId);

    public File getPlayerFile(UUID uuid)
    {
        return new File(getStorageDir(), uuid.toString() + ".cfg");
    }
}
