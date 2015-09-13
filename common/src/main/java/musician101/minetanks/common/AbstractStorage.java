package musician101.minetanks.common;

import java.io.File;

public abstract class AbstractStorage
{
    File storageDir;

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
