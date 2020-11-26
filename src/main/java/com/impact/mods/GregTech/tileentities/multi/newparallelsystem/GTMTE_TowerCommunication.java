package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import com.github.technus.tectech.mechanics.constructable.IConstructable;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.impact.client.gui.GUIHandler;
import com.impact.core.Impact_API;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.gui.GT_Container_MultiParallelMachine;
import com.impact.mods.GregTech.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.HashSet;

import static com.github.technus.tectech.mechanics.structure.StructureUtility.*;
import static com.impact.core.Refstrings.MODID;
import static com.impact.mods.GregTech.blocks.Casing_Helper.sCaseCore2;

public class GTMTE_TowerCommunication extends GT_MetaTileEntity_MultiblockBase_EM implements IConstructable {

    private static final String[] description = new String[]{
            EnumChatFormatting.RED + "Impact Details:",
            "- Steel Frame Box",
            "- Communication Tower Casing",
            "- Communication Receiver (" + EnumChatFormatting.RED + "Red Point" + EnumChatFormatting.RESET + ")",
    };
    public static Block CASING = Casing_Helper.sCaseCore2;
    public static byte CASING_META = 12;
    public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    public static int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
    public static int frameId = 4096 + Materials.Steel.mMetaItemSubID;
    public static int frameMeta = GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();
    private static final IStructureDefinition<GTMTE_TowerCommunication> STRUCTURE_DEFINITION =
            StructureDefinition.<GTMTE_TowerCommunication>builder()
                    .addShape("main", new String[][]{
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
                            {"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
                            {"   E   ", "   E   ", "   E   ", "   E   ", "  DED  ", "   E   ", "       ", "       ", "       ", " E   E ", "       ", "       ", "       ", "       ", "E     E", "       ", "       ", " AA~AA ", " AAAAA "},
                            {"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
                            {"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"}
                    })
                    .addElement('A', ofChain(
                            ofHatchAdder(GTMTE_TowerCommunication::addClassicToMachineList, CASING_TEXTURE_ID, sCaseCore2, CASING_META),
                            ofBlock(CASING, CASING_META)))
                    .addElement('D', ofHatchAdder(GTMTE_TowerCommunication::addCommunicationHatchToMachineList, CASING_TEXTURE_ID, sCaseCore2, CASING_META))
                    .addElement('E', ofHintDeferred(() -> new IIcon[]{Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(),
                    }, Materials.Steel.mRGBa))
                    .build();
    public final HashSet<GTMTE_SpaceSatellite_Transmitter> sCommunTransmitter = new HashSet<>();
    public final HashSet<GTMTE_SpaceSatellite_Receiver> sCommunReceiver = new HashSet<>();
    public boolean Stuff;
    public int casingCount = 0;
    public int mFrequency = -1;
    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public int mTargetD = 0;
    public boolean mIsConnect = false;
    public TileEntity tile = null;


    public GTMTE_TowerCommunication(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_TowerCommunication(String aName) {
        super(aName);
    }

    public boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Receiver) {
                ((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sCommunReceiver.add((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity);
            } else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Transmitter) {
                ((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sCommunTransmitter.add((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    @Override
    public IStructureDefinition<GTMTE_TowerCommunication> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TowerCommunication(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == 1 ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Hyper Generator", "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        sCommunReceiver.clear();
        sCommunTransmitter.clear();
        casingCount = 0;
        return structureCheck_EM("main", 3, 17, 3);
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    @Override
    public boolean checkRecipe_EM(ItemStack itemStack) {
        this.mMaxProgresstime = 10;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        this.mEUt = 0;

        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
            if (aBaseMetaTileEntity.isActive()) {
                for (GTMTE_SpaceSatellite_Receiver ph : sCommunReceiver) {
                    if (ph.getBaseMetaTileEntity().isActive())
                        this.mIsConnect = ph.getIsReceive();
                    else this.mIsConnect = false;
                }
            }
        }

        if (aBaseMetaTileEntity.isServerSide()) {
            if ((aTick < 10000 && aTick % 20 == 0) || (aTick % 20 * 600 == 0))
                checkMachine_EM(aBaseMetaTileEntity, null);
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
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("Block Digger")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addOutputBus("Any casing (max x1)")
                .addInputHatch("Any casing (max x1)")
                .addOtherStructurePart("Block of Digger (x1)", "to the very center from below")
                .addCasingInfo("Moon Miner Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public String[] getInfoData() {

        return new String[]{
                "Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt * 256) + EnumChatFormatting.RESET + " EU/t",
                "Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt) + EnumChatFormatting.RESET + " EU/t | Amperes: " + EnumChatFormatting.GREEN + "256" + EnumChatFormatting.RESET + " A",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 3, 17, 3, hintsOnly, stackSize);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (!aPlayer.isSneaking()) {
            aPlayer.openGui(MODID, GUIHandler.GUI_ID_LapTop, this.getBaseMetaTileEntity().getWorld(), this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord());
        }
    }

    public void setFrequency(int aFreq, EntityPlayer aPlayer){
        mFrequency = aFreq;
        Impact_API.sCommunicationTower.put(aFreq, new int[]{getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getWorld().provider.dimensionId});
        GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aFreq);
    }

    public void setCoord(int[] coords) {
        this.mTargetX = coords[0];
        this.mTargetY = coords[1];
        this.mTargetZ = coords[2];
        this.mTargetD = coords[3];
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mTargetX", this.mTargetX);
        aNBT.setInteger("mTargetY", this.mTargetY);
        aNBT.setInteger("mTargetZ", this.mTargetZ);
        aNBT.setInteger("mTargetD", this.mTargetD);
        aNBT.setInteger("mFrequency", this.mFrequency);
        aNBT.setBoolean("mIsReceive", this.mIsConnect);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mTargetX = aNBT.getInteger("mTargetX");
        this.mTargetY = aNBT.getInteger("mTargetY");
        this.mTargetZ = aNBT.getInteger("mTargetZ");
        this.mTargetD = aNBT.getInteger("mTargetD");
        this.mFrequency = aNBT.getInteger("mFrequency");
        this.mIsConnect = aNBT.getBoolean("mIsReceive");
    }

}
