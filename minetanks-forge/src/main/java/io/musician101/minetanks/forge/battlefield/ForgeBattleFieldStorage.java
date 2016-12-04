package io.musician101.minetanks.forge.battlefield;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonStorage;
import io.musician101.minetanks.common.battlefield.AbstractBattleFieldStorage;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.util.ForgeRegion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ForgeBattleFieldStorage extends AbstractBattleFieldStorage<ForgeBattleField>
{
    public ForgeBattleFieldStorage(File configDir)
    {
        super(new File(configDir, CommonStorage.BATTLEFIELDS));
        loadFromFiles();
    }

    @SideOnly(Side.SERVER)
    @Override
    public boolean canPlayerExit(UUID player)
    {
        for (String name : fields.keySet())
        {
            ForgeBattleField field = getField(name);
            if (field.getPlayers().containsKey(player))
                return field.canPlayerExit(player);
        }

        return false;
    }

    @SideOnly(Side.SERVER)
    @Override
    public boolean createField(String name)
    {
        return createField(name, false, null, null, null, null);
    }

    @SideOnly(Side.SERVER)
    private boolean createField(String name, boolean enabled, ForgeRegion forgeRegion, BlockPos greenSpawn, BlockPos redSpawn, BlockPos spectators)
    {
        for (String field : fields.keySet())
            if (field.equals(name))
                return false;

        fields.put(name, new ForgeBattleField(name, enabled, forgeRegion, greenSpawn, redSpawn, spectators, forgeRegion.getWorld().getMinecraftServer()));
        return true;
    }

    @Override
    public void loadFromFiles()
    {
        //noinspection ResultOfMethodCallIgnored
        getStorageDir().mkdirs();
        //noinspection ConstantConditions
        for (File file : getStorageDir().listFiles())
        {
            if (file.getName().endsWith(".cfg"))
            {
                NBTTagCompound field;
                try
                {
                    field = JsonToNBT.getTagFromJson(String.join("\n", Files.readAllLines(file.toPath())));
                }
                catch (IOException | NBTException exception)//NOSONAR
                {
                    ForgeMineTanks.instance.getLogger().warn(CommonMessages.fileLoadFailed(file));
                    continue;
                }

                String name = file.getName().replace(".cfg", "");
                boolean enabled = field.getBoolean(CommonConfig.ENABLED);
                ForgeRegion forgeRegion = null;
                BlockPos greenSpawn = null;
                BlockPos redSpawn = null;
                BlockPos spectators = null;

                if (field.hasKey(CommonConfig.REGION))
                    forgeRegion = new ForgeRegion(field.getCompoundTag(CommonConfig.REGION));

                if (field.hasKey(CommonConfig.GREEN_SPAWN))
                    greenSpawn = deserializeBlockPos((NBTTagCompound) field.getTag(CommonConfig.GREEN_SPAWN));

                if (field.hasKey(CommonConfig.RED_SPAWN))
                    redSpawn = deserializeBlockPos((NBTTagCompound) field.getTag(CommonConfig.RED_SPAWN));

                if (field.hasKey(CommonConfig.SPECTATORS))
                    spectators = deserializeBlockPos((NBTTagCompound) field.getTag(CommonConfig.SPECTATORS));

                if (!createField(name, enabled, forgeRegion, greenSpawn, redSpawn, spectators))
                    ForgeMineTanks.instance.getLogger().warn(CommonMessages.fileLoadFailed(file));
            }
        }
    }

    private BlockPos deserializeBlockPos(NBTTagCompound nbt)
    {
        int x = nbt.hasKey(CommonConfig.X) ? nbt.getInteger(CommonConfig.X) : 0;
        int y = nbt.hasKey(CommonConfig.X) ? nbt.getInteger(CommonConfig.X) : 0;
        int z = nbt.hasKey(CommonConfig.X) ? nbt.getInteger(CommonConfig.X) : 0;
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean removeField(String field)
    {
        if (!fields.containsKey(field))
            return false;

        fields.remove(field);
        return new File(getStorageDir(), CommonConfig.battlefieldFile(fields.get(field))).delete();
    }

    @Override
    public void saveToFiles()
    {
        for (ForgeBattleField field : fields.values())
            field.saveToFile(getStorageDir());
    }
}
