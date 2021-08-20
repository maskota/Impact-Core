package com.impact.mods.gregtech.items;

import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Spaceport;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_None;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_Spaceport extends Behaviour_None {

    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Spaceport();

    protected MetaTileEntity mMetaTileEntity;

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        NBTTagCompound tNBT = new NBTTagCompound();
        TileEntity te = aWorld.getTileEntity(aX, aY, aZ);
        if (aPlayer instanceof EntityPlayerMP && te instanceof IGregTechTileEntity) {
            IGregTechTileEntity gte = (IGregTechTileEntity) te;
            IMetaTileEntity imte = gte.getMetaTileEntity();
            if (imte instanceof GTMTE_Spaceport/* && ((GTMTE_Spaceport) imte).mMachine*/) {
                if (!aPlayer.isSneaking()) {

                    PositionObject op = new PositionObject(gte);
                    tNBT.setInteger("xPos", op.xPos);
                    tNBT.setInteger("yPos", op.yPos);
                    tNBT.setInteger("zPos", op.zPos);
                    tNBT.setInteger("dPos", op.dPos);
                    GT_Utility.sendChatToPlayer(aPlayer, "Save position");
                    Utilits.openTileGui(aPlayer, GUIHandler.GUI_ID_SetString, gte);
                } else {
                    GTMTE_Spaceport port = ((GTMTE_Spaceport) imte);
                    try {
                        int x = tNBT.getInteger("xPos");
                        int y = tNBT.getInteger("yPos");
                        int z = tNBT.getInteger("zPos");
                        int d = tNBT.getInteger("dPos");
                        port.targetSpacePort = new PositionObject(x, y, z, d);
                        port.startConnection();
                        GT_Utility.sendChatToPlayer(aPlayer, "Connection successful to " + port.name);

                    } catch (Exception ignored) {
                        GT_Utility.sendChatToPlayer(aPlayer, "Connection failed");
                    }
                }
            }
            GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(108), 1, 1.0F, aX, aY, aZ);
            return true;
        }
        return aPlayer instanceof EntityPlayerMP;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add("Shift + RClick - Set Position Spaceport");
        aList.add("RClick - Get Position Spaceport");
        try {
            NBTTagCompound tNBT = aStack.getTagCompound();
            aList.add("x " + tNBT.getInteger("xPos"));
            aList.add("y " + tNBT.getInteger("yPos"));
            aList.add("z " + tNBT.getInteger("zPos"));
            aList.add("d " + tNBT.getInteger("dPos"));
        }catch (Exception e){}

        return aList;
    }

}
