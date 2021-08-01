package com.impact.mods.railcraft.carts.machines;

import buildcraft.api.statements.IActionExternal;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import mods.railcraft.common.plugins.buildcraft.triggers.IHasCart;
import mods.railcraft.common.plugins.buildcraft.triggers.IHasWork;
import mods.railcraft.common.util.inventory.PhantomInventory;
import net.minecraft.entity.item.EntityMinecart;

public abstract class GT_RC_Loader_Base extends GT_MetaTileEntity_TieredMachineBlock implements IHasCart, IHasWork {

    protected EntityMinecart currentCart;
    protected final PhantomInventory invCarts = new PhantomInventory(2, this);

    public GT_RC_Loader_Base(int aID, String aName, String aNameRegional, int aTier, int slots, String aDescription) {
        super(aID, aName, aNameRegional, aTier, slots, aDescription);
    }

    public GT_RC_Loader_Base(String aName, int aTier, int slots, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, slots, aDescription, aTextures);
    }

    public GT_RC_Loader_Base(String aName, int aTier, int slots, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, slots, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aConnected, boolean aRedstone) {
        return mTextures[Math.min(2, aSide) + (aSide == aFacing ? 3 : 0)][aColorIndex + 1];
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[6][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1]};
            rTextures[1][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1]};
            rTextures[2][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1]};
            rTextures[3][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
            rTextures[4][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
            rTextures[5][i + 1] = new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][i + 1], Textures.BlockIcons.OVERLAYS_ENERGY_OUT[mTier]};
        }
        return rTextures;
    }

    public EntityMinecart getCurrentCart() {
        return currentCart;
    }

    public void setCurrentCart(EntityMinecart currentCart) {
        this.currentCart = currentCart;
    }

    public final PhantomInventory getCartFilters() {
        return this.invCarts;
    }

    //RailCraft Part
    public abstract boolean canHandleCart(EntityMinecart cart);

    public abstract boolean shouldSendCart(EntityMinecart cart);

    @Override
    public boolean hasWork() {
        return this.currentCart != null && this.canHandleCart(this.currentCart) &&
                (getBaseMetaTileEntity().isAllowedToWork() || !this.shouldSendCart(this.currentCart));
    }

    @Override
    public boolean hasMinecart() {
        return this.currentCart != null;
    }

    @Override
    public void actionActivated(IActionExternal iActionExternal) {

    }
}
