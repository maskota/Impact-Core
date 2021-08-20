package com.impact.client.gui;

import com.impact.impact;
import com.impact.mods.asp.client.GuiAdvSolarPanel;
import com.impact.mods.asp.common.te.TileEntitySolarPanel;
import com.impact.mods.gregtech.gui.Container_LapTopSetting;
import com.impact.mods.gregtech.gui.GUI_LapTopSetting;
import com.impact.mods.gregtech.gui.aerostat.Container_FirstAerostat;
import com.impact.mods.gregtech.gui.aerostat.Countainer_SelectAerostat;
import com.impact.mods.gregtech.gui.aerostat.GUI_FirstAerostat;
import com.impact.mods.gregtech.gui.aerostat.GUI_SelectAerostat;
import com.impact.mods.gregtech.gui.impl.Container_SetString;
import com.impact.mods.gregtech.gui.impl.GUI_SetString;
import com.impact.mods.gregtech.gui.regulatechest.Container_ValueRegulateChest;
import com.impact.mods.gregtech.gui.regulatechest.GUI_ValueRegulateChest;
import com.impact.mods.railcraft.carts.item.client.OpenableGUI;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;

public class GUIHandler implements IGuiHandler {
	
	public static final int GUI_ID_Solar = 0, GUI_ID_Carts = 2, GUI_ID_LapTop = 3, GUI_ID_FirstAerostat = 4, GUI_ID_SetString = 7;
	
	public GUIHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		Entity e = world.getEntityByID(x);
		
		switch (ID) {
			case GUI_ID_Solar:
				if (te instanceof TileEntitySolarPanel) {
					return ((TileEntitySolarPanel) te).getGuiContainer(player.inventory);
				}
			case GUI_ID_Carts:
				if (e instanceof OpenableGUI) {
					return ((OpenableGUI) e).getServerGuiElement(ID, player, world, x, y, z);
				}
		}
		
		switch (ID) {
			case GUI_ID_LapTop:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new Container_LapTopSetting(player.inventory, gte);
				}
			case GUI_ID_FirstAerostat:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new Countainer_SelectAerostat(player.inventory, gte);
				}
			case GUI_ID_FirstAerostat + 1:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new Container_FirstAerostat(player.inventory, gte);
				}
			case GUI_ID_FirstAerostat + 2:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new Container_ValueRegulateChest(player.inventory, gte);
				}
			case GUI_ID_SetString:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new Container_SetString(player.inventory, gte);
				}
		}
		WARNING("GUIHandler Server - Not Loaded");
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		Entity e = world.getEntityByID(x);
		
		switch (ID) {
			case GUI_ID_Solar:
				if (te instanceof TileEntitySolarPanel) {
					return new GuiAdvSolarPanel((TileEntitySolarPanel) te, player.inventory);
				}
			case GUI_ID_Carts:
				if (e instanceof OpenableGUI) {
					return ((OpenableGUI) e).getClientGuiElement(ID, player, world, x, y, z);
				}
			case GUI_ID_LapTop:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new GUI_LapTopSetting(player.inventory, gte, gte.getInventoryName());
				}
			case GUI_ID_FirstAerostat:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new GUI_SelectAerostat(player.inventory, gte, "Aerostat");
				}
			case GUI_ID_FirstAerostat + 1:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new GUI_FirstAerostat(player.inventory, gte);
				}
			case GUI_ID_FirstAerostat + 2:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new GUI_ValueRegulateChest(player.inventory, gte);
				}
			case GUI_ID_SetString:
				if (te instanceof IGregTechTileEntity) {
					IGregTechTileEntity gte = (IGregTechTileEntity) te;
					return new GUI_SetString(player.inventory, gte);
				}
				INFO("GUIHandler Client - Loaded");
		}
		return null;
	}
}