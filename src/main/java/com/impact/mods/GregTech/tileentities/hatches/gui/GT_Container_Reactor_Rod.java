package com.impact.mods.GregTech.tileentities.hatches.gui;

import com.impact.mods.GregTech.tileentities.hatches.GTMTE_Reactor_Rod_Hatch;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_Reactor_Rod extends GT_ContainerMetaTile_Machine {

    public GT_Container_Reactor_Rod(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(this.mTileEntity, 0, 90, 8));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 70, 8, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 70, 62, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 1) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {

            switch (aSlotIndex) {
                case 1:
                    ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod -= 1;
                    if (((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod < 0)
                        ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod = 0;
                    if (aShifthold == 1)
                        ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod = 0;
                    return null;
                case 2:
                    ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod += (aShifthold == 1 ? 10 : 1);
                    if (((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod > 10)
                        ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod = 10;
                    if (aShifthold == 1)
                        ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod = 10;
                    return null;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    public int getPosRod() {
        return ((GTMTE_Reactor_Rod_Hatch) this.mTileEntity.getMetaTileEntity()).mDownRod;
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }

}
