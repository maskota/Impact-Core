package com.impact.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

    public static void register_event(Object obj) {
        FMLCommonHandler.instance().bus().register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }

    public void addTexturePage() {
    }

    public void registerRenderInfo() {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public World getClientWorld() {
        return null;
    }

    public void registerRenderers() {
    }

    public void preInit() {
    }

    public void init() {
    }

    public void postInit() {
    }

    public void hint_particle(World w, int x, int y, int z, Block block, int meta) {
    }

    public void hint_particle(World w, int x, int y, int z, Block block, int meta, int age) {
    }

    public void hint_particle(World w, int x, int y, int z, double xx, double yy, double zz, Block block, int meta, int age) {
    }

    public void beam(World worldObj, double sx, double sy, double sz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, int age) {
    }

    public Object beamBore(World worldObj, double px, double py, double pz, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
        return null;
    }

    public Object beamCont(World worldObj, EntityPlayer p, double tx, double ty, double tz, int type, int color, boolean reverse, float endmod, Object input, int impact) {
        return null;
    }

    public void onServerStarted() {
        Impact_API.sSpaceSatellite.clear();
        Impact_API.sElevatorSpace.clear();
        Impact_API.sCommunicationTower.clear();
        Impact_API.sAerostat.clear();
    }

    public void onServerStopping() {
        Impact_API.sSpaceSatellite.clear();
        Impact_API.sElevatorSpace.clear();
        Impact_API.sCommunicationTower.clear();
        Impact_API.sAerostat.clear();
    }
}