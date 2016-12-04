package io.musician101.minetanks.forge.battlefield;

import io.musician101.minetanks.common.CommonReference.CommonConfig;
import io.musician101.minetanks.common.CommonReference.CommonItemText;
import io.musician101.minetanks.common.CommonReference.CommonMessages;
import io.musician101.minetanks.common.battlefield.AbstractBattleField;
import io.musician101.minetanks.common.battlefield.player.AbstractPlayerTank.MTTeam;
import io.musician101.minetanks.forge.ForgeMineTanks;
import io.musician101.minetanks.forge.battlefield.player.ForgePlayerTank;
import io.musician101.minetanks.forge.menu.ForgeMainSelectionMenu;
import io.musician101.minetanks.forge.scoreboard.ForgeMTScoreboard;
import io.musician101.minetanks.forge.tank.ForgeTank;
import io.musician101.minetanks.forge.tank.modules.cannon.ForgeAutoLoader;
import io.musician101.minetanks.forge.util.ForgeInventoryStorage;
import io.musician101.minetanks.forge.util.ForgeRegion;
import io.musician101.minetanks.forge.util.MTUtils;
import io.musician101.musicianlibrary.java.minecraft.forge.TextComponentUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.SERVER)
public class ForgeBattleField extends AbstractBattleField<EntityPlayerMP, ForgePlayerTank, ForgeRegion, ForgeMTScoreboard, Item, BlockPos, BreakEvent, PlaceEvent, PlayerInteractEvent, ItemTossEvent, PlayerLoggedInEvent, PlayerLoggedOutEvent, LivingUpdateEvent, Object, ArrowLooseEvent, LivingHurtEvent, Object, Detonate>
{
    private final List<UUID> arrows = new ArrayList<>();
    private final MinecraftServer server;

