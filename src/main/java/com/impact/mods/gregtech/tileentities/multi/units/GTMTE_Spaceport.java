package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.World_Interaction;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.spaceport.Countainer_SpacePort;
import com.impact.mods.gregtech.gui.spaceport.GUI_SpacePort;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.IGTE_NameHash;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GTMTE_Spaceport extends GT_MetaTileEntity_MultiParallelBlockBase {

    public int aerID = 1;
    public int curID = 1;

    public PositionObject targetSpacePort;
    public List<GTMTE_Spaceport> spacePortWorldOwner = new LinkedList<>();
    public static ArrayList<String> owners = new ArrayList<>();

    public String nameHash = "";

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
    public ITexture[] getTexture(final IGregTechTileEntity iAm, final byte aSide,
                                 final byte aFacing,
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
        return new GUI_SpacePort(aPlayerInventory, aBaseMetaTileEntity, nameHash);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Countainer_SpacePort(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("aerID", aerID);
        aNBT.setInteger("curID", curID);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        curID = aNBT.getInteger("curID");
        aerID = aNBT.getInteger("aerID");
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
            if (iAm.getRedstone())  {
                if (!PositionObject.checkComparePosition(targetSpacePort, new PositionObject(iAm))) {
                    teleportEntity(iAm);
                } else Utilits.sendChatByTE(iAm, "на тот же незя");
            }
        }
        // TODO: 05.08.2021 Получить при старте мира загруженые машины и
        //  сделать им статические ID чтобы можно было найти из списка
        //  по тому же методу что и всегда найти машину (из полученного списка машин - аррей спайспортов)
        //  добавить публичную станцию или нет (проверку овнера, если есть то приват, нету - паблик)
        //  Также сделать при старте для все частот статическую функцию, которая будет записывать в свой список все что нужно
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        //GT_Utility.sendChatToPlayer(aPlayer, "");


    }

    @Override
    public void inValidate() {
        World_Interaction.worldInteractionChecker(World_Interaction.SPACEPORT);
        checkOnlyOwnerSpacePorts();
        super.inValidate();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity iAm) {
        super.onFirstTick(iAm);
        nameHash = new IGTE_NameHash(iAm).getNameHash();
        World_Interaction.worldInteractionChecker(World_Interaction.SPACEPORT);
        checkOnlyOwnerSpacePorts();
    }

    public void checkOnlyOwnerSpacePorts() {
        spacePortWorldOwner.clear();
        for (GTMTE_Spaceport port : World_Interaction.World_SpacePort) {
            if (!port.nameHash.equals(nameHash)) spacePortWorldOwner.add(port);
        }
    }

    public boolean checkCurrentDimension(int dim) {
        return dim == getBaseMetaTileEntity().getWorld().provider.dimensionId;
    }

    public boolean isDimensionalTeleportAvailable() {
        return GT_Utility.isRealDimension(this.mTargetD) && GT_Utility.isRealDimension(getBaseMetaTileEntity().getWorld().provider.dimensionId);
    }

    public void teleportEntity(IGregTechTileEntity iAm) {

        GTMTE_Spaceport destPort =  spacePortWorldOwner.get(aerID - 1);
        if (destPort != null) {

            int a = World_Interaction.World_SpacePort.indexOf(destPort);
            int b = World_Interaction.World_SpacePort.indexOf(this);

            Utilits.sendChatByTE(iAm, "destPort: " + a + " this:  " + b);

            PositionObject destPortPos = new PositionObject(destPort.getBaseMetaTileEntity());

            List entities_in_box = iAm.getWorld().getEntitiesWithinAABB(Entity.class, Utilits.setBoxAABB(iAm, 1.5));
            for (Object tObject : entities_in_box) {
                if (((tObject instanceof Entity)) && (!((Entity) tObject).isDead)) {
                    Entity tEntity = (Entity) tObject;
//                    if (tEntity instanceof EntityPlayer) {
//                        EntityPlayer player = (EntityPlayer) tEntity;
//                        player.setPositionAndUpdate(destPortPos.xPos + 0.5D, destPortPos.yPos + 1, destPortPos.zPos + 0.5D);
//                    }

                    if (tEntity.ridingEntity != null) {
                        tEntity.mountEntity(null);
                    }
                    if (tEntity.riddenByEntity != null) {
                        tEntity.riddenByEntity.mountEntity(null);
                    }
                    if ((checkCurrentDimension(destPortPos.dPos) || (!isDimensionalTeleportAvailable()) ||
                            (!GT_Utility.moveEntityToDimensionAtCoords(tEntity, destPortPos.dPos, destPortPos.xPos + 0.5D, destPortPos.yPos + 1.5D, destPortPos.zPos + 0.5D)))) {
                        if ((tEntity instanceof EntityLivingBase)) {
                            ((EntityLivingBase) tEntity).setPositionAndUpdate(destPortPos.xPos + 0.5D, destPortPos.yPos + 1.5D, destPortPos.zPos + 0.5D);
                        } else {
                            tEntity.setPosition(destPortPos.xPos + 0.5D, destPortPos.yPos + 1.5D, destPortPos.zPos + 0.5D);
                        }
                    }
                }
            }
        }
    }


}
