package com.impact.loader;

import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.block.netherportal.BlockHandler;
import com.impact.common.block.netherportal.BlockNullPortal;
import com.impact.common.item.*;
import com.impact.common.te.TE_NqTether;
import com.impact.common.te.TE_SpaceElevatorTether;
import com.impact.common.te.TilePlacedItem;
import com.impact.impact;
import com.impact.mods.asp.ASP;
import com.impact.mods.galacticraft.gg.SpaceDimRegisterer;
import com.impact.mods.gregtech.Basic_Register;
import com.impact.mods.gregtech.GT_ItemRegister;
import com.impact.mods.gregtech.GT_WorldGenRegister;
import com.impact.mods.gregtech.Multi_Register;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gtscanner.GTScanner;
import com.impact.mods.opencomputers.Driver_Register;
import com.impact.mods.railcraft.carts.item.events.Module;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

import static codechicken.nei.api.API.hideItem;
import static com.impact.common.item.Core_List_Items.registerOreDictNames;
import static com.impact.core.Config.DisableNether;
import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;
import static com.impact.impact.getModules;
import static com.impact.loader.ItemRegistery.IGlassBlock;

public class MainLoader {

    private MainLoader() {
    }

    public static void Init(FMLInitializationEvent event) {
        ItemRegistery.run();

        // Register Dimensions in GalacticGregGT5
        if (Loader.isModLoaded("galacticgreg")) {
            SpaceDimRegisterer spaceDimReg = new SpaceDimRegisterer();
            if (spaceDimReg.Init()) {
                spaceDimReg.Register();
                INFO("[load] Space Dimension Register - Loaded");
            }
            WARNING("[load] Space Dimension Register - Not Loaded");
        }

        OreDictRegister.register_all();
        INFO("[load] OreDict Register List - Loaded");

        ASP.load();
        INFO("[load] Solar Panel 2 - Loaded");

        Block_QuantumStuff.run();
        INFO("[load] Quantum Stuff registered");

        for (Module module : getModules()) {
            if (module.getIsActive()) {
                module.load(event);
            }
        }

        Driver_Register.init();
        INFO("[Init] OpenComputers Integration - Loaded");

        impact.proxy.registerRenderInfo();
        INFO("[Init] Item Registery - Loaded");
    }

    public static void preInit(FMLPreInitializationEvent event) {

        ItemRegistery.GregtechPump();
        INFO("[preInit] Gregtech Pump - Loaded");

        ASP.preInit();
        INFO("[preInit] Solar Panel - Loaded");

        Core_Items.getInstance().registerItem();
        INFO("[preInit] Meta Items 1 - Loaded");

        Core_Items2.getInstance().registerItem();
        INFO("[preInit] Meta Items 2 - Loaded");

        Core_Items3.getInstance().registerItem();
        INFO("[preInit] Meta Items 3 - Loaded");

        WoodBrickFormTool.getInstance().registerItem();
        INFO("[preInit] Wood Brick Form Tool - Loaded");

        FakeCircuits.getInstance().registerItem();
        INFO("[preInit] Fake Circuits - Loaded");

        registerOreDictNames();
        INFO("[preInit] Meta Items OreDict List - Loaded");

        ItemRegistery.registerBlocks();
        INFO("[preInit] Meta Blocks - Loaded");

        for (byte i = 0; i <= 7; i++) {
            hideItem(new ItemStack(FakeCircuits.getInstance(), 1, i));
        }
        INFO("[preInit] Hide NEI Items - Loaded");

        if (DisableNether) {
            BlockHandler.replaceBlock(Blocks.portal, BlockNullPortal.class, ItemBlock.class);
            INFO("[preInit] Disabled Nether Portal - Loaded");
        }

        for (Module module : getModules()) {
            if (!module.areRequirementsMet() && module.getIsActive()) {
                module.setIsActive(false);
            }
        }

        for (Module module : getModules()) {
            if (module.getIsActive()) {
                module.init(event);
            }
        }

        GameRegistry.registerTileEntity(TE_SpaceElevatorTether.class, "space_elevator_tether");
        GameRegistry.registerTileEntity(TE_NqTether.class, "nq_tether");
        GameRegistry.registerTileEntity(TilePlacedItem.class, "TilePlacedItem");

        GTScanner.preInit();
    }

    public static void postInit(FMLPostInitializationEvent event) {
        new GT_ItemRegister().run();
        new Casing_Helper().run();
        new Multi_Register().run();
        new Basic_Register().run();
        new GT_WorldGenRegister().run();
        new ModLoader().run();

        for (Module module : getModules()) {
            if (module.getIsActive()) {
                module.postInit(event);
            }
        }
        addRecipeSS();
    }

    private static void addRecipeSS() {
        final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 40), 40);
        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 20), 20);
        inputMap.put(new ItemStack(IGlassBlock, 20), 20);
        inputMap.put(new ItemStack(GCItems.rocketEngine, 4), 4);
        GalacticraftRegistry.registerSpaceStation(
                new SpaceStationType(ConfigManagerCore.idDimensionOverworldOrbit, 0,
                        new SpaceStationRecipe(inputMap)));
    }
}