    public ForgeBattleField(String name, boolean enabled, ForgeRegion region, BlockPos greenSpawn, BlockPos redSpawn, BlockPos spectators, MinecraftServer server)
    {
        super(name, enabled, region, greenSpawn, redSpawn, spectators, new ForgeMTScoreboard(server));
        this.server = server;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean addPlayer(EntityPlayerMP player, MTTeam team)
    {
        UUID uuid = player.getUniqueID();
        if (!players.containsKey(uuid) && !ForgeMineTanks.instance.getInventoryStorage().save(player))
            return false;

        if (team == MTTeam.SPECTATOR)
            player.sendMessage(TextComponentUtils.greenText(CommonMessages.fieldSpectating(this)));
        else
        {
            player.inventory.setInventorySlotContents(0, MTUtils.createCustomItem(Items.STICK, CommonItemText.OPEN_HANGAR, CommonItemText.selectedTank(null)));
            player.inventory.setInventorySlotContents(1, MTUtils.createCustomItem(Items.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            unassigned++;
        }

        player.setPositionAndUpdate(getSpectators().getX(), getSpectators().getY(), getSpectators().getZ());
        player.setGameType(GameType.SURVIVAL);
        players.put(player.getUniqueID(), new ForgePlayerTank(player.getUniqueID(), team, player.getServer()));
        return true;
    }

    @Override
    protected void arrowIsDamager(UUID damager, double damage, EntityPlayerMP damaged)
    {
        EntityArrow arrow = (EntityArrow) getRegion().getWorld().getEntityFromUuid(damager);
        if (arrow == null)
            return;

        if (arrow.shootingEntity instanceof EntityPlayerMP && players.get(arrow.shootingEntity.getUniqueID()) != null)
        {
            playerHit((EntityPlayerMP) arrow.shootingEntity, damaged, damage);
            arrows.add(arrow.getUniqueID());
        }
    }

    @SubscribeEvent
    public void test(ServerTickEvent event)
    {
        //TODO left off here
        //TODO need to check if arrow leaves the arena, Forge is gonna have to be a bit round about
        WorldServer world = getRegion().getWorld();
        List<Entity> entities = arrows.stream().filter(uuid ->
        {
            Entity entity = world.getEntityFromUuid(uuid);
            return entity != null && getRegion().isInRegion(entity.getPosition(), entity.dimension);
        }).map(world::getEntityFromUuid).collect(Collectors.toList());

        if (entities.isEmpty())
            return;

        Entity entity = entities.get(0);
        arrows.remove(entity.getUniqueID());
        entity.setDead();
    }

    @Override
    protected boolean assignTeams(List<UUID> playersList)
    {
        Collections.shuffle(playersList);
        for (UUID uuid : playersList)
        {
            ForgePlayerTank pt = players.get(uuid);
            if (getScoreboard().getGreenTeamSize() == 0 && getScoreboard().getRedTeamSize() == 0 && unassigned < 2)
                return false;

            EntityPlayerMP player = (EntityPlayerMP) getRegion().getWorld().getPlayerEntityByUUID(uuid);
            if (getScoreboard().getGreenTeamSize() == getScoreboard().getRedTeamSize() && unassigned == 1)
            {
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTeam(null);
            }
            else if (getScoreboard().getGreenTeamSize() <= getScoreboard().getRedTeamSize())
            {
                getScoreboard().addGreenPlayer(player);
                pt.setTeam(MTTeam.ASSIGNED);
                pt.getTank().applySpeedEffect(player);
            }
            else if (getScoreboard().getGreenTeamSize() >= getScoreboard().getRedTeamSize())
            {
                pt.setTeam(MTTeam.ASSIGNED);
                getScoreboard().addRedPlayer(player);
                pt.getTank().applySpeedEffect(player);
            }

            unassigned--;
        }

        return true;
    }

    @Override
    protected void blockExplosion(UUID damager, double damage, EntityPlayerMP damaged)
    {
        //noinspection ConstantConditions
        playerHit(damaged, (EntityPlayerMP) ((EntityArrow) damaged.getServerWorld().getEntityFromUuid(damager)).shootingEntity, damage);
        arrows.remove(damager);
    }

    @Override
    public void endMatch()
    {
        endMatch(false);
    }

    @Override
    public void endMatch(boolean forced)
    {
        if (!inProgress())
            return;

        if (forced || getScoreboard().getGreenTeamSize() == 0 || getScoreboard().getRedTeamSize() == 0)
        {
            setInProgress(false);
            for (Entry<UUID, ForgePlayerTank> entry : players.entrySet())
            {
                EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(entry.getKey());
                player.inventory.clear();
                //noinspection ConstantConditions
                player.removePotionEffect(Potion.getPotionFromResourceLocation("slowness"));
                //noinspection ConstantConditions
                player.removePotionEffect(Potion.getPotionFromResourceLocation("speed"));
                ForgePlayerTank pt = entry.getValue();
                player.setPositionAndUpdate(getSpectators().getX(), getSpectators().getY(), getSpectators().getZ());
                pt.setTeam(MTTeam.SPECTATOR);
                pt.setTank(null);
                getScoreboard().playerDeath(player);
                if (forced)
                    player.sendMessage(TextComponentUtils.goldText(CommonMessages.MATCH_FORCE_ENDED));
                else if (getScoreboard().getGreenTeamSize() == 0)
                    player.sendMessage(TextComponentUtils.redText(CommonMessages.RED_WINS));
                else if (getScoreboard().getRedTeamSize() == 0)
                    player.sendMessage(TextComponentUtils.greenText(CommonMessages.GREEN_WINS));
            }
        }
    }

    @Override
    protected void gravityHit(EntityPlayerMP player, double damage)
    {
        double dmg = damage * 5;
        ForgeMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(player, (int) (sb.getPlayerHealth(player) - dmg));
        //TODO need to create a new message and custom code for accidental/purposeful suicide
        getPlayers().keySet().stream().filter(uuid -> sb.getPlayerHealth(player) <= 0).forEach(uuid -> server.getPlayerList().getPlayerByUUID(uuid).sendMessage(new TextComponentString("need a message here")));
    }

    @Override
    protected boolean handleWatch(EntityPlayerMP player)
    {
        ForgePlayerTank pt = players.get(player.getUniqueID());
        if (pt.getTank() == null)
        {
            //TODO need to make this a CommonMessage
            player.sendMessage(TextComponentUtils.redText(CommonMessages.PREFIX + "You cannot ready until you have selected a tank."));
            return false;
        }
        else if (pt.isReady())
        {
            pt.setReady(false);
            player.inventory.setInventorySlotContents(1, MTUtils.createCustomItem(Items.CLOCK, CommonItemText.READY_UP, CommonItemText.NOT_READY));
            return true;
        }

        pt.setReady(true);
        player.inventory.setInventorySlotContents(1, MTUtils.createCustomItem(Items.CLOCK, CommonItemText.UNREADY, CommonItemText.READY));
        startMatch();
        return true;
    }

    @Override
    protected boolean isMenuItem(Item item)
    {
        //Stick is the default item if a player hasn't chosen a tank.
        return Arrays.asList(Items.STICK, Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.CLOCK).contains(item);
    }

    @Override
    protected void meleeHit(EntityPlayerMP rammed, EntityPlayerMP rammer, double damage)
    {
        double rammerDmg = damage * players.get(rammer.getUniqueID()).getTank().getType().getRamModifier();
        double rammedDmg = damage * players.get(rammed.getUniqueID()).getTank().getType().getRamModifier();
        if (rammerDmg > 0)
            playerHit(rammed, rammer, rammerDmg);

        if (rammedDmg > 0)
            playerHit(rammer, rammed, rammedDmg);
    }

    @Override
    @SubscribeEvent
    public void onBlockBreak(BreakEvent event)
    {
        EntityPlayer player = event.getPlayer();
        if (!getRegion().isInRegion(player.getPosition(), player.dimension))
            return;

        if (hasPermission(player) && !isEnabled())
            return;

        event.setCanceled(true);
    }

    @Override
    @SubscribeEvent
    public void onBlockExplode(Detonate event)
    {
        if (event.isCanceled())
            return;

        List<Integer> ids = Arrays.stream(DimensionManager.getIDs()).filter(id -> DimensionManager.getWorld(id).equals(event.getWorld())).collect(Collectors.toList());
        if (ids.isEmpty())
            return;

        if (getRegion().isInRegion(new BlockPos(event.getExplosion().getPosition()), ids.get(0)))
            event.getAffectedBlocks().clear();
    }

    @Override
    @SubscribeEvent
    public void onBlockInteract(PlayerInteractEvent event)
    {
        if (event.getItemStack() == null)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!players.containsKey(player.getUniqueID()))
            return;

        Item item = event.getItemStack().getItem();
        if (!isMenuItem(item) && inProgress())
            return;

        if (!processBlockInteract((EntityPlayerMP) player))
            return;

        event.setCanceled(true);
    }

    @Override
    @SubscribeEvent
    public void onBlockPlace(PlaceEvent event)
    {
        EntityPlayer player = event.getPlayer();
        if (!getRegion().isInRegion(player.getPosition(), player.dimension))
            return;

        if (hasPermission(player) && !isEnabled())
            return;

        event.setCanceled(true);
    }

    @Override
    @SubscribeEvent
    public void onBowShoot(ArrowLooseEvent event)//NOSONAR
    {
        if (!(event.getEntity() instanceof EntityPlayerMP))
            return;

        EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
        if (!players.containsKey(player.getUniqueID()))
            return;

        ForgePlayerTank pt = players.get(player.getUniqueID());
        if (pt.getTeam() == MTTeam.SPECTATOR)
            return;

        ForgeTank tank = pt.getTank();
        event.setCanceled(pt.isReloading());
        if (event.isCanceled())
            return;

        if (tank.getCannon() instanceof ForgeAutoLoader)
        {
            ItemStack itemStack = event.getBow();
            ForgeAutoLoader cannon = (ForgeAutoLoader) tank.getCannon();
            if (pt.getClipSize() < 1)
                pt.setClipSize(cannon.getClipSize());
            else
                pt.setClipSize(pt.getClipSize() - 1);

            //TODO add utility method for adding lore
            NBTTagCompound display = new NBTTagCompound();
            NBTTagList lore = new NBTTagList();
            lore.appendTag(new NBTTagString(CommonItemText.CANNON));
            lore.appendTag(new NBTTagString(CommonItemText.clipSize(pt.getClipSize(), cannon.getClipSize())));
            lore.appendTag(new NBTTagString(CommonItemText.cycleTime(cannon.getCycleTime())));
            lore.appendTag(new NBTTagString(CommonItemText.clipReloadTime(cannon.getReloadTime())));
            display.setTag("Lore", lore);
            itemStack.setTagInfo("display", display);
        }

        int slot = -1;
        ItemStack[] inv = player.inventory.mainInventory;
        for (int i = 0; i < inv.length; i++)
            if (inv[i].getItem() == Items.ARROW)
                slot = i;

        if (slot == -1)
            return;

        ItemStack itemStack = inv[slot];
        itemStack.stackSize--;
    }

    @Override
    @SubscribeEvent
    public void onEntityDamage(LivingHurtEvent event)//NOSONAR
    {
        if (!inProgress())
            return;

        DamageSource source = event.getSource();
        if (!source.isExplosion() || !source.isProjectile() || !source.equals(DamageSource.fall) || source.getEntity() == null)
            return;

        EntityLivingBase entityDamaged = event.getEntityLiving();
        Entity entityDamager = source.getEntity();
        if (!(entityDamaged instanceof EntityPlayerMP))
            return;

        if (!players.containsKey(entityDamaged.getUniqueID()))
            return;

        //TODO using spectate command on a match in progress doesn't assign the scoreboard=
        EntityPlayerMP damaged = (EntityPlayerMP) entityDamaged;
        ForgePlayerTank damagedPT = players.get(event.getEntity().getUniqueID());
        if (damagedPT.getTeam() != MTTeam.ASSIGNED)
            return;

        double damage = event.getAmount();
        if (entityDamager instanceof EntityArrow)
            arrowIsDamager(entityDamager.getUniqueID(), damage, damaged);
        else if (entityDamager instanceof EntityPlayerMP)
            playerIsDamager((EntityPlayerMP) entityDamager, damage, damaged);
        else if (source.equals(DamageSource.fall))
            gravityHit(damaged, damage);
        else if (source.isExplosion())
            blockExplosion(entityDamager.getUniqueID(), damage, (EntityPlayerMP) entityDamaged);

        event.setCanceled(true);
    }

    @Override
    @SubscribeEvent
    public void onItemDrop(ItemTossEvent event)
    {
        event.setCanceled(players.containsKey(event.getPlayer().getUniqueID()));
    }

    @Override
    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event)
    {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        ForgeInventoryStorage invStorage = ForgeMineTanks.instance.getInventoryStorage();
        File file = invStorage.getPlayerFile(player.getUniqueID());
        if (!file.exists())
            return;

        invStorage.load(player);
        player.sendMessage(TextComponentUtils.greenText(CommonMessages.LOGGED_OFF_WITH_ITEMS_STORED));
    }

    @Override
    @SubscribeEvent
    public void onPlayerMove(LivingUpdateEvent event)//NOSONAR
    {
        if (!(event.getEntityLiving() instanceof EntityPlayerMP))
            return;

        EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
        ForgePlayerTank pt = players.get(player.getUniqueID());
        if (pt == null)
            return;

        ForgeRegion region = getRegion();
        if (region.isInRegion(player.getPosition(), player.dimension))
            return;

        player.sendMessage(TextComponentUtils.redText(CommonMessages.OUT_OF_BOUNDS));
        if (!region.getWorld().equals(player.getServerWorld()))
        {
            player.setWorld(region.getWorld());
            BlockPos bp = getSpectators();
            if (getScoreboard().isOnGreen(player))
                bp = getGreenSpawn();
            else if (getScoreboard().isOnRed(player))
                bp = getRedSpawn();

            player.setPositionAndUpdate(bp.getX(), bp.getY(), bp.getZ());
            return;
        }

        int x = 0;
        int z = 0;
        BlockPos position = player.getPosition();
        if (position.getX() > region.getMaxX())
            x = -1;
        if (position.getX() < region.getMinX())
            x = 1;
        if (position.getZ() > region.getMaxZ())
            z = -1;
        if (position.getZ() < region.getMinZ())
            z = 1;

        player.motionX = x;
        player.motionZ = z;
    }

    @Override
    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event)
    {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        UUID uuid = player.getUniqueID();
        if (getPlayers().containsKey(uuid))
            removePlayer(player);
    }

