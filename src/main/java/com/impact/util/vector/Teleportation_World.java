package com.impact.util.vector;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class Teleportation_World {

    public static Entity teleportEntity(Entity entity, TeleportPoint tpPoint) {
        boolean changeDim = entity.worldObj.provider.dimensionId != tpPoint.dimID;
        if (changeDim) {
            teleportToDimensionNew(entity, tpPoint);
        } else {
            Entity mount = entity.ridingEntity;
            if (entity.ridingEntity != null) {
                entity.mountEntity(null);
                teleportEntity(mount, tpPoint);
            }

            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) entity;
                player.setPositionAndUpdate(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5);
            } else {
                entity.setPosition(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5);
            }

            entity.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            if (mount != null) {
                entity.mountEntity(mount);
            }
        }

        return entity;
    }

    public static void teleportToDimension(Entity entity, int targetDimID, TeleportPoint tpPoint) {
        if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            int currentDim = entity.worldObj.provider.dimensionId;
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            WorldServer currentServer = minecraftserver.worldServerForDimension(currentDim);
            WorldServer targetServer = minecraftserver.worldServerForDimension(targetDimID);
            player.dimension = targetDimID;
            player.playerNetServerHandler.sendPacket(new S07PacketRespawn(player.dimension, player.worldObj.difficultySetting, player.worldObj.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
            currentServer.removePlayerEntityDangerously(player);
            player.isDead = false;
            player.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            targetServer.theChunkProviderServer.loadChunk((int) tpPoint.x >> 4, (int) tpPoint.z >> 4);
            targetServer.spawnEntityInWorld(player);
            targetServer.updateEntityWithOptionalForce(player, false);
            player.setWorld(targetServer);
            if (currentServer != null) {
                currentServer.getPlayerManager().removePlayer(player);
            }

            targetServer.getPlayerManager().addPlayer(player);
            targetServer.theChunkProviderServer.loadChunk((int) player.posX >> 4, (int) player.posZ >> 4);
            player.playerNetServerHandler.setPlayerLocation(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            player.theItemInWorldManager.setWorld(targetServer);
            player.mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(player, targetServer);
            player.mcServer.getConfigurationManager().syncPlayerInventory(player);
            FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, currentDim, targetDimID);
            player.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            targetServer.updateEntityWithOptionalForce(player, true);
        }

    }

    public static Entity teleportToDimensionNew(Entity entity, TeleportPoint tpPoint) {
        if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            Entity mount = entity.ridingEntity;
            if (entity.ridingEntity != null) {
                entity.mountEntity((Entity) null);
                mount = teleportToDimensionNew(mount, tpPoint);
            }

            tpPoint.y += 0.5D;
            int currentDim = entity.worldObj.provider.dimensionId;
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            WorldServer currentServer = minecraftserver.worldServerForDimension(currentDim);
            WorldServer targetServer = minecraftserver.worldServerForDimension(tpPoint.dimID);
            currentServer.updateEntityWithOptionalForce(entity, false);
            EntityPlayerMP player;
            if (entity instanceof EntityPlayerMP) {
                player = (EntityPlayerMP) entity;
                player.dimension = tpPoint.dimID;
                player.playerNetServerHandler.sendPacket(new S07PacketRespawn(player.dimension, player.worldObj.difficultySetting, player.worldObj.getWorldInfo().getTerrainType(), player.theItemInWorldManager.getGameType()));
                currentServer.removePlayerEntityDangerously(player);
                player.isDead = false;
            } else {
                entity.dimension = tpPoint.dimID;
                entity.isDead = false;
            }

            entity.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            targetServer.theChunkProviderServer.loadChunk((int) tpPoint.x >> 4, (int) tpPoint.z >> 4);
            targetServer.spawnEntityInWorld(entity);
            targetServer.updateEntityWithOptionalForce(entity, false);
            entity.setWorld(targetServer);
            if (!(entity instanceof EntityPlayerMP)) {
                NBTTagCompound player1 = new NBTTagCompound();
                entity.isDead = false;
                entity.writeToNBTOptional(player1);
                entity.isDead = true;
                entity = EntityList.createEntityFromNBT(player1, targetServer);
                if (entity == null) {
                    return null;
                }

                entity.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
                targetServer.spawnEntityInWorld(entity);
                entity.setWorld(targetServer);
                entity.dimension = tpPoint.dimID;
            }

            if (entity instanceof EntityPlayerMP) {
                player = (EntityPlayerMP) entity;
                if (currentServer != null) {
                    currentServer.getPlayerManager().removePlayer(player);
                }

                targetServer.getPlayerManager().addPlayer(player);
                targetServer.theChunkProviderServer.loadChunk((int) player.posX >> 4, (int) player.posZ >> 4);
                targetServer.updateEntityWithOptionalForce(entity, false);
                player.playerNetServerHandler.setPlayerLocation(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
                player.theItemInWorldManager.setWorld(targetServer);
                player.mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(player, targetServer);
                player.mcServer.getConfigurationManager().syncPlayerInventory(player);
                FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, currentDim, tpPoint.dimID);
                player.setPositionAndUpdate(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5);
                player.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            }

            entity.setLocationAndAngles(tpPoint.x + 0.5, tpPoint.y + 1.5, tpPoint.z + 0.5, (float) tpPoint.yaw, (float) tpPoint.pitch);
            if (mount != null) {
                if (entity instanceof EntityPlayerMP) {
                    targetServer.updateEntityWithOptionalForce(entity, true);
                }

                entity.mountEntity(mount);
                targetServer.updateEntities();
                teleportEntity(entity, tpPoint);
            }
        }

        return entity;
    }


}
