package com.impact.mods.gregtech.gui.spaceportsystem;

import com.impact.mods.gregtech.tileentities.multi.units.spaceportsystem.GTMTE_Spaceport;
import com.impact.mods.gregtech.tileentities.multi.units.spaceportsystem.GTMTE_Spaceport_Connector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_SpacePortSetting extends GT_ContainerMetaTile_Machine {

    public int mFrequency = 0;
    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public int mTargetD = 0;
    public int mIsEnable = 0;

    public Container_SpacePortSetting(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aPlayerInventory) {
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 24 - 16, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 42 - 16, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 60-5, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 0) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }

        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {

            if (mTileEntity.getMetaTileEntity() instanceof GTMTE_Spaceport_Connector) {
                GTMTE_Spaceport_Connector base = (GTMTE_Spaceport_Connector) mTileEntity.getMetaTileEntity();
                int aFrequency = base.mFrequency;
                switch (aSlotIndex) {
                    case 0:
                        base.mFrequency += (aShifthold == 1 ? 10 : 1);
                        return null;
                    case 1:
                        base.mFrequency -= (aShifthold == 1 ? aFrequency - 10 < 0 ? 0 : 10 : aFrequency - 1 < 0 ? 0 : 1);
                        return null;
                    case 2:
                        if (aShifthold == 1) {
                            base.setFrequency(base.mFrequency, aPlayer);
                        } else {
                            base.getFrequency(base.mFrequency, aPlayer);
                        }
                        return null;
                }
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
            return;
        }

        if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_Spaceport_Connector) {
            GTMTE_Spaceport_Connector connector = ((GTMTE_Spaceport_Connector) this.mTileEntity.getMetaTileEntity());
            this.mTargetX = connector.mTargetX;
            this.mTargetY = connector.mTargetY;
            this.mTargetZ = connector.mTargetZ;
            this.mTargetD = connector.mTargetD;
            this.mFrequency = connector.mFrequency;
            this.mIsEnable = connector.mConnected ? 1 : 0;
        }

        for (Object crafter : this.crafters) {
            ICrafting var1 = (ICrafting) crafter;
            var1.sendProgressBarUpdate(this, 100, this.mTargetX & 0xFFFF);
            var1.sendProgressBarUpdate(this, 101, this.mTargetX >>> 16);

            var1.sendProgressBarUpdate(this, 102, this.mTargetY & 0xFFFF);
            var1.sendProgressBarUpdate(this, 103, this.mTargetY >>> 16);

            var1.sendProgressBarUpdate(this, 104, this.mTargetZ & 0xFFFF);
            var1.sendProgressBarUpdate(this, 105, this.mTargetZ >>> 16);

            var1.sendProgressBarUpdate(this, 106, this.mTargetD & 0xFFFF);
            var1.sendProgressBarUpdate(this, 107, this.mTargetD >>> 16);

            var1.sendProgressBarUpdate(this, 108, this.mFrequency & 0xFFFF);
            var1.sendProgressBarUpdate(this, 109, this.mFrequency >>> 16);

            var1.sendProgressBarUpdate(this, 110, this.mIsEnable);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                this.mTargetX = (this.mTargetX & 0xFFFF0000 | par2);
                break;
            case 101:
                this.mTargetX = (this.mTargetX & 0xFFFF | par2 << 16);
                break;
            case 102:
                this.mTargetY = (this.mTargetY & 0xFFFF0000 | par2);
                break;
            case 103:
                this.mTargetY = (this.mTargetY & 0xFFFF | par2 << 16);
                break;
            case 104:
                this.mTargetZ = (this.mTargetZ & 0xFFFF0000 | par2);
                break;
            case 105:
                this.mTargetZ = (this.mTargetZ & 0xFFFF | par2 << 16);
                break;
            case 106:
                this.mTargetD = (this.mTargetD & 0xFFFF0000 | par2);
                break;
            case 107:
                this.mTargetD = (this.mTargetD & 0xFFFF | par2 << 16);
                break;
            case 108:
                this.mFrequency = (this.mFrequency & 0xFFFF0000 | par2);
                break;
            case 109:
                this.mFrequency = (this.mFrequency & 0xFFFF | par2 << 16);
                break;
            case 110:
                this.mIsEnable = par2;
        }
    }
}