    @Override
    public void onPlayerTeleport(Object event)
    {
        //No teleport event available
    }

    @Override
    public void onProjectileHit(Object event)
    {
        //No Projectile Hit Event available
        /*new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (event.getEntity().getShooter() instanceof EntityPlayerMP && event.getEntity() instanceof Arrow)
                {
                    Arrow arrow = (Arrow) event.getEntity();
                    if (!arrows.containsKey(arrow.getUniqueID()))
                        return;

                    EntityPlayerMP player = (EntityPlayerMP) arrow.getShooter();
                    if (!players.containsKey(player.getUniqueID()))
                        return;

                    arrow.getWorld().createExplosion(arrow.getBlockPos(), 1F);
                    Bukkit.getScheduler().cancelTask(arrows.get(arrow.getUniqueID()));
                    arrow.remove();
                }
            }
        }.runTaskLater(ForgeMineTanks.instance, 1L);*/
    }

    @Override
    protected void playerHit(EntityPlayerMP damaged, EntityPlayerMP damager, double damage)
    {
        ForgeMTScoreboard sb = getScoreboard();
        sb.setPlayerHealth(damaged, (int) (sb.getPlayerHealth(damaged) - (damage * 2) * 20));
        if (sb.getPlayerHealth(damaged) <= 0)
            triggerPlayerDeath(damager, damaged);
    }

