package com.impact.mods.GregTech.GTregister;

import gregtech.api.enums.*;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.objects.MaterialStack;

import java.util.Arrays;

import static gregtech.api.enums.Materials.RedAlloy;

public class GT_Materials implements IMaterialHandler {

    /** 1 = Dusts of all kinds.
    *   2 = Dusts, Ingots, Plates, Rods/Sticks, Machine Components and other Metal specific things.
    *   4 = Dusts, Gems, Plates, Lenses (if transparent).
    *   8 = Dusts, Impure Dusts, crushed Ores, purified Ores, centrifuged Ores etc.
    *   16 = Cells
    *   32 = Plasma Cells
    *   64 = Tool Heads
    *   128 = Gears
    *   256 = Designates something as empty (only used for the Empty material)/

    /** MATERIALS> */
/* TODO IN GT MATERIALS

    public static Materials Zirconium = new Materials           ( 27, TextureSet.SET_SHINY,128.0F,51200,8,1,     200,200,210,0,"Zirconium",         "Zirconium",           0,0,1000,0, true, false,4,1,1, Dyes.dyeLightGray, Element.Zr, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_Aspects.TC_AspectStack(TC_Aspects.VOLATUS, 1)));
    public static Materials Samarium = new Materials            ( 73, TextureSet.SET_SHINY,128.0F,51200,8,1,     255,255,255,0,"Samarium",          "Samarium",            0,0,1000,0, true, false,4,1,1, Dyes.dyeLightGray, Element.Sm, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_Aspects.TC_AspectStack(TC_Aspects.VOLATUS, 1)));
    public static Materials Gadolinium = new Materials          ( 74, TextureSet.SET_SHINY,128.0F,51200,8,1,     255,203,64 ,0,"Gadolinium",        "Gadolinium",          0,0,1000,0, true, false,4,1,1, Dyes.dyeLightGray, Element.Gd, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_Aspects.TC_AspectStack(TC_Aspects.VOLATUS, 1)));
    public static Materials Trinium = new Materials             ( 7 , TextureSet.SET_SHINY,128.0F,51200,8,2,     31 ,47 , 71,0,"Trinium",           "Trinium",             0,0,1000,0, true, false,4,1,1, Dyes.dyeLightGray).disableAutoGeneratedBlastFurnaceRecipes();
    public static Materials HastelloyC276 = new Materials       ( 15, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 207,109,57 ,0,"HastelloyC-276",    "Hastelloy C-276",     0,0,3666,0,false, false,4,1,1, Dyes.dyeRed,  6, Arrays.asList(new MaterialStack(Materials.Cobalt, 1), new MaterialStack(Materials.Molybdenum, 8), new MaterialStack(Materials.Tungsten, 1), new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Chrome, 7), new MaterialStack(Materials.Nickel, 32))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials GumMetal = new Materials            ( 16, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 138,12 ,175,0,"TNTZAlloy",         "TNTZ Alloy",          0,0,3777,0,false, false,4,1,1, Dyes.dyeRed,  4, Arrays.asList(new MaterialStack(Materials.Titanium,1), new MaterialStack(Materials.Niobium,23), new MaterialStack(Materials.Tantalum,1), new MaterialStack(Zirconium,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Titaniolum = new Materials          ( 38, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 209,168,85 ,0,"Titaniolum",        "Titaniolum",          0,0,3888,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Titanium,1), new MaterialStack(Materials.Niobium,1), new MaterialStack(Materials.Tantalum,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Zamak = new Materials               ( 40, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 43 ,117,222,0,"Zamak",             "Zamak",               0,0,3999,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Aluminium,4), new MaterialStack(Materials.Copper,3), new MaterialStack(Materials.Magnesium,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Duraluminium = new Materials        ( 41, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 1  ,165,87 ,0,"Duraluminium",      "Duraluminium",        0,0,4111,0,false, false,4,1,1, Dyes.dyeRed,  4, Arrays.asList(new MaterialStack(Materials.Aluminium,3), new MaterialStack(Materials.Copper,3), new MaterialStack(Materials.Magnesium,1), new MaterialStack(Materials.Manganese,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Inconel690 = new Materials          ( 42, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 221,238,1  ,0,"Inconel-690",       "Inconel-690",         0,0,4222,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Chrome,1), new MaterialStack(Materials.Niobium,2), new MaterialStack(Materials.Molybdenum,2), new MaterialStack(Materials.Nichrome,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Nitinol = new Materials             ( 43, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 164,250,108,0,"Nitinol",           "Nitinol",             0,0,4333,0,false, false,4,1,1, Dyes.dyeRed,  2, Arrays.asList(new MaterialStack(Materials.Titanium,1), new MaterialStack(Materials.Nickel,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Inconel792 = new Materials          ( 44, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 35 ,52 ,148,0,"Inconel-792",       "Inconel-792",         0,0,4444,0,false, false,4,1,1, Dyes.dyeRed,  4, Arrays.asList(new MaterialStack(Materials.Nickel,2), new MaterialStack(Materials.Niobium,1), new MaterialStack(Materials.Aluminium,2), new MaterialStack(Materials.Nichrome,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials TiBetaC = new Materials             ( 46, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 161,197,163,0,"TiBetaC",           "TiBetaC",             0,0,4555,0,false, false,4,1,1, Dyes.dyeRed,  6, Arrays.asList(new MaterialStack(Materials.Titanium,2), new MaterialStack(Materials.Aluminium,3), new MaterialStack(Materials.Vanadium,8), new MaterialStack(Materials.Chrome,6), new MaterialStack(Materials.Molybdenum,4), new MaterialStack(Zirconium,4))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials MaragingSteel250 = new Materials    ( 49, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 242,242,182,0,"MaragingSteel250",  "Maraging Steel 250",  0,0,4666,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Steel,16), new MaterialStack(Materials.Titanium,1), new MaterialStack(Materials.Aluminium,1), new MaterialStack(Materials.Nickel,4), new MaterialStack(Materials.Cobalt,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Talonite = new Materials            ( 50, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 1  ,29 ,66 ,0,"Talonite",          "Talonite",            0,0,4777,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Cobalt,4), new MaterialStack(Materials.Chrome,3), new MaterialStack(Materials.Phosphor,2), new MaterialStack(Materials.Molybdenum,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Grisium = new Materials             ( 51, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 67 ,88 ,221,0,"Grisium",           "Grisium",             0,0,4888,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Titanium,9), new MaterialStack(Materials.Carbon,9), new MaterialStack(Materials.Kalium,9), new MaterialStack(Materials.Lithium,9), new MaterialStack(Materials.Sulfur,9))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Nitinol60 = new Materials           ( 53, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 122,235,112,0,"Nitinol-60",        "Nitinol-60",          0,0,4999,0,false, false,4,1,1, Dyes.dyeRed,  2, Arrays.asList(new MaterialStack(Materials.Nickel,2), new MaterialStack(Materials.Titanium,3))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Kovar = new Materials               ( 59, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 203,192,166,0,"Kovar",             "Kovar",               0,0,4000,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Iron,5), new MaterialStack(Materials.Nickel,3), new MaterialStack(Materials.Cobalt,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Mangalloy = new Materials           ( 60, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 57 ,152,213,0,"Mangalloy",         "Mangalloy",           0,0,3945,0,false, false,4,1,1, Dyes.dyeRed,  2, Arrays.asList(new MaterialStack(Materials.Steel,5), new MaterialStack(Materials.Manganese,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials MaragingSteel300 = new Materials    ( 61, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 99 ,112,135,0,"MaragingSteel300",  "Maraging Steel 300",  0,0,5001,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Steel,16), new MaterialStack(Materials.Titanium,1), new MaterialStack(Materials.Aluminium,1), new MaterialStack(Materials.Nickel,4), new MaterialStack(Materials.Cobalt,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Stellite = new Materials            ( 66, TextureSet.SET_SHINY,128.0F,51200,8,2|128, 180,30 ,7  ,0,"Stellite",          "Stellite",            0,0,5399,0,false, false,4,1,1, Dyes.dyeRed,  4, Arrays.asList(new MaterialStack(Materials.Cobalt,7), new MaterialStack(Materials.Chrome,3), new MaterialStack(Materials.Phosphor,2), new MaterialStack(Materials.Molybdenum,1))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Zeron100 = new Materials            ( 68, TextureSet.SET_SHINY,128.0F,51200,8,2,     180,180,20, 0,"Zeron-100",         "Zeron-100",           0,0,5100,0,false, false,4,1,1, Dyes.dyeRed,  6, Arrays.asList(new MaterialStack(Materials.Chrome,26), new MaterialStack(Materials.Nickel,6), new MaterialStack(Materials.Molybdenum,4), new MaterialStack(Materials.Copper,20), new MaterialStack(Materials.Tungsten,4), new MaterialStack(Materials.Steel,40))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials HastelloyN = new Materials          ( 69, TextureSet.SET_SHINY,128.0F,51200,8,2,     201,231,104,0,"HastelloyN",        "Hastelloy N",         0,0,4350,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Yttrium,8), new MaterialStack(Materials.Molybdenum,16), new MaterialStack(Materials.Chrome,8), new MaterialStack(Materials.Titanium,8), new MaterialStack(Materials.Nickel,60))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Lafium = new Materials              ( 71, TextureSet.SET_SHINY,128.0F,51200,8,2,     144,167,154,0,"Lafium",            "Lafium",              0,0,6105,0,false, false,4,1,1, Dyes.dyeRed,  6, Arrays.asList(new MaterialStack(HastelloyN,8), new MaterialStack(Samarium,2), new MaterialStack(Materials.Tungsten,4), new MaterialStack(Materials.Aluminium,6), new MaterialStack(Materials.Nickel,8), new MaterialStack(Materials.Carbon,2))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials CinobiteA243 = new Materials        ( 72, TextureSet.SET_SHINY,128.0F,51200,8,2,     245,245,220,0,"CinobiteA243",      "Cinobite A243",       0,0,7350,0,false, false,4,1,1, Dyes.dyeRed,  6, Arrays.asList(new MaterialStack(Zeron100,16), new MaterialStack(Materials.Gadolinium,5), new MaterialStack(Materials.Aluminium,3), new MaterialStack(Materials.Tin,2), new MaterialStack(Materials.Titanium,12), new MaterialStack(Materials.Osmiridium,6))).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);
    public static Materials Potin = new Materials               ( 75, TextureSet.SET_SHINY,128.0F,51200,8,2,     201,151,129,0,"Potin",             "Potin",               0,0,0,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Copper,6), new MaterialStack(Materials.Tin,2), new MaterialStack(Materials.Lead,1))).disableAutoGeneratedBlastFurnaceRecipes(); //Cu6Sn2Pb
    public static Materials EglinSteel = new Materials          ( 76, TextureSet.SET_SHINY,128.0F,51200,8,2,     139,69 ,19 ,0,"EglinSteel",        "Eglin Steel",         0,0,0,0,false, false,4,1,1, Dyes.dyeRed,  5, Arrays.asList(new MaterialStack(Materials.Iron,5), new MaterialStack(Materials.Invar,5), new MaterialStack(Materials.Sulfur,1), new MaterialStack(Materials.Silicon,4), new MaterialStack(Materials.Carbon,1))).disableAutoGeneratedBlastFurnaceRecipes(); //Fe5Invar5S1Si4C1
    public static Materials Birmabright = new Materials         ( 77, TextureSet.SET_SHINY,128.0F,51200,8,2,     118,132,245,0,"Birmabright",       "Birmabright",         0,0,0,0,false, false,4,1,1, Dyes.dyeRed,  3, Arrays.asList(new MaterialStack(Materials.Aluminium,1), new MaterialStack(Materials.Magnesium,1), new MaterialStack(Materials.Manganese,1))).disableAutoGeneratedBlastFurnaceRecipes(); //AlMgMn

    TODO FLUID

    public static Materials SupercriticalSteam = new MaterialBuilder(11, TextureSet.SET_FLUID, "Supercritical Steam").addCell().addFluid().setRGB(32, 32, 32).setColor(Dyes.dyeGray).setFuelType(MaterialBuilder.NUKE).setFuelPower(16).constructMaterial();
    public static Materials RawRadox = new MaterialBuilder(-1, TextureSet.SET_DULL, "Raw Radox").setRGB(80, 30, 80).addFluid().constructMaterial();
    public static Materials RadoxSuperLight = new MaterialBuilder(-1, TextureSet.SET_DULL, "Super Light Radox").setRGB(155, 0, 155).addGas().constructMaterial();
    public static Materials RadoxLight = new MaterialBuilder(-1, TextureSet.SET_DULL, "Light Radox").setRGB(140, 0, 140).addGas().constructMaterial();
    public static Materials RadoxHeavy = new MaterialBuilder(-1, TextureSet.SET_DULL, "Heavy Radox").setRGB(115, 0, 115).addFluid().constructMaterial();
    public static Materials RadoxSuperHeavy = new MaterialBuilder(-1, TextureSet.SET_DULL, "Super Heavy Radox").setRGB(100, 0, 100).addFluid().constructMaterial();
    public static Materials Xenoxene = new MaterialBuilder(-1, TextureSet.SET_DULL, "Xenoxene").setRGB(133, 130, 128).addFluid().constructMaterial();
    public static Materials DelutedXenoxene = new MaterialBuilder(-1, TextureSet.SET_DULL, "Deluted Xenoxene").setRGB(206, 200, 196).addFluid().constructMaterial();
    public static Materials RadoxCracked = new MaterialBuilder(-1, TextureSet.SET_DULL, "Cracked Radox").setRGB(180, 130, 180).addGas().constructMaterial();
    public static Materials RadoxGas = new MaterialBuilder(-1, TextureSet.SET_DULL, "Radox Gas").setRGB(255, 130, 255).addGas().constructMaterial();
    public static Materials RadoxPolymerHot = new MaterialBuilder(-1, TextureSet.SET_DULL, "Hot Radox Polymer").setRGB(255, 130, 255).addGas().constructMaterial();
    public static Materials RadoxPolymer = new Materials(426, TextureSet.SET_DULL, 8.0F, 346, 3, 1 | 2 | 16, 133, 0, 128, 0, "RadoxPoly", "Radox Polymer", 0, 0, 6203, 0, true, false, 1, 1, 1, Dyes.dyePurple, 0, Arrays.asList(new MaterialStack(Carbon, 14), new MaterialStack(Materials.Osmium, 11), new MaterialStack(Materials.Oxygen, 7), new MaterialStack(Materials.Silver, 3), new MaterialStack(Materials.Ledox, 1)), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.HUMANUS, 2))).setHasCorrespondingGas(true).setGasTemperature(12406).disableAutoGeneratedBlastFurnaceRecipes();
*/
    public GT_Materials() {
        Materials.add(this);
    }

