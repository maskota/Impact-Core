package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import static com.impact.util.Utilits.getFluidStack;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

import com.impact.mods.gregtech.gui.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

public class GTMTE_AdvancedVacuumFreezer extends GT_MetaTileEntity_MultiParallelBlockBase {

  Block CASING = GregTech_API.sBlockCasings2;
  byte CASING_META = 1;
  byte CASING_TEXTURE_ID = 17;

  public GTMTE_AdvancedVacuumFreezer(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_AdvancedVacuumFreezer(String aName) {
    super(aName);
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    return aSide == aFacing
        ? new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID],
        new GT_RenderedTexture(aActive
            ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE
            : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)}
        : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID]};
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_AdvancedVacuumFreezer(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("adv_vac_freezer");
    b
        .addInfo("info.0", "Speed Freeez!")
        .addParallelInfo(1, 256)
        .addInfo("info.1", "Super Coolant is required for operation: 50 per second")
        .addInfo("info.2", "At the output, get Hot Coolant: 25 per second")
        .addTypeMachine("name", "Vacuum Freezer")
        .addSeparatedBus()
        .addSeparator()
        .addController()
        .addEnergyHatch()
        .addMaintenanceHatch()
        .addInputHatch(1)
        .addOutputHatch(1)
        .addInputBus(8)
        .addOutputBus(1)
        .addParallelHatch()
        .addCasingInfo("case", "Frost Proof Machine Casing and IC2 Coolant fluid")
        .signAndFinalize();
    if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getInformation();
    } else {
      return b.getStructureInformation();
    }
  }

  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
        "MultiParallelBlockGUI.png", " Freezing ");
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
  }

  public boolean checkRecipe(ItemStack itemStack) {
    mCheckParallelCurrent = 0;
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
      ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
      tBus.mRecipeMap = getRecipeMap();
      if (isValidMetaTileEntity(tBus)) {
        for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
          if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
            tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
          }
        }
      }
      ArrayList<FluidStack> tFluidList = this.getStoredFluids();
      FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
      ArrayList<ItemStack> tInputList = this.getStoredInputs();
      ItemStack[] tInputs = tBusItems.toArray(new ItemStack[]{});
      if (tInputList.size() > 0) {
        long nominalV = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
        GT_Recipe tRecipe;
        tRecipe = getRecipeMap()
            .findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

        if (tRecipe != null) {

          if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100) &&
              !isValidForLowGravity(tRecipe,
                  getBaseMetaTileEntity().getWorld().provider.dimensionId)) {
            return false;
          }

          if (tRecipe.mSpecialValue == -200 && (mCleanroom == null
              || mCleanroom.mEfficiency == 0)) {
            return false;
          }

          ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
          boolean found_Recipe = false;
          while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0
              && mCheckParallelCurrent < mParallel) { //THIS PARALLEL
            if ((tRecipe.mEUt * (mCheckParallelCurrent + 1)) < nominalV && tRecipe
                .isRecipeInputEqual(true, tFluids, tInputs)) {
              found_Recipe = true;
              for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                outputItems.add(tRecipe.getOutput(i));
              }
              ++mCheckParallelCurrent;
            } else {
              break;
            }
          }
          if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * mCheckParallelCurrent;
            if (actualEUT > Integer.MAX_VALUE) {
              byte divider = 0;
              while (actualEUT > Integer.MAX_VALUE) {
                actualEUT = actualEUT / 2;
                divider++;
              }
              OverclockCalculate.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
                  tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else {
              OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV,
                  this);
            }
            //In case recipe is too OP for that machine
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1
                && this.mEUt == Integer.MAX_VALUE - 1) {
              return false;
            }
            if (this.mEUt > 0) {
              this.mEUt = (-this.mEUt);
            }
            int TimeProgress;
            switch (mParallel) {
              default:
                TimeProgress = this.mMaxProgresstime;
                break;
              case 16:
                TimeProgress = this.mMaxProgresstime / 2;
                break;
              case 64:
                TimeProgress = this.mMaxProgresstime / 3;
                break;
              case 256:
                TimeProgress = this.mMaxProgresstime / 4;
            }

            this.mMaxProgresstime = Math.max(1, TimeProgress);
            this.mOutputItems = new ItemStack[outputItems.size()];
            this.mOutputItems = outputItems.toArray(this.mOutputItems);
            this.updateSlots();
            return true;
          }
        }
      }
    }
    return false;
  }


  @Override
  public boolean onRunningTick(ItemStack aStack) {
    if (getBaseMetaTileEntity().getTimer() % 20 == 0) {
      if (this.mEfficiency > 0 && depleteInput(getFluidStack("supercoolant", 50))) {
        addOutput(getFluidStack("ic2hotcoolant", 25));
      } else {
        stopMachine();
      }
    }
    return super.onRunningTick(aStack);
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {
    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    int minCasingAmount = 12; // Минимальное количество кейсов
    boolean formationChecklist = true; // Если все ок, машина собралась

    for (byte X = -1; X <= 1; X++) {
      for (byte Z = 0; Z >= -4; Z--) {
        for (byte Y = -1; Y <= 2; Y++) {

          if (X == 0 && Y == 0 && Z == 0) {
            continue;
          }

          final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

          if (X == 0 && (((Y == 0 || Y == 1 || Y == 2) && (Z == -1 || Z == -2))
              || Y == 2 && Z == -3)) {
            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == BlocksItems
                .getFluidBlock(InternalName.fluidCoolant))) {
            } else {
              formationChecklist = false;
            }
            continue;
          }

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
              minCasingAmount--;
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }

    for (byte Z = -1; Z >= -3; Z--) {
      for (byte Y = -1; Y <= 1; Y++) {
        final Vector3ic offset = rotateOffsetVector(forgeDirection, -2, Y, Z);

        if (Z == -1 && Y == 1) {
          continue;
        }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
              == CASING_META)) {
            minCasingAmount--;
          } else {
            formationChecklist = false;
          }
        }
      }
    }

    for (byte Z = -1; Z >= -3; Z--) {
      for (byte Y = -1; Y <= 1; Y++) {
        final Vector3ic offset = rotateOffsetVector(forgeDirection, 2, Y, Z);

        if (Z == -1 && Y == 1) {
          continue;
        }

        IGregTechTileEntity currentTE = thisController
            .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
        if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

          if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
              && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
              == CASING_META)) {
            minCasingAmount--;
          } else {
            formationChecklist = false;
          }
        }
      }
    }

    if (this.mInputBusses.size() > 8) {
      formationChecklist = false;
    }
    if (this.mInputHatches.size() > 1) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 3) {
      formationChecklist = false;
    }
    if (this.mOutputHatches.size() > 1) {
      formationChecklist = false;
    }
    if (this.mEnergyHatches.size() > 4) {
      formationChecklist = false;
    }
    if (this.mMaintenanceHatches.size() != 1) {
      formationChecklist = false;
    }
    if (this.sParallHatchesIn.size() > 1) {
      formationChecklist = false;
    }
    if (this.sParallHatchesOut.size() != 0) {
      formationChecklist = false;
    }
    return formationChecklist;
  }

  @Override
  public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
      float aZ) {
    super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
    if (aPlayer.isSneaking()) {
      if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
        modeBuses++;
        if (modeBuses > 1) {
          modeBuses = 0;
        }

        GT_Utility.sendChatToPlayer(aPlayer, "Buses separated " + (modeBuses == 0 ? "on" : "off"));
      }
    }
  }

  @Override
  public int getPollutionPerTick(ItemStack aStack) {
    return 0;
  }
}