    @Override
    protected void playerIsDamager(EntityPlayerMP damager, double damage, EntityPlayerMP damaged)
    {
        if (players.get(damager.getUniqueID()) != null)
            meleeHit(damaged, damager, damage);
    }

    @Override
    public void playerKilled(EntityPlayerMP player)
    {
        players.get(player.getUniqueID()).killed();
        getScoreboard().playerDeath(player);
        player.inventory.clear();
        player.setPositionAndUpdate(getSpectators().getX(), getSpectators().getY(), getSpectators().getZ());
        endMatch();
    }

    @Override
    protected boolean processBlockInteract(EntityPlayerMP player)
    {
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (itemStack == null)
            return false;

        Item item = itemStack.getItem();
        ForgePlayerTank pt = players.get(player.getUniqueID());
        if (item == Items.CLOCK)
        {
            return handleWatch(player);
        }
        else if (pt.isReady())
        {
            player.sendMessage(TextComponentUtils.redText(CommonMessages.MUST_UNREADY));
            return false;
        }

        new ForgeMainSelectionMenu(this, player);
        return true;
    }

    @Override
    public boolean removePlayer(EntityPlayerMP player)
    {
        UUID uuid = player.getUniqueID();
        ForgePlayerTank pt = players.get(uuid);
        if (pt == null)
            return false;

        if (getScoreboard().isOnGreen(player) || getScoreboard().isOnRed(player))
            getScoreboard().playerDeath(player);

        players.remove(uuid);
        ForgeMineTanks.instance.getInventoryStorage().load(player);
        if (unassigned > 0 && pt.getTeam() != MTTeam.SPECTATOR)
            unassigned--;

        return true;
    }

