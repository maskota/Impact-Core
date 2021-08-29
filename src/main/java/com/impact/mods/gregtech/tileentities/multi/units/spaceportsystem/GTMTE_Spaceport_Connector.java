package com.impact.mods.gregtech.tileentities.multi.units.spaceportsystem;

import com.impact.core.Impact_API;
import com.impact.impact;
import com.impact.mods.gregtech.gui.spaceportsystem.Container_SpacePortSetting;
import com.impact.mods.gregtech.gui.spaceportsystem.GUI_SpacePortSetting;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_GREEN;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static gregtech.api.enums.Dyes.MACHINE_METAL;


public class GTMTE_Spaceport_Connector extends GT_MetaTileEntity_Hatch {

    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public int mTargetD = 0;
    public IGregTechTileEntity tTile = null;
    public int mFrequency = 0;
    public int mTargetFrequency = 0;
    public boolean mConnected;

    public GTMTE_Spaceport mPort;
    public GTMTE_Spaceport mTargetPort;
    public PositionObject mTargetPO;

    public GTMTE_Spaceport_Connector(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 3, 0, new String[]{
                "Connector for Spaceport communication",
                EnumChatFormatting.RED + "▉" + EnumChatFormatting.GRAY + " - Error",
                EnumChatFormatting.GREEN + "▉" + EnumChatFormatting.GRAY + " - All Right",
                Utilits.impactTag()
        });
    }

    public GTMTE_Spaceport_Connector(String aName, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, 3, 0, aDescription, aTextures);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Container_SpacePortSetting(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_SpacePortSetting(aPlayerInventory, aBaseMetaTileEntity, "Spaceport Connector");
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_GREEN, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                /*new GT_RenderedTexture(EM_D_CONN)*/};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                /*new GT_RenderedTexture(EM_D_CONN)*/};
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GTMTE_Spaceport_Connector(mName, mDescriptionArray, mTextures);
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return false;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        if (aSide == aBaseMetaTileEntity.getFrontFacing()) {
            GT_Utility.sendChatToPlayer(aPlayer, "Connection only with Laptop!");
            GT_Utility.sendChatToPlayer(aPlayer, "freq = " + mFrequency);
            GT_Utility.sendChatToPlayer(aPlayer, "connect = " + mConnected);
        }
        return true;
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            getBaseMetaTileEntity().openGUI(aPlayer);
        }
    }

    public void getFrequency(int freq, EntityPlayer aPlayer) {
        this.mFrequency = freq;
        int[] coords = Impact_API.sSpacePortConnector.get(Utilits.inToStringUUID(mFrequency, aPlayer));
        if (coords != null) setCoord(new PositionObject(coords));
//        Impact_API.sSpacePortConnector.clear();
    }

    public void setFrequency(int freq, EntityPlayer aPlayer) {
        this.mFrequency = freq;
        Impact_API.sSpacePortConnector.put(Utilits.inToStringUUID(mFrequency, aPlayer), new PositionObject(getBaseMetaTileEntity()).getCoords());
        GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + EnumChatFormatting.YELLOW + mFrequency);
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && aTick % 20 == 0) {
            boolean active = false;
            if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
                tTile = iAm.getIGregTechTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
            } else {
                World tWorld = DimensionManager.getWorld(this.mTargetD);
                TileEntity te = tWorld != null ? tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ) : null;
                tTile = te instanceof IGregTechTileEntity ? (IGregTechTileEntity) te : null;
            }
            if (tTile != null && tTile.getMetaTileEntity() instanceof GTMTE_Spaceport_Connector) {
                PositionObject thisPO = new PositionObject(getBaseMetaTileEntity());
                if (!PositionObject.checkComparePositionWithDim(new PositionObject(tTile), thisPO)) {
                    GTMTE_Spaceport_Connector connector = (GTMTE_Spaceport_Connector) tTile.getMetaTileEntity();
                    active = mFrequency == connector.mFrequency;
                    setConnect(active);
                    connector.setConnect(active);
                    connector.setCoord(thisPO);
                    connector.getBaseMetaTileEntity().setActive(active);
                    mTargetPort = connector.mPort;
                    mTargetPO = new PositionObject(connector.mPort.getBaseMetaTileEntity());
                }
            }
            iAm.setActive(active);
        }
    }

    public void setCoord(PositionObject pos) {
        this.mTargetX = pos.xPos;
        this.mTargetY = pos.yPos;
        this.mTargetZ = pos.zPos;
        this.mTargetD = pos.dPos;
    }

    public void setPort(GTMTE_Spaceport port) {
        mPort = port;
    }

    public GTMTE_Spaceport getTargetPort() {
        return mTargetPort;
    }

    public PositionObject getTargetPO() {
        return mTargetPO;
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mTargetX", this.mTargetX);
        aNBT.setInteger("mTargetY", this.mTargetY);
        aNBT.setInteger("mTargetZ", this.mTargetZ);
        aNBT.setInteger("mTargetD", this.mTargetD);
        aNBT.setInteger("mFrequency", this.mFrequency);
        aNBT.setInteger("mTargetFrequency", this.mTargetFrequency);
        aNBT.setBoolean("mConnected", this.mConnected);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mTargetX = aNBT.getInteger("mTargetX");
        this.mTargetY = aNBT.getInteger("mTargetY");
        this.mTargetZ = aNBT.getInteger("mTargetZ");
        this.mTargetD = aNBT.getInteger("mTargetD");
        this.mFrequency = aNBT.getInteger("mFrequency");
        this.mTargetFrequency = aNBT.getInteger("mTargetFrequency");
        this.mConnected = aNBT.getBoolean("mConnected");
    }

    public void setConnect(boolean connect) {
        mConnected = connect;
    }
}