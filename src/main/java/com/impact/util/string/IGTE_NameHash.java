package com.impact.util.string;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class IGTE_NameHash {

    private String nameHash;

    public IGTE_NameHash(IGregTechTileEntity iAm) {
        nameHash = "d" + iAm.getWorld().provider.dimensionId + "x" + iAm.getXCoord() + "y" + iAm.getYCoord() + "z" + iAm.getZCoord();
    }

    public IGTE_NameHash(String iAm) {
        nameHash = iAm;
    }

    public String getNameHash() {
        return nameHash;
    }

    public void setNameHash(String nameHash) {
        this.nameHash = nameHash;
    }

    public IGTE_NameHash copy() {
        return new IGTE_NameHash(this.nameHash);
    }

    public static boolean checkHash(IGTE_NameHash first, IGTE_NameHash second) {
        return first.nameHash.equals(second.nameHash);
    }

    public static boolean checkHash(IGTE_NameHash first, IGregTechTileEntity iAm) {
        return first.nameHash.equals("d" + iAm.getWorld().provider.dimensionId + "x" + iAm.getXCoord() + "y" + iAm.getYCoord() + "z" + iAm.getZCoord());
    }
}