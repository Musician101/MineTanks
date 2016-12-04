package io.musician101.minetanks.forge.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TankSelectionGUI extends GuiScreen
{
    private final EntityPlayer player;

    public TankSelectionGUI(EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        GuiButton button;
    }
}
