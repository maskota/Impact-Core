package com.impact.util;

import com.impact.util.vector.TeleportPoint;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PositionObject {
	
	public int xPos = 0;
	public int yPos = 0;
	public int zPos = 0;
	public int dPos = 0;
	public EntityPlayer player = null;
	public String playerName = "";
	public String nameLocation = "";
	
	/**
	 * @param igt - IGregTechTileEntity
	 */
	public PositionObject(IGregTechTileEntity igt) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	/**
	 * @param x - x Position
	 * @param y - y Position
	 * @param z - z Position
	 */
	public PositionObject(int x, int y, int z) {
		xPos = x;
		yPos = y;
		zPos = z;
	}

	public PositionObject(int x, int y, int z, int d) {
		xPos = x;
		yPos = y;
		zPos = z;
		dPos = d;
	}
	
	/**
	 * only 4 coords!
	 * @param coords - x, y, z, d Position
	 */
	public PositionObject(int[] coords) {
		xPos = coords[0];
		yPos = coords[1];
		zPos = coords[2];
		dPos = coords[3];
	}
	
	/**
	 * @param igt - IGregTechTileEntity
	 * @param name - Name Position
	 */
	public PositionObject(IGregTechTileEntity igt, String name) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		nameLocation = name;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	/**
	 * @param obj - PositionObject copy method
	 */
	private PositionObject(PositionObject obj) {
		xPos = obj.xPos;
		yPos = obj.yPos;
		zPos = obj.zPos;
		dPos = obj.dPos;
		player = obj.player;
		playerName = obj.playerName;
		nameLocation = obj.nameLocation;
	}
	
	/**
	 * @return Integer Array with coords
	 */
	public int[] getCoords() {
		return new int[] {xPos, yPos, zPos, dPos};
	}
	
	/**
	 * @return PositionObject copy
	 */
	public PositionObject copy() {
		return new PositionObject(this);
	}

	public static TeleportPoint toTeleportPoint(PositionObject p) {
		TeleportPoint tp = new TeleportPoint();
		tp.dimID = p.dPos;
		tp.x = p.xPos;
		tp.y = p.yPos;
		tp.z = p.zPos;
		return tp;
	}

	public TeleportPoint toTeleportPoint() {
		TeleportPoint tp = new TeleportPoint();
		tp.dimID = this.dPos;
		tp.x = this.xPos;
		tp.y = this.yPos;
		tp.z = this.zPos;
		return tp;
	}

	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePosition(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.xPos == obj2.xPos && obj1.yPos == obj2.yPos && obj1.zPos == obj2.zPos;
	}
	
	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePositionWithDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos && checkComparePosition(obj1, obj2);
	}
	
	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePositionOnlyDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos;
	}
	
	/**
	 * @param igt - IGregTechTileEntity
	 * @param obj - PositionObject source
	 * @return IGregTechTileEntity
	 */
	public static IGregTechTileEntity getIGregTechTileEntity(IGregTechTileEntity igt, PositionObject obj) {
		if (obj == null) return null;
		return igt.getIGregTechTileEntity(obj.xPos, obj.yPos, obj.zPos);
	}

	public static IGregTechTileEntity isGT(PositionObject pos) {
		TileEntity te = DimensionManager.getWorld(pos.dPos).getTileEntity(pos.xPos, pos.yPos, pos.zPos);
		if (te instanceof IGregTechTileEntity) {
			return (IGregTechTileEntity) te;
		}
		return null;
	}

	public void saveNBT(NBTTagCompound aNBT) {
		aNBT.setInteger("poX", this.xPos);
		aNBT.setInteger("poY", this.yPos);
		aNBT.setInteger("poZ", this.zPos);
		aNBT.setInteger("poD", this.dPos);
	}

	public void loadFromNBT(NBTTagCompound aNBT) {
		this.xPos = aNBT.getInteger("poX");
		this.yPos = aNBT.getInteger("poY");
		this.zPos = aNBT.getInteger("poZ");
		this.dPos = aNBT.getInteger("poD");
	}
}