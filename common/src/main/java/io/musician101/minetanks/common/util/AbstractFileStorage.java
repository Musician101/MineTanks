package io.musician101.minetanks.common.util;

import java.io.File;

public abstract class AbstractFileStorage
{
    private final File storageDir;

    protected AbstractFileStorage(File storageDir)
    {
        this.storageDir = storageDir;
        storageDir.mkdirs();
    }

    protected File getStorageDir()
    {
        return storageDir;
    }
}
