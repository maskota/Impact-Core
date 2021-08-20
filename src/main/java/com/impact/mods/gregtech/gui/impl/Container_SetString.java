package com.impact.mods.gregtech.gui.impl;

import com.impact.client.gui.GUIHandler;
import com.impact.util.Utilits;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class Container_SetString extends GT_ContainerMetaTile_Machine {

    public Container_SetString(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
        super(inventoryPlayer, te, false);
    }

    public void setLocationName(String name) {
        IMetaTileEntity mte = this.mTileEntity.getMetaTileEntity();
        if (mte instanceof IStringSetter) {
            IStringSetter te = (IStringSetter) mte;
            te.setString(name);
        }
    }
}
