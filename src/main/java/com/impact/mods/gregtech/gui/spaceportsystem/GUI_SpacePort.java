package com.impact.mods.gregtech.gui.spaceportsystem;

import com.impact.world.World_Interaction;
import com.impact.mods.gregtech.gui.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.units.spaceportsystem.GTMTE_Spaceport;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_SpacePort extends GT_GUIContainerMT_Machine {

	public String mName;
	public String mStationName = "";
	public String targetStationName = "";
	public List<GTMTE_Spaceport> spacePorts = new LinkedList<>();

	public void checkOnlyOwnerSpacePorts() {
		Container_SpacePort container = (Container_SpacePort) this.mContainer;
		if (container.targetID != -1 && !World_Interaction.World_SpacePort.isEmpty()) {
			GTMTE_Spaceport port = World_Interaction.World_SpacePort.get(container.targetID);
			if (port != null) targetStationName = port.name;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		checkOnlyOwnerSpacePorts();
	}

	public GUI_SpacePort(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new Container_SpacePort(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "AerostatSelect.png");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			Container_SpacePort container = (Container_SpacePort) this.mContainer;
			if (mContainer.mTileEntity != null) {
//				getTooltip(mouseX, mouseY, 9, 158 - 60, 60, 14, new String[]{
//						"Gas Amount: ",
//						NumberFormat.getNumberInstance().format(container.curBuffer) + " / " +
//								NumberFormat.getNumberInstance().format(GTMTE_Spaceport.MAX_BUFFER) + " L"
//				});
			}
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		//this.fontRendererObj.drawString(mName, 33, 8, 16448255);
		Container_SpacePort container = (Container_SpacePort) this.mContainer;
		//this.fontRendererObj.drawString("this name: " + mName, 33, 8, 16448255);
		//this.fontRendererObj.drawString(mStationName, 33, 18, 16448255);
		//this.fontRendererObj.drawString("Owner: " + EnumChatFormatting.GREEN + playerName, 33, 18, 16448255);
		if (container.targetID > 0) {
			this.fontRendererObj.drawString("Countdown to flight: " + container.targetID/20 + "s", 33, 28, 16448255);
			this.fontRendererObj.drawString(EnumChatFormatting.RED + "Waiting for flight..", 33, 38, 16448255);
			if (container.targetID/20 < 3) {
				this.fontRendererObj.drawString(EnumChatFormatting.GREEN + "Flight is performed..", 33, 48, 16448255);
			}
		} else {
			this.fontRendererObj.drawString(EnumChatFormatting.GREEN + "Flight is done!", 33, 38, 16448255);
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		Container_SpacePort container = (Container_SpacePort) mContainer;
//		double tScale = (double) container.curBuffer / (double) GTMTE_Aerostat.MAX_BUFFER;
//		drawTexturedModalRect(x + 9, y + 158 - (int) Math.min(60D, tScale * 60D), 242, 60 - (int) Math.min(60D, tScale * 60D), 14, 60);
		
	}
}