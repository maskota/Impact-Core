package com.impact.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.mods.gregtech.gui.impl.Container_SetString;
import com.impact.mods.gregtech.gui.impl.IStringSetter;
import com.impact.network.ImpactNetwork;
import com.impact.network.ImpactPacket;
import com.impact.world.World_Interaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ImpactPacketSetString extends ImpactPacket {

    private int dimID, playerID;
    private String name;

    public ImpactPacketSetString() {
    }

    public ImpactPacketSetString(String name, int dimID, int playerID) {
        this.name = name;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketSetString(String name, EntityPlayer p) {
        this.name = name;
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 6;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeUTF(name);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketSetString(aData.readUTF(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World w = DimensionManager.getWorld(dimID);
        if (w != null && w.getEntityByID(playerID) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) w.getEntityByID(playerID);
            if (player.openContainer instanceof Container_SetString) {
                Container_SetString container = (Container_SetString) player.openContainer;
                if (container.mTileEntity.getMetaTileEntity() instanceof IStringSetter) {
                    IStringSetter te = (IStringSetter) container.mTileEntity.getMetaTileEntity();
                    te.setString(name);
                    World_Interaction.worldInteractionChecker(World_Interaction.SPACEPORT);
                }
            }
        }
    }
}