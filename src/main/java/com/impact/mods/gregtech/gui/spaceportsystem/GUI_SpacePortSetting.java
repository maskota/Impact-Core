package com.impact.mods.gregtech.gui.spaceportsystem;

import com.impact.mods.gregtech.gui.GT_GUIContainerMT_Machine;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static org.lwjgl.input.Keyboard.*;

public class GUI_SpacePortSetting extends GT_GUIContainerMT_Machine {

    public String mName;

    public GUI_SpacePortSetting(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String name) {
        super(new Container_SpacePortSetting(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "SpaceSatelliteHatches.png");
        mName = name;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {

        if (this.mContainer != null) {
          Container_SpacePortSetting aContainer = (Container_SpacePortSetting) this.mContainer;
            this.fontRendererObj.drawString(mName, 33, 8, 16448255);
            this.fontRendererObj.drawString( EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + (isKeyDown(KEY_RSHIFT) || isKeyDown(KEY_LSHIFT) ? "Set " : "Get ") + EnumChatFormatting.RESET + "Frequency: " + GT_Utility.parseNumberToString(aContainer.mFrequency), 33, 20, 16448255);
            this.fontRendererObj.drawString("Coords:", 33, 30, 16448255);
            this.fontRendererObj.drawString("X: " + aContainer.mTargetX + " Y: " + aContainer.mTargetY + " Z: " + aContainer.mTargetZ, 33, 40, 16448255);
            this.fontRendererObj.drawString("Dim ID: " + aContainer.mTargetD, 33, 50, 16448255);
            this.fontRendererObj.drawString("Connection: " + (aContainer.mIsEnable == 1
                    ? (EnumChatFormatting.GREEN + "on")
                    : (EnumChatFormatting.RED + "off")), 33, 64, 16448255);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        super.drawScreen(mouseX, mouseY, par3);
        if (mContainer != null) {
            Container_SpacePortSetting aContainer = (Container_SpacePortSetting) this.mContainer;
            if (aContainer.mTileEntity != null) {
                List<String> list = new ArrayList<>();
                list.add((isKeyDown(KEY_RSHIFT) || isKeyDown(KEY_LSHIFT) ? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_GRAY) + "Shift + RClick --> Set Frequency");
                list.add((isKeyDown(KEY_RSHIFT) || isKeyDown(KEY_LSHIFT) ? EnumChatFormatting.DARK_GRAY : EnumChatFormatting.GREEN) + "RClick --> Get Frequency");
                getTooltip(mouseX, mouseY, 8, 60-5, 16, 16, list);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}