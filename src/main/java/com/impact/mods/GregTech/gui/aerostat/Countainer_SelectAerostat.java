package com.impact.mods.gregtech.gui.aerostat;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Countainer_SelectAerostat extends GT_ContainerMetaTile_Machine {
	
	public int idLocation = 1;
	public int curID = 1;
	public int curBuffer = 0;
	
	public Countainer_SelectAerostat(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity, false);
	}
	
	@Override
	public void addSlots(InventoryPlayer aPlayerInventory) {
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 8, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 26, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 55, false, false, 1));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
		if (aSlotIndex < 0) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		try {
			Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
			if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {
				IMetaTileEntity imte = this.mTileEntity.getMetaTileEntity();
				if (imte instanceof GTMTE_Aerostat) {
					GTMTE_Aerostat aerostat = ((GTMTE_Aerostat) this.mTileEntity.getMetaTileEntity());
					if (!aerostat.aerName.equals("")) {
						if (aSlotIndex == 0) {
							if (aShifthold == 1) {
								aerostat.aerID = Impact_API.sAerostat.size();
							} else {
								aerostat.aerID += (aerostat.aerID + 1 > Impact_API.sAerostat.size()) ? 0 : 1;
							}
							return null;
						} else if (aSlotIndex == 1) {
							if (aShifthold == 1) {
								aerostat.aerID = 1;
							} else {
								aerostat.aerID -= (aerostat.aerID - 1 < 1) ? 0 : 1;
							}
							return null;
						} else if (aSlotIndex == 2) {
							aerostat.toTravel(aPlayer);
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
			return;
		}
		idLocation = ((GTMTE_Aerostat) this.mTileEntity.getMetaTileEntity()).aerID;
		curID = ((GTMTE_Aerostat) this.mTileEntity.getMetaTileEntity()).curID;
		curBuffer = ((GTMTE_Aerostat) this.mTileEntity.getMetaTileEntity()).curBuffer;
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, this.idLocation & 0xFFFF);
			var1.sendProgressBarUpdate(this, 101, this.idLocation >>> 16);
			var1.sendProgressBarUpdate(this, 102, this.curID & 0xFFFF);
			var1.sendProgressBarUpdate(this, 103, this.curID >>> 16);
			var1.sendProgressBarUpdate(this, 104, this.curBuffer & 0xFFFF);
			var1.sendProgressBarUpdate(this, 105, this.curBuffer >>> 16);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		switch (par1) {
			case 100:
				this.idLocation = (this.idLocation & 0xFFFF0000 | par2);
				break;
			case 101:
				this.idLocation = (this.idLocation & 0xFFFF | par2 << 16);
				break;
			case 102:
				this.curID = (this.curID & 0xFFFF0000 | par2);
				break;
			case 103:
				this.curID = (this.curID & 0xFFFF | par2 << 16);
				break;
			case 104:
				this.curBuffer = (this.curBuffer & 0xFFFF0000 | par2);
				break;
			case 105:
				this.curBuffer = (this.curBuffer & 0xFFFF | par2 << 16);
				break;
		}
	}
}