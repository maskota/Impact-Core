package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import com.impact.core.Impact_API;
import com.impact.mods.GregTech.gui.Container_SpaceSatelliteHatches;
import com.impact.mods.GregTech.gui.GUI_SpaceSatelliteHathes;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

import java.util.Random;

import static com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DataConnector.EM_D_CONN;
import static com.impact.mods.GregTech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.GregTech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_SpaceSatellite_Transmitter extends GT_MetaTileEntity_Hatch {

    //todo Передатчик
    public boolean mIsTransmit = false;
    public int mFrequency = 0;

    public GTMTE_SpaceSatellite_Transmitter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 3, 0, new String[]{
                Utilits.impactTag(),
                "Transmission of signals from orbit",
                "Used in Space Satellite",
                EnumChatFormatting.RED + "▉" + EnumChatFormatting.GRAY + " - Error",
                EnumChatFormatting.YELLOW + "▉" + EnumChatFormatting.GRAY + " - All Right"
        });
    }

    public GTMTE_SpaceSatellite_Transmitter(String aName, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, 3, 0, aDescription, aTextures);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GTMTE_SpaceSatellite_Transmitter(mName, mDescriptionArray, mTextures);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Container_SpaceSatelliteHatches(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_SpaceSatelliteHathes(aPlayerInventory, aBaseMetaTileEntity, "Communication Transmitter");
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(EM_D_CONN)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture,
                new GT_RenderedTexture(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
                new GT_RenderedTexture(EM_D_CONN)};
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
    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return false;
    }

    public void setIsTransmit(boolean aIsTransmit) {
        mIsTransmit = aIsTransmit;
    }

    public boolean getIsTransmit() {
        return mIsTransmit;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        if (aSide == aBaseMetaTileEntity.getFrontFacing())
            GT_Utility.sendChatToPlayer(aPlayer, "Connection only with Laptop!");
        return true;
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            GT_Utility.sendChatToPlayer(aPlayer, "Check: " + mIsTransmit);
        }
    }

    @Override
    public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
        if (getBaseMetaTileEntity().isServerSide()) {
            getBaseMetaTileEntity().openGUI(aPlayer);
        }
    }

    public void setFrequency(int freq, EntityPlayer aPlayer) {
        Impact_API.sSpaceSatellite.put(freq, new int[]{getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getWorld().provider.dimensionId});
        GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + EnumChatFormatting.YELLOW + freq);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("mIsTransmit", this.mIsTransmit);
        aNBT.setInteger("mFrequency", this.mFrequency);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mIsTransmit = aNBT.getBoolean("mIsTransmit");
        this.mFrequency = aNBT.getInteger("mFrequency");
    }
}
