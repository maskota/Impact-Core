package com.impact.mods.GregTech.tileentities.storage;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.tileentities.hatches.GTMTE_RegulatorTankHatch;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.FluidTankBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static gregtech.api.GregTech_API.sBlockCasings8;

public class GTMTE_LargeTank extends GT_MetaTileEntity_MultiBlockBase implements IFluidHandler {

    private final HashSet<GTMTE_RegulatorTankHatch> sRegulatorTankHatch = new HashSet<>();

    private final Block CASING = sBlockCasings8;
    private final Block CASING_TANK = FluidTankBlock;
    public FluidStack mFluid;
    public long mFluidAmount = 0;
    public long mFluidAmountMAX = 1;
    int CASING_TEXTURE_ID = 176;
    private boolean doVoidExcess = false;


    public GTMTE_LargeTank(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_LargeTank(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        run();
        return new GTMTE_LargeTank(super.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b.addInfo("High-Tech fluid tank!")
                .addInfo("Right-Click the controller with a screwdriver will turn on excess voiding.")
                .addInfo("Fluid storage amount and running cost depends on the storage field blocks used.")
                .addSeparator()
                .addInfo("Note on hatch locking:")
                .addSeparator()
                .beginStructureBlock(3, 7, 3, true)
                .addController()
                .addEnergyHatch("Any top or bottom casing")
                .addOtherStructurePart("Inner 1x5x1 tube", "Tank Storage Block")
                .addOtherStructurePart("Outer 3x1&7x3 Casing", "Chemical Casing")
                .addOtherStructurePart("Outer 3x5x3 glass shell", "I-Glass")
                .addOtherStructurePart("I/O Tank Hatch", "Instead of any casing or glass")
                .addInfo("I/O Tank Hatch for information and used EC2, OC systems")
                .signAndFinalize(": " + EnumChatFormatting.RED + "Impact");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
                                 boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48],
                new GT_RenderedTexture(aActive
                        ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR)}
                : new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(),
                "MultiblockDisplay.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack var1) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack guiSlotItem) {
        this.mProgresstime = 1;
        this.mMaxProgresstime = 20;
        this.mEUt = 0;
        this.mEfficiencyIncrease = 10000;




        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        final ArrayList<FluidStack> inputHatchFluids = getStoredFluids();

        if (inputHatchFluids.size() > 0) {

            for (FluidStack fluidStack : inputHatchFluids) {

                if (GT_Utility.areFluidsEqual(fluidStack, mFluid)) {
                    if ((fluidStack.amount + mFluidAmount) <= mFluidAmountMAX) {
                        mFluidAmount += fluidStack.amount;
                        depleteInput(fluidStack);
                    }
                } else if (mFluid == null) {
                    mFluid = fluidStack;
                    //mFluidAmount += mFluid.amount;
                    //depleteInput(fluidStack);
                }
            }
        }

        int possibleOutput = 0;
        for(GT_MetaTileEntity_Hatch_Output outputHatch : super.mOutputHatches) {
            if(outputHatch.isFluidLocked() && outputHatch.getLockedFluidName().equals(mFluid.getUnlocalizedName())) {
                possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
            } else if (outputHatch.getFluid() != null && outputHatch.getFluid().getUnlocalizedName().equals(mFluid.getUnlocalizedName())) {
                possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
            } else if (outputHatch.getFluid() == null) {
                possibleOutput += outputHatch.getCapacity() - outputHatch.getFluidAmount();
            }
        }

        final FluidStack tempStack = mFluid.copy();
        tempStack.amount = possibleOutput;
        if ((mFluidAmount - tempStack.amount) >= 0) {
            mFluidAmount -= tempStack.amount;
            addOutput(new FluidStack(tempStack.getFluid(), (int) Math.min(mFluidAmount, Integer.MAX_VALUE)));
        }
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // either direction on z-axis
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // either direction on x-axis
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // either direction on y-axis
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {

        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
        );
        boolean formationChecklist = true;
        int minCasingAmount = 16;
        int firstGlassMeta = -1;
        long fluidCapacityAcc = 0;

        sRegulatorTankHatch.clear();

        for (int Y = 0; Y <= 1; Y++) {
            for (int X = -2; X <= 2; X++) {
                for (int Z = 0; Z >= -4; Z--) {

                    if (X == 0 && Y == 0 && Z == 0) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    final IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                    if (!addRegulatorTankHatchToMachineList(currentTE, CASING_TEXTURE_ID) &&
                            !addInputToMachineList(currentTE, CASING_TEXTURE_ID) &&
                            !addOutputToMachineList(currentTE, CASING_TEXTURE_ID) &&
                            !addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        int firstGlassHeight = 3;
        for (int X = -1; X <= 1; X++) {
            for (int Z = -1; Z >= -3; Z--) {
                for (int Y = 2; Y <= 17; Y++) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
                    if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING_TANK) {
                        switch (meta) {
                            case 0:
                                fluidCapacityAcc += 32000000L;
                                break;
                            case 1:
                                fluidCapacityAcc += 512000000L;
                                break;
                            case 2:
                                fluidCapacityAcc += 8192000000L;
                                break;
                            case 3:
                                fluidCapacityAcc += 131072000000L;
                                break;
                            case 4:
                                fluidCapacityAcc += 2097152000000L;
                                break;
                            case 5:
                                fluidCapacityAcc += 33554432000000L;
                                break;
                            case 6:
                                fluidCapacityAcc += 536870912000000L;
                                break;
                            case 7:
                                fluidCapacityAcc += 8589934592000000L;
                                break;
                        }
                    } else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                        firstGlassHeight = Y;
                        break;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }

        for (int Y = 2; Y <= firstGlassHeight; Y++) {
            for (int X = -2; X <= 2; X++) {
                for (int Z = 0; Z >= -4; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());

                    if ((Y < firstGlassHeight)) {
                        if (X == -2 || X == 2 || Z == 0 || Z == 4) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                                if (firstGlassMeta == -1) {
                                    firstGlassMeta = meta;
                                } else if (meta != firstGlassMeta) {
                                    formationChecklist = false;
                                }
                            } else {
                                formationChecklist = false;
                            }
                        }
                    } else {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                            if (meta != firstGlassMeta) {
                                formationChecklist = false;
                            }
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if (minCasingAmount > 0) formationChecklist = false;

        mFluidAmountMAX = fluidCapacityAcc;

        for (GTMTE_RegulatorTankHatch aRegulatorTankHatch : sRegulatorTankHatch)
            aRegulatorTankHatch.setCapacity(1000000000);

        if (this.mEnergyHatches.size() > 1) formationChecklist = false;

        this.mWrench = true;
        this.mScrewdriver = true;
        this.mSoftHammer = true;
        this.mHardHammer = true;
        this.mSolderingTool = true;
        this.mCrowbar = true;
        return formationChecklist;
    }

    public void run() {
        registerMetaClass(GTMTE_LargeTank.class, new IMultiblockInfoContainer<GTMTE_LargeTank>() {
            //region Structure
            private final IStructureDefinition<GTMTE_LargeTank> definition =
                    StructureDefinition.<GTMTE_LargeTank>builder()
                            .addShape("main", new String[][]{
                                    {"AAA", "A~A", "AAA"},
                                    {"CCC", "CBC", "CCC"},
                                    {"CCC", "CBC", "CCC"},
                                    {"CCC", "CBC", "CCC"},
                                    {"CCC", "CBC", "CCC"},
                                    {"CCC", "CBC", "CCC"},
                                    {"AAA", "AAA", "AAA"}
                            })
                            .addElement('A', ofBlock(sBlockCasings8, 0))
                            .addElement('B', ofBlock(FluidTankBlock))
                            .addElement('C', ofBlock(IGlassBlock))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- Chemical Casing",
                    "- Tank Storage Block (Tier 1-6)",
                    "- I-Glass",
                    "- Hatches (any Chemical Casing)",
                    "- I/O Tank Hatch (any Chemical Casing or I-Glass (not angle))",
            };

            //endregion
            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_LargeTank tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        1, 1, 0, hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });
    }

    public boolean addRegulatorTankHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_RegulatorTankHatch) {
                ((GTMTE_RegulatorTankHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sRegulatorTankHatch.add((GTMTE_RegulatorTankHatch) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        doVoidExcess = !doVoidExcess;
        GT_Utility.sendChatToPlayer(aPlayer, doVoidExcess ? "Auto-voiding enabled" : "Auto-voiding disabled");
    }

    @Override
    public String[] getInfoData() {
        final ArrayList<String> ll = new ArrayList<>();
        int fluidInHatches = 0;
        String fluidName = "Empty Fluid";

//        if (mFluid != null) {
//            for (GT_MetaTileEntity_Hatch_Output outputHatch : mOutputHatches) {
//                fluidInHatches += outputHatch.getFluidAmount();
//                fluidName = outputHatch.getFluid().getLocalizedName();
//            }
//
//            for (GTMTE_RegulatorTankHatch outputHatch : sRegulatorTankHatch) {
//                fluidInHatches += outputHatch.getFluidAmount();
//                fluidName = outputHatch.getFluid().getLocalizedName();
//            }
//        }

        ll.add(EnumChatFormatting.YELLOW + "Operational Data:" + EnumChatFormatting.RESET);
        ll.add("Fluid Name: " + fluidName);
        ll.add("Amount Cur: " + EnumChatFormatting.GREEN + GT_Utility.formatNumbers(mFluidAmount + fluidInHatches) + EnumChatFormatting.RESET + " L");
        ll.add("Amount Max: " + EnumChatFormatting.YELLOW + GT_Utility.formatNumbers(mFluidAmountMAX) + EnumChatFormatting.RESET + " L");
        ll.add("Running Cost: " + EnumChatFormatting.RED + ((super.mEUt) * 10000 / Math.max(1000, super.mEfficiency)) + EnumChatFormatting.RESET + " EU/t");
        ll.add("Maintenance Status: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "Working perfectly" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET));

        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        super.saveNBTData(nbt);
        if (mFluid != null) nbt.setTag("mFluid", mFluid.writeToNBT(new NBTTagCompound()));
        nbt.setLong("mFluidAmount", mFluidAmount);
        nbt.setLong("mFluidAmountMAX", mFluidAmountMAX);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);
        mFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("mFluid"));
        mFluidAmount = nbt.getLong("mFluidAmount");
        mFluidAmountMAX = nbt.getLong("mFluidAmountMAX");
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack var1) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack var1) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack var1) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack var1) {
        return false;
    }

}
