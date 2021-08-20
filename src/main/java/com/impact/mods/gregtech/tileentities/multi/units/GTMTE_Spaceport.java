package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.impl.IStringSetter;
import com.impact.mods.gregtech.gui.spaceport.Container_SpacePort;
import com.impact.mods.gregtech.gui.spaceport.GUI_SpacePort;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.vector.TeleportPoint;
import com.impact.util.vector.Teleportation_World;
import com.impact.world.World_Interaction;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class GTMTE_Spaceport extends GT_MetaTileEntity_MultiParallelBlockBase implements IStringSetter {

    public PositionObject targetSpacePort;
    public ArrayList<String> owners = new ArrayList<>();

    public String name = "";
    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 14;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
    private boolean firstChecker = true;

    public GTMTE_Spaceport(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_Spaceport(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity iAm, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == 1
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
        if (targetSpacePort != null) targetSpacePort.saveNBT(aNBT);
        aNBT.setString("name", name);
        aNBT.setBoolean("firstChecker", firstChecker);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        targetSpacePort.loadFromNBT(aNBT);
        name = aNBT.getString("name");
        firstChecker = aNBT.getBoolean("firstChecker");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide()) {

        }
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        //GT_Utility.sendChatToPlayer(aPlayer, "");
    }

    @Override
    public void inValidate() {
        World_Interaction.worldInteractionChecker(World_Interaction.SPACEPORT);
        super.inValidate();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity iAm) {
        super.onFirstTick(iAm);
        World_Interaction.worldInteractionChecker(World_Interaction.SPACEPORT);
    }

    public String getDimensionName() {
        return getBaseMetaTileEntity().getWorld().provider.getDimensionName();
    }

    @Override
    public void onNotePadRightClick(ItemStack stack, byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(stack, aSide, aPlayer, aX, aY, aZ);
        Utilits.openTileGui(aPlayer, GUIHandler.GUI_ID_SetString, getBaseMetaTileEntity());
        if (getBaseMetaTileEntity().isServerSide()) {
            NBTTagCompound tNBT = stack.getTagCompound();
            if (firstChecker) {
                PositionObject thisPO = new PositionObject(getBaseMetaTileEntity());
                tNBT.setInteger("mXCoordIn", thisPO.xPos);
                tNBT.setInteger("mYCoordIn", thisPO.yPos);
                tNBT.setInteger("mZCoordIn", thisPO.zPos);
                tNBT.setInteger("mDCoordIn", thisPO.dPos);
                firstChecker = false;
            } else {
                targetSpacePort.xPos = tNBT.getInteger("mXCoordIn");
                targetSpacePort.yPos = tNBT.getInteger("mYCoordIn");
                targetSpacePort.zPos = tNBT.getInteger("mZCoordIn");
                targetSpacePort.dPos = tNBT.getInteger("mDCoordIn");
            }
        }
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        return super.onRightclick(aBaseMetaTileEntity, aPlayer);
    }

    public int getTargetIDPort() {
        int id = 0;
        for (; id < World_Interaction.World_SpacePort.size(); id++) {
            PositionObject po = new PositionObject(World_Interaction.World_SpacePort.get(id).getBaseMetaTileEntity());
            if (PositionObject.checkComparePositionWithDim(po, targetSpacePort)) {
                return id;
            }
        }
        return -1;
    }

    public void teleportEntity(Entity entity) {
        TeleportPoint point = PositionObject.toTeleportPoint(targetSpacePort);
        PositionObject tPos = new PositionObject(getBaseMetaTileEntity());
        if (tPos.dPos != point.dimID) Teleportation_World.teleportEntity(entity, point);
    }

    @Override
    public void setString(String str) {
        name = str;
    }
}