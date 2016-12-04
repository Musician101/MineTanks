package io.musician101.minetanks.forge.util;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.CommonReference.CommonStorage;
import io.musician101.minetanks.common.util.AbstractInventoryStorage;
import io.musician101.musicianlibrary.java.minecraft.forge.TextComponentUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.common.util.Constants.NBT;

public class ForgeInventoryStorage extends AbstractInventoryStorage<EntityPlayerMP>
{
    public ForgeInventoryStorage(File configDir)
    {
        super(new File(configDir, CommonStorage.INVENTORY));
    }

    @Override
    public void load(EntityPlayerMP player)//NOSONAR
    {
        File file = getPlayerFile(player.getUniqueID());
        if (file.exists())
        {
            player.inventory.armorInventory[0] = null;
            player.inventory.armorInventory[0] = null;
            player.inventory.armorInventory[0] = null;
            player.inventory.armorInventory[0] = null;
            player.inventory.clear();
            //noinspection ConstantConditions
            player.removePotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("speed")));
            //noinspection ConstantConditions
            player.removePotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("slowness")));

            NBTTagCompound data;
            try
            {
                data = JsonToNBT.getTagFromJson(String.join("\n", Files.readAllLines(file.toPath())));
            }
            catch (IOException | NBTException e)//NOSONAR
            {
                player.sendMessage(TextComponentUtils.redText(CommonMessages.fileLoadFailed(file)));
                return;
            }

            String invKey = CommonConfig.INVENTORY.replace(".", "");
            if (data.hasKey(invKey))
                player.inventory.readFromNBT(data.getTagList(invKey, NBT.TAG_COMPOUND));

            if (data.hasKey(CommonConfig.EFFECTS))
            {
                NBTTagList effects = data.getTagList(CommonConfig.EFFECTS, NBT.TAG_COMPOUND);
                for (int i = 0; i < effects.tagCount(); i++)
                    player.addPotionEffect(PotionEffect.readCustomPotionEffectFromNBT(effects.getCompoundTagAt(i)));
            }

            if (data.hasKey(CommonConfig.LOCATION))
            {
                NBTTagCompound location = data.getCompoundTag(CommonConfig.LOCATION);
                int world = player.dimension;
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                int z = player.getPosition().getZ();
                if (location.hasKey(CommonConfig.X))
                    x = location.getInteger(CommonConfig.X);

                if (location.hasKey(CommonConfig.Y))
                    y = location.getInteger(CommonConfig.Y);

                if (location.hasKey(CommonConfig.Z))
                    y = location.getInteger(CommonConfig.Z);

                if (location.hasKey(CommonConfig.WORLD))
                    world = location.getInteger(CommonConfig.WORLD);

                player.changeDimension(world);
                player.setPositionAndUpdate(x, y, z);
            }

            if (data.hasKey(CommonConfig.XP))
                player.addExperience(data.getInteger(CommonConfig.XP));

            if (data.hasKey(CommonConfig.GAMEMODE))
                player.setGameType(GameType.parseGameTypeWithDefault(data.getString(CommonConfig.GAMEMODE), player.getServerWorld().getWorldInfo().getGameType()));

            //noinspection ResultOfMethodCallIgnored
            file.delete();//NOSONAR
        }
    }

    @Override
    public boolean save(EntityPlayerMP player)
    {
        File file = getPlayerFile(player.getUniqueID());
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();//NOSONAR
        }
        catch (IOException e)//NOSONAR
        {
            player.sendMessage(TextComponentUtils.redText(CommonMessages.fileSaveFailed(file)));
            return false;
        }

        NBTTagCompound data = new NBTTagCompound();
        InventoryPlayer inv = player.inventory;
        data.setTag(CommonConfig.INVENTORY.replace(".", ""), inv.writeToNBT(new NBTTagList()));

        NBTTagList effects = new NBTTagList();
        player.getActivePotionEffects().forEach(effect -> effects.appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound())));
        data.setTag(CommonConfig.EFFECTS, effects);

        NBTTagCompound location = new NBTTagCompound();
        location.setInteger(CommonConfig.X, player.getPosition().getX());
        location.setInteger(CommonConfig.Y, player.getPosition().getY());
        location.setInteger(CommonConfig.Z, player.getPosition().getZ());
        location.setInteger(CommonConfig.WORLD, player.dimension);
        data.setTag(CommonConfig.LOCATION, location);
        data.setInteger(CommonConfig.XP, player.experienceTotal);

        try(FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(data.toString().getBytes());
        }
        catch (IOException e)//NOSONAR
        {
            player.sendMessage(TextComponentUtils.redText(CommonMessages.fileSaveFailed(file)));
            //noinspection ResultOfMethodCallIgnored
            file.delete();//NOSONAR
            return false;
        }

        player.removeExperienceLevel(player.experienceLevel + 1);
        player.inventory.clear();
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getPotion());

        return true;
    }
}
