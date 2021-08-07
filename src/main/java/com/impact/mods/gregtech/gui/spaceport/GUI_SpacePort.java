package com.impact.mods.gregtech.gui.spaceport;

import com.impact.World_Interaction;
import com.impact.mods.gregtech.gui.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Spaceport;
import com.impact.util.string.IGTE_NameHash;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import li.cil.repack.org.luaj.vm2.ast.Str;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_SpacePort extends GT_GUIContainerMT_Machine {

	public String mName;
	public String mStationName = "";
	public String playerName = "";
	public List<GTMTE_Spaceport> spacePorts = new LinkedList<>();
	public Map<GTMTE_Spaceport, String> spacePortsName = new LinkedHashMap<>();

	public void checkOnlyOwnerSpacePorts() {
		spacePorts.clear();
		spacePorts.addAll(World_Interaction.World_SpacePort);
		for (GTMTE_Spaceport a : spacePorts) {
			spacePortsName.put(a, a.getDimensionName() + "#");
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		checkOnlyOwnerSpacePorts();
	}

	public GUI_SpacePort(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new Countainer_SpacePort(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "AerostatSelect.png");
		mName = aName;

	}
	
	public String getNameLocation(int id, EnumChatFormatting color, boolean general) {
		int idd = 1;
		for (GTMTE_Spaceport spaceport : spacePorts) {
			if (idd == id) {
				String tDim = spaceport.getDimensionName() + "#" + spaceport.hash;
				return idd + ". " + tDim + (spaceport.nameHash.equals(new IGTE_NameHash(mContainer.mTileEntity).getNameHash()) ? " (this)" : "");
			}
			idd++;
		}
		return "";
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			Countainer_SpacePort container = (Countainer_SpacePort) this.mContainer;
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
		Countainer_SpacePort container = (Countainer_SpacePort) this.mContainer;
		this.fontRendererObj.drawString(mName, 33, 8, 16448255);
		//this.fontRendererObj.drawString("Owner: " + EnumChatFormatting.GREEN + playerName, 33, 18, 16448255);
		
			this.fontRendererObj.drawString("Station Select:", 33, 28, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 5, EnumChatFormatting.DARK_GRAY, false), 33, 50 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 4, EnumChatFormatting.DARK_GRAY, false), 33, 60 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 3, EnumChatFormatting.DARK_GRAY, false), 33, 70 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 2, EnumChatFormatting.DARK_GRAY, false), 33, 80 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 1, EnumChatFormatting.DARK_GRAY, false), 33, 90 - 10, 16448255);
			this.fontRendererObj.drawString(EnumChatFormatting.GOLD + getNameLocation(container.idLocation + 0, EnumChatFormatting.GOLD, true), 33, 100 - 5, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 1, EnumChatFormatting.DARK_GRAY, false), 33, 110, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 2, EnumChatFormatting.DARK_GRAY, false), 33, 120, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 3, EnumChatFormatting.DARK_GRAY, false), 33, 130, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 4, EnumChatFormatting.DARK_GRAY, false), 33, 140, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 5, EnumChatFormatting.DARK_GRAY, false), 33, 150, 16448255);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		Countainer_SpacePort container = (Countainer_SpacePort) mContainer;
//		double tScale = (double) container.curBuffer / (double) GTMTE_Aerostat.MAX_BUFFER;
//		drawTexturedModalRect(x + 9, y + 158 - (int) Math.min(60D, tScale * 60D), 242, 60 - (int) Math.min(60D, tScale * 60D), 14, 60);
		
	}
}