    @Override
    public void saveToFile(File battlefields)
    {
        Logger logger = ForgeMineTanks.instance.getLogger();
        File file = new File(battlefields, CommonConfig.battlefieldFile(this));
        try
        {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();//NOSONAR
        }
        catch (IOException e)//NOSONAR
        {
            logger.warn(CommonMessages.fileCreateFailed(file));
            return;
        }

        NBTTagCompound field = new NBTTagCompound();
        if (getRegion() != null)
        {
            NBTTagCompound region = new NBTTagCompound();
            region.setInteger(CommonConfig.MIN_X, getRegion().getMinX());
            region.setInteger(CommonConfig.MAX_X, getRegion().getMaxX());
            region.setInteger(CommonConfig.MIN_Y, getRegion().getMinY());
            region.setInteger(CommonConfig.MAX_Y, getRegion().getMaxY());
            region.setInteger(CommonConfig.MIN_Z, getRegion().getMinZ());
            region.setInteger(CommonConfig.MAX_Z, getRegion().getMaxZ());
            region.setInteger(CommonConfig.WORLD, getRegion().getDimension());
            field.setTag(CommonConfig.REGION, region);
        }

        if (getGreenSpawn() != null)
            field.setTag(CommonConfig.GREEN_SPAWN, serializeBlockPos(getGreenSpawn()));

        if (getRedSpawn() != null)
            field.setTag(CommonConfig.RED_SPAWN, serializeBlockPos(getRedSpawn()));

        if (getSpectators() != null)
            field.setTag(CommonConfig.SPECTATORS, serializeBlockPos(getSpectators()));

        field.setBoolean(CommonConfig.ENABLED, isEnabled());
        try (FileWriter fw = new FileWriter(file))
        {
            fw.write(field.toString());
            fw.close();
        }
        catch (IOException e)//NOSONAR
        {
            logger.warn(CommonMessages.fileSaveFailed(file));
        }
    }

