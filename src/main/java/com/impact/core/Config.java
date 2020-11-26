package com.impact.core;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;

public class Config {

    public static boolean loadConfig;
    public static Configuration config;

    public static boolean disableLogger;
    public static boolean csv;
    public static boolean hideBackground;
    public static boolean toolTips;
    public static boolean DisableNether;
    public static boolean DisableTheEnd;

    public Config(File file) {
        if (!loadConfig) {
            config = new Configuration(file);
            syncConfig(true);
        }
    }

    public static void syncConfig(boolean load) {
        ArrayList<String> General = new ArrayList<String>();
        ArrayList<String> Debug = new ArrayList<String>();

        try {
            if (!config.isChild && load) {
                config.load();
            }

            Property cfg;
            //todo GENERAL
            cfg = config.get("GENERAL", "Print csv", false);
            cfg.comment = "[NEI Ore Plugin] Princsv, you need apache commons collections to be injected in the minecraft jar. [Default: false]";
            csv = cfg.getBoolean(false);
            General.add(cfg.getName());

            cfg = config.get("GENERAL", "Hide Background", true);
            cfg.comment = "[NEI Ore Plugin] Hides the Background when the tooltip for the Dimensions is renderedr. [Default: true]";
            hideBackground = cfg.getBoolean(true);
            General.add(cfg.getName());

            cfg = config.get("GENERAL", "DimTooltip", true);
            cfg.comment = "[NEI Ore Plugin] Activates Dimensison Tooltips. [Default: true]";
            toolTips = cfg.getBoolean(true);
            General.add(cfg.getName());

            cfg = config.get("GENERAL", "Disable Nether", false);
            cfg.comment = "Disable Nether. [Default: false]";
            DisableNether = cfg.getBoolean(false);
            General.add(cfg.getName());

            cfg = config.get("GENERAL", "Disable The End", false);
            cfg.comment = "Disable The End. [Default: false]";
            DisableTheEnd = cfg.getBoolean(false);
            General.add(cfg.getName());

            //todo DEBUG
            cfg = config.get("DEBUG", "disableLogger", true);
            cfg.comment = "Disabled Logger. [Default: true]";
            disableLogger = cfg.getBoolean(true);
            Debug.add(cfg.getName());

            config.setCategoryPropertyOrder("GENERAL", General);
            config.setCategoryPropertyOrder("DEBUG", Debug);

            if (config.hasChanged()) config.save();

            FMLLog.log(Level.INFO, "[IMPACT] Logger: " + disableLogger);

        } catch (Exception e) {
            FMLLog.log(Level.ERROR, e, "[IMPACT] Error load config!");
        }
    }
}
