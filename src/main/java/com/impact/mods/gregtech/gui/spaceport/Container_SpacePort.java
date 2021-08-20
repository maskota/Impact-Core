package com.impact.mods.gregtech.gui.spaceport;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Spaceport;
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

public class Container_SpacePort extends GT_ContainerMetaTile_Machine {

    public int targetID = 0;


    public Container_SpacePort(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity, false);
    }

    @Override
    public void addSlots(InventoryPlayer aPlayerInventory) {
//        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 8, false, false, 1));
//        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 26, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 0, 8, 55, false, false, 1));
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
                if (imte instanceof GTMTE_Spaceport) {
                    GTMTE_Spaceport spaceport = ((GTMTE_Spaceport) this.mTileEntity.getMetaTileEntity());
                    if (aSlotIndex == 0) spaceport.teleportEntity(aPlayer);
                }
            }
        } catch (Exception ignored) {
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) return;
        targetID = ((GTMTE_Spaceport) this.mTileEntity.getMetaTileEntity()).getTargetIDPort();
        for (Object crafter : this.crafters) {
            ICrafting var1 = (ICrafting) crafter;
            var1.sendProgressBarUpdate(this, 100, this.targetID & 0xFFFF);
            var1.sendProgressBarUpdate(this, 101, this.targetID >>> 16);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                this.targetID = (this.targetID & 0xFFFF0000 | par2);
                break;
            case 101:
                this.targetID = (this.targetID & 0xFFFF | par2 << 16);
                break;
        }
    }
}