    private NBTTagCompound serializeBlockPos(BlockPos blockPos)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger(CommonConfig.X, blockPos.getX());
        nbt.setInteger(CommonConfig.Y, blockPos.getY());
        nbt.setInteger(CommonConfig.Z, blockPos.getZ());
        return nbt;
    }

    @Override
    protected void setPlayerScoreboards()
    {
        players.keySet().stream()
                .map(server.getPlayerList()::getPlayerByUUID)
                .forEach(getScoreboard()::setPlayerScoreboard);
    }

    @Override
    protected void setUpPlayers(List<UUID> playersList)
    {
        for (UUID uuid : playersList)
        {
            ForgePlayerTank pt = players.get(uuid);
            ForgeTank tank = pt.getTank();
            EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(uuid);
            if (pt.getTeam() != MTTeam.SPECTATOR)
            {
                if (getScoreboard().isOnGreen(player))
                    player.setPositionAndUpdate(getGreenSpawn().getX(), getGreenSpawn().getY(), getGreenSpawn().getZ());
                else if (getScoreboard().isOnRed(player))
                    player.setPositionAndUpdate(getRedSpawn().getX(), getRedSpawn().getY(), getRedSpawn().getZ());

                getScoreboard().setPlayerHealth(player, tank.getHealth());
                player.inventory.clear();
                for (int i = 0; i < tank.getWeapons().size(); i++)
                    player.inventory.setInventorySlotContents(i, tank.getWeapons().get(i));

                player.inventory.armorInventory[0] = tank.getHelmet();
                player.inventory.armorInventory[1] = tank.getChestplate();
                player.inventory.armorInventory[2] = tank.getLeggings();
                player.inventory.armorInventory[3] = tank.getBoots();
                player.inventory.markDirty();
                pt.isReloading();
                //TODO spectators tank doesn't get set to null and still receives gear
                //TODO field counts spectator as unassigned
            }
            else
            {
                pt.setTank(null);
                player.inventory.clear();
            }
        }
    }

    @Override
    protected void triggerPlayerDeath(EntityPlayerMP killer, EntityPlayerMP killed)
    {
        ForgeMTScoreboard sb = getScoreboard();
        TextComponentString killedMsg = (TextComponentString) (sb.isOnGreen(killed) ? TextComponentUtils.greenText(killed.getName()) : TextComponentUtils.redText(killed.getName()));
        TextComponentString killerMsg = (TextComponentString) (sb.isOnGreen(killed) ? TextComponentUtils.greenText(killed.getName()) : TextComponentUtils.redText(killed.getName()));
        getPlayers().keySet().forEach(uuid ->
                server.getPlayerList().getPlayerByUUID(uuid)
                        .sendMessage(TextComponentUtils.goldText(CommonMessages.PREFIX).appendSibling(killedMsg)
                                .appendSibling(TextComponentUtils.goldText(" was killed by "))
                                .appendSibling(killerMsg).appendSibling(TextComponentUtils.goldText("."))));
        playerKilled(killed);
    }

    private boolean hasPermission(EntityPlayer player)
    {
        return server.getPlayerList().getOppedPlayers().getPermissionLevel(player.getGameProfile()) > 1;
    }
}