    @Override
    public void onMaterialsInit() {
        /** This is just left here as an example of how to add new materials. **/


        /*
        int i = 0;
        for (int j = GregTech_API.sMaterialProperties.get("general", "AmountOfCustomMaterialSlots", 16); i < j; i++) {
            String aID = (i < 10 ? "0" : "") + i;
            new Materials(-1, TextureSet.SET_METALLIC, 1.0F, 0, 0, 0, 255, 255, 255, 0, "CustomMat" + aID, "CustomMat" + aID, 0, 0, 0, 0, false, false, 1, 1, 1, Dyes._NULL, "custom", true, aID);
        }
        */
    }

    @Override
    public void onComponentInit() {
        for(OrePrefixes ore:OrePrefixes.values()) {
            ore.enableComponent(RedAlloy);
        }

        /** This is just left here as an example of how to add components. **/
        /*
        //Enabling specific components:
        OrePrefixes.spring.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.plateTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDense.enableComponent(Materials.Cobalt);
        */
    }

    @Override
    public void onComponentIteration(Materials aMaterial) {
        /** This is just left here as an example of how to add components. **/
        /*
        //Enabling/Disabling components depending on the current Materials values:
        if ((aMaterial.mTypes & 0x40) != 0) {                     //This material can be made into tool heads
            OrePrefixes.plateQuadruple.mDisabledItems.remove(aMaterial);
        }
        */
    }
}
