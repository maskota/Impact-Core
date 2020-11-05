package com.impact.mods.GregTech;


import com.impact.mods.GregTech.tileentities.multi.*;
import com.impact.mods.GregTech.tileentities.multi.generators.*;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_LapPowerStation;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_LargeTank;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_MultiTank;
import com.impact.mods.GregTech.tileentities.storage.GTMTE_SingleTank;

import static com.impact.mods.GregTech.GT_ItemList.*;

public class Multi_Register {
    public void run() {
        registerMachines();
    }

    private void registerMachines() {
        int ID = 14020;

        //MULTIBLOCKS
        Machine_PBE.set(new GTMTE_PressBendExtrud(ID++, "multimachine.pbe", "Multi PBE Machine").getStackForm(1L));
        Machine_LaserEngraver.set(new GTMTE_LaserEng(ID++, "multimachine.laserengraver", "Multi Laser Engraver").getStackForm(1L));
        Machine_Assembler.set(new GTMTE_Assembler(ID++, "multimachine.assembler", "Multi Assembling Machine").getStackForm(1L));
        Machine_Centrifuge.set(new GTMTE_Centrifuge(ID++, "multimachine.centrifuge", "Multi Centrifuge").getStackForm(1L));
        Machine_Electrolyzer.set(new GTMTE_Electrolyzer(ID++, "multimachine.electrolyzer", "Multi Electrolyzer").getStackForm(1L));
        Machine_Wire.set(new GTMTE_Wire(ID++, "multimachine.wire", "Multi Wire Factory").getStackForm(1L));
        Machine_Supply.set(new GTMTE_Supply(ID++, "multimachine.supply", "Multi Supply Production").getStackForm(1L));
        Machine_Utility.set(new GTMTE_Utility(ID++, "multimachine.utility", "Multi Utility Machine").getStackForm(1L));
        Machine_Brewmenter.set(new GTMTE_Brewmenter(ID++, "multimachine.brewmenter", "Multi Brewmenter").getStackForm(1L));
        Machine_ArcFurnace.set(new GTMTE_ArcFurnace(ID++, "multimachine.arcfurnace", "Multi Arc Furnace").getStackForm(1L));
        Machine_Cutting.set(new GTMTE_Cutting(ID++, "multimachine.cutting", "Multi Cutting Machine").getStackForm(1L));
        Machine_Extradifier.set(new GTMTE_Extradifier(ID++, "multimachine.extradifier", "Multi Extradification").getStackForm(1L));
        Machine_Macerator.set(new GTMTE_Macerator(ID++, "multimachine.macerator", "Multi Maceration Stack").getStackForm(1L));
        Machine_Mixer.set(new GTMTE_Mixer(ID++, "multimachine.mixer", "Multi Mixing Machine").getStackForm(1L));
        Machine_Siftarator.set(new GTMTE_Siftarator(ID++, "multimachine.siftarator", "Multi Siftaration Unit").getStackForm(1L));
        Machine_DDDPrinter.set(new GTMTE_DDDPrinter(ID++, "multimachine.dddprinter", "3D Printer").getStackForm(1L));
        Machine_FreezSolidifier.set(new GTMTE_FreezerSolidifier(ID++, "multimachine.freezsolidifier", "Freezer Solidifier").getStackForm(1L));
        Machine_BlastSmelter.set(new GTMTE_BlastSmelter(ID++, "multimachine.blastsmelter", "Blast Smelter").getStackForm(1L));
        WaterDrill.set(new GTMTE_DrillerWater(ID++, "multimachine.waterdrill", "Electric Water Drilling Rig").getStackForm(1L));
        BasicWaterPump.set(new GTMTE_BasicWaterPump(ID++, "multimachine.basicwaterpump", "Primitive Water Pump").getStackForm(1L));
        AdvVacuumFreezer.set(new GTMTE_AdvancedVacuumFreezer(ID++, "multimachine.advvf", "Advanced Vacuum Freezer").getStackForm(1L));
        LapPowerStation.set(new GTMTE_LapPowerStation(ID++, "multimachine.supercapacitor", "Lapotronic Supercapacitor").getStackForm(1L));
        SawMill.set(new GTMTE_SawMill(ID++, "multimachine.sawmill", "Saw Mill").getStackForm(1L));
        Pyrolyse.set(new GTMTE_Pyrolyse(ID++, "multimachine.pyrolyse", "Pyrolyse Oven").getStackForm(1L));
        AdvPyrolyse.set(new GTMTE_AdvancedPyrolyse(ID++, "multimachine.advpyrolyse", "Advanced Pyrolyse Oven").getStackForm(1L));
        Naquadah_multi.set(new GTMTE_HyperNaquadahGenerator(ID++, "multimachine.naqgen", "Hyper Generator").getStackForm(1L));
        Naquadah_Liquid_multi.set(new GTMTE_LiquidNqGenerator(ID++, "multimachine.liquidnaqgen", "Liquid Naquadah Generator").getStackForm(1L));
        Heavy_Metal_Cyclone.set(new GTMTE_HeavyMetalCyclone(ID++, "multimachine.heavymetalcyclone", "Heavy Metal Cyclone").getStackForm(1L));
        Naquadah_Liquid_Enriched.set(new GTMTE_LiquidEnrichedNqGenerator(ID++, "multimachine.liquidenrichednqgenerator", "Liquid Enriched Naquadah Generator").getStackForm(1L));

        Moon_Miner.set(new GTMTE_MoonMiner(ID++, "multimachine.blockminer", "Moon Miner").getStackForm(1L));

        Large_Multi_Tank.set(new GTMTE_LargeTank(ID, "multimachine.largemultitank", "Single-Tank").getStackForm(1L));

        Machine_CokeOven.set(new GTMTE_CokeOven(13002, "multimachine.cokeoven", "Coke Oven").getStackForm(1L));
        Machine_Multi_Farm.set(new GTMTE_Farm(13001, "multimachine.farm", "Multiblock Farm").getStackForm(1L));

        SOFC_Low.set(new GTMTE_SOFC_I(13101, "multimachine.fuelcellmk1", "Solid-Oxide Generator Tier 1", 0).getStackForm(1L));
        SOFC_Medium.set(new GTMTE_SOFC_II(13102, "multimachine.fuelcellmk2", "Solid-Oxide Generator Tier 2", 1).getStackForm(1L));
        SOFC_Huge.set(new GTMTE_SOFC_III(13103, "multimachine.fuelcellmk3", "Solid-Oxide Generator Tier 3", 2).getStackForm(1L));
        Multi_Tank.set(new GTMTE_MultiTank(13104, "multimachine.multifluidtank", "Multi-Tank").getStackForm(1L));
        Single_Tank.set(new GTMTE_SingleTank(13105, "multimachine.singlefluidtank", "Single-Tank").getStackForm(1L));
    }
}
