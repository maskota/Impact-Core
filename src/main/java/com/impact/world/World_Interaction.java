package com.impact.world;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Spaceport;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum World_Interaction {

    AEROSTAT, SPACEPORT, ALL;

    public static List<GTMTE_Spaceport> World_SpacePort = new LinkedList<>();
    public static List<GTMTE_Aerostat> World_AeroStat = new LinkedList<>();

    public static boolean check(World_Interaction wInChecker, World_Interaction ws) {
        return wInChecker.equals(ws) || wInChecker.equals(ALL);
    }

    public static void clearWorld(World_Interaction w) {
        if (check(w, SPACEPORT)) World_SpacePort.clear();
//        if (check(w, AEROSTAT)) World_AeroStat.clear();
    }

    public static void worldInteractionChecker(World_Interaction w) {
        IGregTechTileEntity gte;
        clearWorld(w);
        if (FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            WorldServer[] servers = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;

            for (WorldServer world : servers) {
                for (TileEntity te : new ArrayList<TileEntity>(world.loadedTileEntityList)) {
                    te = world.getTileEntity(te.xCoord, te.yCoord, te.zCoord);
                    gte = te instanceof IGregTechTileEntity ? (IGregTechTileEntity) te : null;
                    if (gte != null) {
                        IMetaTileEntity mte = gte.getMetaTileEntity();
                        //SpacePort Checker
                        if (check(w, SPACEPORT) && mte instanceof GTMTE_Spaceport) {
                            GTMTE_Spaceport spaceport = (GTMTE_Spaceport) mte;
                            if (!spaceport.name.isEmpty()) World_SpacePort.add(spaceport);
                        }
                        //AeroStat Checker
//                        if (check(w, AEROSTAT) && mte instanceof GTMTE_Aerostat) {
//                            GTMTE_Aerostat aeroStat = (GTMTE_Aerostat) mte;
//                            World_AeroStat.add(aeroStat);
//                        }
                    }
                }
            }
        }
    }
}