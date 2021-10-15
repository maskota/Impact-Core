package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.util.Utilits.getFluidStack;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.gregtech.gui.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.Utilits;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.multis.WorldProperties;
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
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

public class GTMTE_MultiDistillationTower extends GT_MetaTileEntity_MultiParallelBlockBase {

  Block CASING = GregTech_API.sBlockCasings4;
  byte CASING_META = 1;
  byte CASING_TEXTURE_ID = 49;
  private short controllerY;

  public GTMTE_MultiDistillationTower(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
    build();
  }

  public GTMTE_MultiDistillationTower(String aName) {
    super(aName);
    build();
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

  private void build() {
    registerMetaClass(GTMTE_MultiDistillationTower.class,
        new IMultiblockInfoContainer<GTMTE_MultiDistillationTower>() {
          //region Structure
          private final IStructureDefinition<GTMTE_MultiDistillationTower> definition =
              StructureDefinition.<GTMTE_MultiDistillationTower>builder()
                  .addShape("main", new String[][]{
                      {" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " A~A ",},
                      {"AAAAA", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "AAAAA",},
                      {"AAAAA", "AB BA", "AB BA", "A   A", "A   A", "AB BA", "AB BA", "A   A", "A   A", "AB BA", "AB BA", "AAAAA",},
                      {"AAAAA", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "A   A", "A   A", "ABBBA", "ABBBA", "AAAAA",},
                      {" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ",},
                  })
                  .addElement('A', ofBlock(CASING, CASING_META))
                  .addElement('B', ofBlock(GregTech_API.sBlockCasings5, 1))
                  .build();
          private final String[] desc = new String[]{
              EnumChatFormatting.RED + "Impact Details:",
              "It's minimal length structure",
              " - Clean Stainless Steel Machine Casing",
              " - Kanthal Coil",
          };

          //endregion
          @Override
          public void construct(ItemStack stackSize, boolean hintsOnly,
              GTMTE_MultiDistillationTower tileEntity, ExtendedFacing aSide) {
            IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
            definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                2, 11, 0, hintsOnly);
          }

          @Override
          public String[] getDescription(ItemStack stackSize) {
            return desc;
          }
        });
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    build();
    return new GTMTE_MultiDistillationTower(this.mName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_distill");
    b
        .addInfo("info.0", "OMG!? Where Bart`s Big Tower?")
        .addSingleAnalog()
        .addParallelInfo(1, 256)
        .addTypeMachine("name", "Multi Distillation Tower")
        .addSeparator()
        .addController()
        .addEnergyHatch(4)
        .addMaintenanceHatch()
        .addInputHatch(9)
        .addOutputHatch(66)
        .addInputBus(4)
        .addOutputBus(1)
        .addParallelHatch(1)
        .addCasingInfo("case", "Clean Stainless Steel Machine Casing")
        .addOtherStructurePart("other.0", "Kanthal Coil", "other.1", "inside the hollow")
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
        "MultiParallelBlockGUI.png", "");
  }

  @Override
  public GT_Recipe.GT_Recipe_Map getRecipeMap() {
    return GT_Recipe_Map.sDistillationRecipes;
  }

  public boolean checkRecipe(ItemStack itemStack) {
    mCheckParallelCurrent = 0;
    if (sParallHatchesIn.size() > 0 && getRecipeCheckParallel()) {
      return false;
    }
    ArrayList<ItemStack> tInputList = this.getStoredInputs();
    ArrayList<FluidStack> tFluidList = this.getStoredFluids();
    ItemStack[] tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
    FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

    if (tInputList.size() > 0 || tFluidList.size() > 0) {
      long nominalV = getMaxInputVoltage();
      byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
      GT_Recipe tRecipe = getRecipeMap()
          .findRecipe(this.getBaseMetaTileEntity(), false, false, V[tTier], tFluids, tInputs);
      if (tRecipe != null) {
        if (!WorldProperties.needCleanroom(tRecipe, this)) {
          return false;
        }
        if (!WorldProperties.needSpace(tRecipe, this)) {
          return false;
        }
        ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
        ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
        boolean found_Recipe = false;
        ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];
        while ((tFluidList.size() > 0 || tInputList.size() > 0) && mCheckParallelCurrent < mParallel) {
          if ((tRecipe.mEUt * (mCheckParallelCurrent + 1)) < nominalV && tRecipe
              .isRecipeInputEqual(true, tFluids, tInputs)) {
            found_Recipe = true;
            for (int h = 0; h < tRecipe.mOutputs.length; h++) {
              if (tRecipe.getOutput(h) != null) {
                tOut[h] = tRecipe.getOutput(h).copy();
                tOut[h].stackSize = 0;
              }
            }
            for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
              outputFluids.add(tRecipe.getFluidOutput(i));
            }
            ++mCheckParallelCurrent;
          } else {
            break;
          }
        }
        for (int f = 0; f < tOut.length; f++) {
          if (tRecipe.mOutputs[f] != null && tOut[f] != null) {
            for (int g = 0; g < mCheckParallelCurrent; g++) {
              if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe
                  .getOutputChance(f)) {
                tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
              }
            }
          }
        }
        tOut = clean(tOut);
        List<ItemStack> overStacks = new ArrayList<ItemStack>();
        for (ItemStack stack : tOut) {
          while (stack.getMaxStackSize() < stack.stackSize) {
            ItemStack tmp = stack.copy();
            tmp.stackSize = tmp.getMaxStackSize();
            stack.stackSize = stack.stackSize - stack.getMaxStackSize();
            overStacks.add(tmp);
          }
        }
        if (overStacks.size() > 0) {
          ItemStack[] tmp = new ItemStack[overStacks.size()];
          tmp = overStacks.toArray(tmp);
          tOut = ArrayUtils.addAll(tOut, tmp);
        }
        List<ItemStack> tSList = new ArrayList<ItemStack>();
        for (ItemStack tS : tOut) {
          if (tS.stackSize > 0) {
            tSList.add(tS);
          }
        }
        tOut = tSList.toArray(new ItemStack[tSList.size()]);
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
              break;
          }
          this.mMaxProgresstime = TimeProgress;
          if (this.mMaxProgresstime < 1) {
            this.mMaxProgresstime = 1;
          }
          this.mOutputItems = tOut;
          for (FluidStack fs : outputFluids) {
            fs.amount *= mCheckParallelCurrent;
          }
          this.mOutputFluids = new FluidStack[outputFluids.size()];
          this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);

          this.updateSlots();
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean machineStructure(IGregTechTileEntity thisController) {

    controllerY = thisController.getYCoord();

    final Vector3ic forgeDirection = new Vector3i(
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
        ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

    boolean formationChecklist = true; // Если все ок, машина собралась

    int x, y, z;

    for (x = -2; x <= 2; x++) {
      for (y = 0; y <= 11; y++) {
        for (z = 0; z >= -4; z--) {
          if (x == 0 && y == 0 && z == 0) {
            continue;
          }

          final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);

          if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
            continue;
          }

          if ((y > 0 && y < 11) && (x > -2 && x < 2) && (z < 0 && z > -4)) {
            if ((y <= 2 || y >= 5 && y <= 6 || y >= 9)) {
              if (x == 0 && z == -2) {
                continue;
              }

              //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), GregTech_API.sBlockCasings5, 1);

              if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockCasings5)
                  && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 1)) {
              } else {
                formationChecklist = false;
              }
              continue;
            }
            continue;
          }

          IGregTechTileEntity currentTE = thisController
              .getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
          if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
              && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {

            //debug Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), CASING, CASING_META);

            if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
                == CASING_META)) {
            } else {
              formationChecklist = false;
            }
          }
        }
      }
    }

    if (this.mInputBusses.size() > 6) {
      formationChecklist = false;
    }
    if (this.mInputHatches.size() > 9) {
      formationChecklist = false;
    }
    if (this.mOutputBusses.size() > 3) {
      formationChecklist = false;
    }
    if (this.mOutputHatches.size() > 66) {
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

  private boolean dumpFluid(ArrayList<GT_MetaTileEntity_Hatch_Output> outputHatches, FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
    for (GT_MetaTileEntity_Hatch_Output tHatch : outputHatches) {
      if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
        continue;
      }
      if (GT_ModHandler.isSteam(copiedFluidStack)) {
        if (!tHatch.outputsSteam()) {
          continue;
        }
      } else {
        if (!tHatch.outputsLiquids()) {
          continue;
        }
        if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch.getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
          continue;
        }
      }
      int tAmount = tHatch.fill(copiedFluidStack, false);
      if (tAmount >= copiedFluidStack.amount) {
        boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
        tHatch.onEmptyingContainerWhenEmpty();
        return filled;
      } else if (tAmount > 0) {
        copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
        tHatch.onEmptyingContainerWhenEmpty();
      }
    }
    return false;
  }

  public boolean addOutput(FluidStack aLiquid, int i) {
    if (aLiquid == null) return false;
    FluidStack copiedFluidStack = aLiquid.copy();

    ArrayList<GT_MetaTileEntity_Hatch_Output> tOutputHatches = new ArrayList<GT_MetaTileEntity_Hatch_Output>();
    for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
      if (tHatch.getBaseMetaTileEntity().getYCoord() == this.controllerY + 1 + i) {
        tOutputHatches.add(tHatch);
      }
    }
    if (!dumpFluid(tOutputHatches, copiedFluidStack, true)) {
      dumpFluid(tOutputHatches, copiedFluidStack, false);
    }
    return false;
  }

  @Override
  protected void addFluidOutputs(FluidStack[] mOutputFluids2) {
    for (int i = 0; i < mOutputFluids2.length; i++) {
      addOutput(mOutputFluids2[i], i);
    }
  }
}