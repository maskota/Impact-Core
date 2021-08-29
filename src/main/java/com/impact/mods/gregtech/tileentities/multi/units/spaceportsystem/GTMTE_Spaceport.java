package com.impact.mods.gregtech.tileentities.multi.units.spaceportsystem;

import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.impl.IStringSetter;
import com.impact.mods.gregtech.gui.spaceportsystem.Container_SpacePort;
import com.impact.mods.gregtech.gui.spaceportsystem.GUI_SpacePort;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.vector.TeleportPoint;
import com.impact.util.vector.Teleportation_World;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.impact.core.Impact_API.sSpacePortConnector;

public class GTMTE_Spaceport extends GT_MetaTileEntity_MultiParallelBlockBase implements IStringSetter {

    public PositionObject targetPO;
    public GTMTE_Spaceport targetPort;
    public boolean mCheckCount = true;

    public FluidStack rocketFuel;
    public long rocketFuelAmount = 0;

    public List<GTMTE_Spaceport_Connector> mSpaceport_connectors = new ArrayList<>();

    public String name = "";
    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 14;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    public GTMTE_Spaceport(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_Spaceport(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity iAm, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE
                        : Texture.Icons.OVERLAY_SPACE_ELEVATOR)}
                : new ITexture[]{INDEX_CASE};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iAm) {

        return new GTMTE_Spaceport(this.mName);
    }

    @Override
    public String[] getDescription() {
//        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
//        b
//                .addInfo("space_elevator.info.0")
//                .addTypeMachine("space_elevator.name")
//                .addInfo("space_elevator.info.1")
//                .addInfo("space_elevator.info.2")
//                .addInfo("space_elevator.info.3")
//                .addController()
//                .addEnergyHatch("space_elevator.hatches")
//                .addCasingInfo("space_elevator.case")
//                .addOtherStructurePart("space_elevator.other.0", "space_elevator.other.1")
//                .signAndFinalize();
//        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//            return b.getInformation();
//        } else {
//            return b.getStructureInformation();
//        }
        return new String[]{"a"}; //// TODO: 05.08.2021
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_SpacePort(aPlayerInventory, aBaseMetaTileEntity, name);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Container_SpacePort(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setString("name", name);
        aNBT.setBoolean("mCheckCount", mCheckCount);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        name = aNBT.getString("name");
        mCheckCount = aNBT.getBoolean("mCheckCount");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

        mSpaceport_connectors.clear();

        boolean formationChecklist = true;

        for (int X = 1; X <= 3; X++) {

            final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0,  0);
            IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

            if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                    && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                    && !addSpaceportConnector(currentTE, CASING_TEXTURE_ID)
                    && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                        && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                } else {
                    formationChecklist = false;
                }
            }
        }

        return formationChecklist;
    }

    public boolean addSpaceportConnector(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GTMTE_Spaceport_Connector) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mSpaceport_connectors.add((GTMTE_Spaceport_Connector) aMetaTileEntity);
        }
        return false;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && mMachine && mSpaceport_connectors.size() > 0) {

            if (aTick % 20 == 0) {
                GTMTE_Spaceport_Connector connector = mSpaceport_connectors.get(0);
                connector.setPort(this);
                this.mIsConnect = connector.mConnected;
                if (connector.getTargetPort() != null) {
                    targetPort = connector.getTargetPort();
                    targetPO = connector.getTargetPO();
                }
                if (targetPort == null || targetPO == null) return;
            }

            if (!mIsConnect) {
                mFrequency = 0;
            }

            int aMinute = 20 * 2;
            int randomTick = new Random().nextInt(60) * 10;
            if (aTick % 20 == 0 && mCheckCount && mIsConnect) {
                mFrequency = randomTick + aMinute;
                //Utilits.sendChatByTE(getBaseMetaTileEntity(), "Полет начался: " + mFrequency);
                mCheckCount = false;
            }

            if (!mCheckCount && mFrequency > 0 && mIsConnect) {
                --mFrequency;
            }

            if (mFrequency == 0 && !mCheckCount) {
                rocketFuelAmount = 5000; //todo debug

                if (rocketFuelAmount >= calcFuel()) {

                    rocketFuelAmount -= calcFuel();

                    List<ItemStack> items = new ArrayList<>();
                    List<FluidStack> fluids = new ArrayList<>();


                    if (mInputBusses.size() > 0) {
                        for (int i = 0; i < mInputBusses.get(0).mInventory.length; i++) {
                            if (mInputBusses.get(0).mInventory[0] != null && mInputBusses.get(0).mInventory[0].stackSize > 0) {
                                items.add(mInputBusses.get(0).mInventory[0]);
                                depleteInput(mInputBusses.get(0).mInventory[0]);
                                mInputBusses.get(0).updateSlots();
                            }
                        }
                    }

                    if (mInputHatches.size() > 0) {
                        if (mInputHatches.get(0).mFluid != null) {
                            fluids.add(mInputHatches.get(0).mFluid.copy());
                            depleteInput(mInputHatches.get(0).mFluid);
                        }
                    }

                    for (ItemStack is : items) targetPort.addOutput(is.copy());
                    for (FluidStack fs : fluids) targetPort.addOutput(fs.copy());

                    items.clear();
                    fluids.clear();
                    //Utilits.sendChatByTE(iAm, "Полет выполнен!");
                    mCheckCount = true;
                }
            }
        }
    }

    public int calcFuel() {
        return 1000; //todo refactor
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (!aPlayer.isSneaking()) {
            Utilits.openTileGui(aPlayer, GUIHandler.GUI_ID_SetString, getBaseMetaTileEntity());
        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        for (String a :sSpacePortConnector.keySet()) {
            GT_Utility.sendChatToPlayer(aPlayer, "" + a);
        }
        //GT_Utility.sendChatToPlayer(aPlayer, "");
    }

    public void teleportEntity(EntityPlayer player) {
        TeleportPoint point = PositionObject.toTeleportPoint(targetPO);
        PositionObject tPos = new PositionObject(getBaseMetaTileEntity());
        player.closeScreen();
//        if (tPos.dPos != point.dimID) {
            Teleportation_World.teleportEntity(player, point);
            GT_Utility.sendChatToPlayer(player, "Teleportation successful");
//            return;
//        }
//        GT_Utility.sendChatToPlayer(player, "Teleportation failed");
    }

    @Override
    public void setString(String str) {
        name = str;
    }
}