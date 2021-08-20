package com.impact.mods.gregtech.items;

import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.network.ImpactNetwork;
import com.impact.network.packets.ImpactPacketStringGui;
import com.impact.util.Utilits;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_Aerostat extends Behaviour_None {
	
	public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Aerostat();
	
	protected MetaTileEntity mMetaTileEntity;
	
	public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
		TileEntity te = aWorld.getTileEntity(aX, aY, aZ);
		if (aPlayer instanceof EntityPlayerMP && te instanceof IGregTechTileEntity) {
			IGregTechTileEntity gte = (IGregTechTileEntity) te;
			IMetaTileEntity aerostat = gte.getMetaTileEntity();
			if (aerostat instanceof GTMTE_Aerostat && ((GTMTE_Aerostat) aerostat).mMachine) {
				if (!aPlayer.isSneaking()) {
					Utilits.openTileGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat, gte);
					ImpactNetwork.INSTANCE.sendToPlayer(new ImpactPacketStringGui(((GTMTE_Aerostat) aerostat).playerName, aPlayer), (EntityPlayerMP) aPlayer);
				} else {
					Utilits.openTileGui(aPlayer, GUIHandler.GUI_ID_FirstAerostat + 1, gte);
				}
			}
			return true;
		}
		return aPlayer instanceof EntityPlayerMP;
	}
	
	public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
		aList.add("1");
		aList.add("123");
		return aList;
	}
	
}
