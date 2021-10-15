package com.impact.mods.gregtech.tileentities.hatches;

import com.impact.util.Language;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_1by1;
import gregtech.api.gui.GT_GUIContainer_1by1;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Primitive_InputBus extends GT_MetaTileEntity_Hatch {

  public GT_Recipe_Map mRecipeMap = null;
  public boolean disableSort;
  public byte mMachineBlock = 0;
  private byte mTexturePage = 0;
  private byte actualTexture = 0;

  public GT_MetaTileEntity_Primitive_InputBus(int aID, String aName, String aNameRegional,
      int aTier) {
    super(aID, aName, aNameRegional, aTier, getSlots(aTier), new String[]{
            Language.transDesc("basic.hatch.primitive.item.in.0",  "Primitive Item Input for Coke Oven"),
            Language.transDesc("basic.hatch.primitive.item.in.1", "Capacity: 1 stack")
    });
  }

  public GT_MetaTileEntity_Primitive_InputBus(String aName, int aTier, String aDescription,
      ITexture[][][] aTextures) {
    super(aName, 0, 1, aDescription, aTextures);
  }

  public GT_MetaTileEntity_Primitive_InputBus(String aName, int aTier, String[] aDescription,
      ITexture[][][] aTextures) {
    super(aName, 0, 1, aDescription, aTextures);
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    if (aSide == aFacing) {
      return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53], new GT_RenderedTexture(
          aActive ? Textures.BlockIcons.OVERLAY_PIPE_IN : Textures.BlockIcons.OVERLAY_PIPE_IN)};
    } else if (aSide != aFacing) {
      return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53]};
    }
    int textureIndex = this.actualTexture | this.mTexturePage << 7;
    int texturePointer = (byte) (this.actualTexture & 127);
    return aSide != aFacing ? (textureIndex > 0 ? new ITexture[]{
        Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]}
        : new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1]})
        : (textureIndex > 0 ? (aActive ? this.getTexturesActive(
            Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]) : this
            .getTexturesInactive(
                Textures.BlockIcons.casingTexturePages[this.mTexturePage][texturePointer]))
            : (aActive ? this
                .getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1])
                : this.getTexturesInactive(
                    Textures.BlockIcons.MACHINE_CASINGS[this.mTier][aColorIndex + 1])));

  }

  @Override
  public ITexture[] getTexturesActive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
  }

  @Override
  public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
    return new ITexture[]{aBaseTexture,
        new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
  }

  @Override
  public boolean isSimpleMachine() {
    return true;
  }

  @Override
  public boolean isFacingValid(byte aFacing) {
    return true;
  }

  @Override
  public boolean isAccessAllowed(EntityPlayer aPlayer) {
    return true;
  }

  @Override
  public boolean isValidSlot(int aIndex) {
    return true;
  }

  @Override
  public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GT_MetaTileEntity_Primitive_InputBus(mName, mTier, mDescriptionArray, mTextures);
  }

  @Override
  public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
    if (aBaseMetaTileEntity.isClientSide()) {
      return true;
    }
    aBaseMetaTileEntity.openGUI(aPlayer);
    return true;
  }

  @Override
  public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
  }

  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, "Primitive Input Bus");
  }


  @Override
  public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
    if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.hasInventoryBeenModified()) {
      fillStacksIntoFirstSlots();
    }
  }

  public void updateSlots() {
    for (int i = 0; i < mInventory.length; i++) {
      if (mInventory[i] != null && mInventory[i].stackSize <= 0) {
        mInventory[i] = null;
      }
    }
    fillStacksIntoFirstSlots();
  }

  protected void fillStacksIntoFirstSlots() {
    if (disableSort) {
      for (int i = 0; i < mInventory.length; i++) {
        for (int j = i + 1; j < mInventory.length; j++) {
          if (mInventory[j] != null && mInventory[j].stackSize <= 0 && (mInventory[i] == null
              || GT_Utility.areStacksEqual(mInventory[i], mInventory[j]))) {
            GT_Utility
                .moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(),
                    j,
                    i,
                    (byte) 64, (byte) 1, (byte) 64, (byte) 1);
          }
        }
      }
    } else {
      for (int i = 0; i < mInventory.length; i++) {
        for (int j = i + 1; j < mInventory.length; j++) {
          if (mInventory[j] != null && (mInventory[i] == null || GT_Utility
              .areStacksEqual(mInventory[i], mInventory[j]))) {
            GT_Utility
                .moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(),
                    j,
                    i,
                    (byte) 64, (byte) 1, (byte) 64, (byte) 1);
          }
        }
      }
    }
  }

  @Override
  public void saveNBTData(NBTTagCompound aNBT) {
    super.saveNBTData(aNBT);
    aNBT.setBoolean("disableSort", disableSort);
  }

  @Override
  public void loadNBTData(NBTTagCompound aNBT) {
    super.loadNBTData(aNBT);
    disableSort = aNBT.getBoolean("disableSort");
  }

  @Override
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    if (!getBaseMetaTileEntity().getCoverBehaviorAtSide(aSide)
        .isGUIClickable(aSide, getBaseMetaTileEntity().getCoverIDAtSide(aSide),
            getBaseMetaTileEntity().getCoverDataAtSide(aSide), getBaseMetaTileEntity())) {
      return;
    }
    if (aPlayer.isSneaking()) {
      disableSort = !disableSort;
      GT_Utility.sendChatToPlayer(aPlayer,
          trans("200", "Sort mode: " + (disableSort ? "Disabled" : "Enabled")));
    }
  }

  public String trans(String aKey, String aEnglish) {
    return GT_LanguageManager
        .addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
  }

  @Override
  public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return false;
  }

  @Override
  public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
      ItemStack aStack) {
    return aSide == getBaseMetaTileEntity().getFrontFacing() && (mRecipeMap == null || mRecipeMap
        .containsInput(aStack));
  }
}
