package com.impact.mods.railcraft.carts.machines;

import com.impact.util.Utilits;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import mods.railcraft.api.carts.CartTools;
import mods.railcraft.common.util.inventory.ItemStackSet;
import mods.railcraft.common.util.inventory.wrappers.IInvSlot;
import mods.railcraft.common.util.inventory.wrappers.InventoryIterator;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class GT_RC_Loader_Item extends GT_RC_Loader_Item_Base {

    private final Set<ItemStack> checkedItems = new ItemStackSet();

    public GT_RC_Loader_Item(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 9, "123");
    }

    public GT_RC_Loader_Item(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 9, aDescription, aTextures);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_RC_Loader_Item(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public void onPostTick(IGregTechTileEntity bmte, long aTick) {
        super.onPostTick(bmte, aTick);
        if (bmte.isClientSide()) return;

//        if (aTick % 20 == 0) {
            movedItemCart = false;


            EntityMinecart cart = CartTools.getMinecartOnSide(bmte.getWorld(), bmte.getXCoord(), bmte.getYCoord(), bmte.getZCoord(), 0.1f, ForgeDirection.UP);

            if (cart != currentCart)  currentCart = cart;
            if (cart == null) return;

            IInventory cartInv = (IInventory) cart;

            int count = 0;

            for (IInvSlot slot : InventoryIterator.getIterable(cartInv)) {
                ItemStack stack = slot.getStackInSlot();
                if (stack != null) {
                    count += stack.stackSize;
                }
            }

            if (count <= 0) {
                for (int a :  ForgeDirection.OPPOSITES)
                bmte.getOutputRedstoneSignal((byte) a);
            }


            for (int i = 0; i < cartInv.getSizeInventory(); i++) {
                GT_Utility.moveOneItemStack(cartInv, bmte, bmte.getFrontFacing(), bmte.getBackFacing(),
                        null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);

                Utilits.sendChatByTE(bmte, "" + cartInv.getStackInSlot(i).getDisplayName());
            }


//        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return true;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return true;
    }

    @Override
    public boolean shouldSendCart(EntityMinecart cart) {
        return false;
    }
}
