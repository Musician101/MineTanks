package musician101.minetanks.common.util;

import java.io.File;

public abstract class AbstractStorage
{
    private final File storageDir;

    protected AbstractStorage(File storageDir)
    {
        this.storageDir = storageDir;
        storageDir.mkdirs();
    }

    protected File getStorageDir()
    {
        return storageDir;
    }
}
