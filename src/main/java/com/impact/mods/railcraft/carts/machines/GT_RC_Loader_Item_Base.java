package com.impact.mods.railcraft.carts.machines;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import mods.railcraft.common.carts.CartUtils;
import mods.railcraft.common.util.inventory.PhantomInventory;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class GT_RC_Loader_Item_Base extends GT_RC_Loader_Base {

    protected boolean movedItemCart;
    protected final PhantomInventory invFilters = new PhantomInventory(9, this);

    public GT_RC_Loader_Item_Base(int aID, String aName, String aNameRegional, int aTier, int slots, String aDescription) {
        super(aID, aName, aNameRegional, aTier, slots, aDescription);
    }

    public GT_RC_Loader_Item_Base(String aName, int aTier, int slots, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, slots, aDescription, aTextures);
    }

    public GT_RC_Loader_Item_Base(String aName, int aTier, int slots, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, slots, aDescription, aTextures);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        this.movedItemCart = false;
    }

    public final PhantomInventory getItemFilters() {
        return this.invFilters;
    }

    public boolean canHandleCart(EntityMinecart cart) {
        if (!(cart instanceof IInventory)) {
            return false;
        } else {
            IInventory cartInv = (IInventory)cart;
            if (cartInv.getSizeInventory() <= 0) {
                return false;
            } else {
                ItemStack minecartSlot1 = this.getCartFilters().getStackInSlot(0);
                ItemStack minecartSlot2 = this.getCartFilters().getStackInSlot(1);
                return minecartSlot1 == null && minecartSlot2 == null || CartUtils.doesCartMatchFilter(minecartSlot1, cart) || CartUtils.doesCartMatchFilter(minecartSlot2, cart);
            }
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        if (aNBT.hasKey("filters")) {
            NBTTagCompound filters = aNBT.getCompoundTag("filters");
            this.getItemFilters().readFromNBT("Items", filters);
        } else {
            this.getItemFilters().readFromNBT("invFilters", aNBT);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        this.getItemFilters().writeToNBT("invFilters", aNBT);
    }
}
