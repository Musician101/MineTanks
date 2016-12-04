package io.musician101.minetanks.forge;

import io.musician101.minetanks.common.CommonReference;
import io.musician101.minetanks.forge.battlefield.ForgeBattleFieldStorage;
import io.musician101.minetanks.forge.command.MTForgeCommand;
import io.musician101.minetanks.forge.gui.MTGuiHandler;
import io.musician101.minetanks.forge.util.ForgeInventoryStorage;
import java.io.File;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = CommonReference.ID, name = CommonReference.NAME, version = CommonReference.VERSION)
public class ForgeMineTanks
{
    private File configDir;
    @Instance(value = CommonReference.ID)
    public static ForgeMineTanks instance;//NOSONAR
    private Logger logger;
    private ForgeBattleFieldStorage fieldStorage;
    private ForgeInventoryStorage inventoryStorage;

    public ForgeBattleFieldStorage getFieldStorage()
    {
        return fieldStorage;
    }

    public ForgeInventoryStorage getInventoryStorage()
    {
        return inventoryStorage;
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event)
    {
        logger.info(CommonReference.ATTENTION);
        fieldStorage = new ForgeBattleFieldStorage(configDir);
        fieldStorage.loadFromFiles();
        inventoryStorage = new ForgeInventoryStorage(configDir);
        event.registerServerCommand(new MTForgeCommand());
        logger.info(CommonReference.MOVIN_ON_OUT);
    }

    @EventHandler
    public void onServerStop(FMLServerStoppingEvent event)
    {
        fieldStorage.saveToFiles();
        logger.info(CommonReference.PACK_IT_UP);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;//NOSONAR
        logger = event.getModLog();
        configDir = new File(event.getModConfigurationDirectory(), CommonReference.ID);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //TODO need to figure out GUI
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new MTGuiHandler());
    }

    public Logger getLogger()
    {
        return logger;
    }
}
