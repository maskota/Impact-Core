package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static micdoodle8.mods.galacticraft.core.util.ConfigManagerCore.disableSpaceStationCreation;

public class GTMTE_SpaceSatellite extends GT_MetaTileEntity_MultiParallelBlockBase {

    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 12;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    //region Register
    public GTMTE_SpaceSatellite(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_SpaceSatellite(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        return new GTMTE_SpaceSatellite(this.mName);
    }
    //endregion

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }

    public void run() {
        registerMetaClass(GTMTE_SpaceSatellite.class, new IMultiblockInfoContainer<GTMTE_SpaceSatellite>() {
            //region Structure
            private final IStructureDefinition<GTMTE_SpaceSatellite> definition =
                    StructureDefinition.<GTMTE_SpaceSatellite>builder()
                            .addShape("main", new String[][]{
                                    {" A A "," A A "," A A "," A A "},
                                    {" A A ","AAAAA","AA~AA","AAAAA"},
                                    {" A A ","AAAAA","AAAAA","AAAAA"},
                                    {" A A "," A A "," A A "," A A "},
                            })
                            .addElement('A', ofBlock(CASING, CASING_META))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    "- Space Satellite Casing",
            };

            //endregion
            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_SpaceSatellite tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        2, 2, 1, hintsOnly);
            }

            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("Space Satellite")
                .addInfo(disableSpaceStationCreation ?
                        "Installation on the Moon required" :
                        "Installation on the Space Station required")
                .addInfo("ватафак? " + disableSpaceStationCreation)

                .addController()
                .addEnergyHatch("Any casing")
                .addOtherStructurePart("Communication Transmitter", "Any casing")
                .addCasingInfo("Space Satellite Casing")
                .signAndFinalize("IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        this.mMaxProgresstime = 10;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        this.mEUt = -16;
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (aTick % 20 == 0) {
                for (GTMTE_SpaceSatellite_Transmitter th : sCommunTransmitter) {
                    if (aBaseMetaTileEntity.isActive()) {
                        th.getBaseMetaTileEntity().setActive(true);
                        th.setIsTransmit(true);
                    } else {
                        th.getBaseMetaTileEntity().setActive(false);
                        th.setIsTransmit(false);
                    }
                }
            }
            if (aTick % 20 * 60 == 0) {
                this.mWrench = true;
                this.mScrewdriver = true;
                this.mSoftHammer = true;
                this.mHardHammer = true;
                this.mSolderingTool = true;
                this.mCrowbar = true;
            }
        }
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack aStack) {
        int dimId = thisController.getWorld().provider.dimensionId;
        boolean debug = true;
        if (debug) {
            if (!disableSpaceStationCreation) {
                if (!(DimensionManager.getProvider(dimId).getClass().getName().contains("Orbit")
                        || DimensionManager.getProvider(dimId).getClass().getName().endsWith("Space")
                        || DimensionManager.getProvider(dimId).getClass().getName().endsWith("SS")
                        || DimensionManager.getProvider(dimId).getClass().getName().contains("SpaceStation")))
                    return false;
            } else if (!DimensionManager.getProvider(dimId).getClass().getName().contains("Moon"))
                return false;
        }
        //region Structure
        TThatches();
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        boolean formationChecklist = true;

        for (int X = -2; X <= 2; X++) {
            for (int Y = -1; Y <= 2; Y++) {
                for (int Z = 1; Z >= -2; Z--) {
                    if (X == 0 && Y == 0 && Z == 0) continue;

                    if ((Z == 1 || Z == -2) && !(X == -1 || X == 1)) continue;
                    if (Y == 2 && !(X == -1 || X == 1)) continue;

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                    if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addCommunicationHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }
        //endregion

        return formationChecklist;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }
}
