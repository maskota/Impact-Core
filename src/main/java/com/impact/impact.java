package com.impact;

import com.impact.client.gui.GUIHandler;
import com.impact.core.CommonProxy;
import com.impact.core.Config;
import com.impact.core.Refstrings;
import com.impact.events.PacketHandler;
import com.impact.events.TickHandler;
import com.impact.events.impactEvents;
import com.impact.loader.MainLoader;
import com.impact.mods.GregTech.enums.IRecipeAdder;
import com.impact.mods.GregTech.enums.RecipeAdder;
import com.impact.mods.GregTech.enums.Texture;
import com.impact.mods.NEI.OrePugin.helper.CSVMaker;
import com.impact.mods.NEI.OrePugin.helper.GT5OreLayerHelper;
import com.impact.mods.NEI.OrePugin.helper.GT5OreSmallHelper;
import com.impact.mods.RailCraft.carts.item.ChestCartModule;
import com.impact.mods.RailCraft.carts.item.events.Module;
import com.impact.util.SendUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.ArrayList;

import static com.impact.core.Config.csv;
import static com.impact.core.Refstrings.MODID;
import static com.impact.core.impactLog.INFO;

@Mod(
        modid = MODID,
        name = Refstrings.NAME,
        version = Refstrings.VERSION,
        dependencies =
                "required-after:Forge@[10.13.2.1291,);")

public class impact {

    @SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static impact instance;
    public static SendUtils SendUtils_instance = new SendUtils();
    public static String ModPackVersion = "1.0 Release [DEV]";
    public static Config mConfig;
    public static FMLEventChannel channel;
    private static ArrayList<Module> MODULES_ENABLED = new ArrayList<Module>();
    public static IRecipeAdder I_RA;

    public impact() {
        impact.I_RA = new RecipeAdder();
        Texture.Icons.VOID.name();
    }

    public static ArrayList<Module> getModules() {
        if (MODULES_ENABLED.isEmpty()) {
            MODULES_ENABLED.add(new ChestCartModule());
        }
        return MODULES_ENABLED;
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent aEvent) {
        proxy.onServerStarted();
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent aEvent) {
        proxy.onServerStopping();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MainLoader.load(event);
        new GUIHandler();
        INFO("MainLoader LOAD Loaded");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mConfig = new Config(new File("config/IMPACT/impact.cfg"));
        INFO("Config Loaded");

        MainLoader.preInit(event);
        INFO("MainLoader PREINIT Loaded ");
        //MainLoader.preInitClient();

        MinecraftForge.EVENT_BUS.register(new impactEvents());
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("Impact");
        channel.register(new PacketHandler());

        TickHandler tickHandler = new TickHandler();
        FMLCommonHandler.instance().bus().register(tickHandler);
        MinecraftForge.EVENT_BUS.register(tickHandler);

        proxy.preload();
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        if (event.getSide() == Side.CLIENT) {
            new GT5OreLayerHelper();
            new GT5OreSmallHelper();
            if (csv) new CSVMaker().run();
        }
    }

    @Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent event) {
        MainLoader.postLoad(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
        MainLoader.postInit();
    }

    @Mod.EventHandler
    public void Init(FMLPostInitializationEvent init) {
        MainLoader.Init();
    }

    @Mod.EventHandler
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        MainLoader.onPreLoad();
    }
}
