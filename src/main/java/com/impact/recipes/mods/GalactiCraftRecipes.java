package com.impact.recipes.mods;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.enums.GT_Values.RA;

public class GalactiCraftRecipes implements Runnable {

    @Override
    public void run() {
        ChemicalReactor();
        Mixer();
    }

    public void ChemicalReactor() {
        //Dimethylhydrazine
        RA.addChemicalRecipe(Materials.Chloramine.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Dimethylamine.getGas(1000), Materials.DilutedHydrochloricAcid.getFluid(1000), Materials.Dimethylhydrazine.getCells(1), 960, 480);
        RA.addChemicalRecipe(Materials.Dimethylamine.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Chloramine.getFluid(1000), Materials.DilutedHydrochloricAcid.getFluid(1000), Materials.Dimethylhydrazine.getCells(1), 960, 480);
        RA.addChemicalRecipe(Materials.Chloramine.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Dimethylamine.getGas(1000), Materials.Dimethylhydrazine.getFluid(1000), Materials.DilutedHydrochloricAcid.getCells(1), 960, 480);
        RA.addChemicalRecipe(Materials.Dimethylamine.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Chloramine.getFluid(1000), Materials.Dimethylhydrazine.getFluid(1000), Materials.DilutedHydrochloricAcid.getCells(1), 960, 480);
        RA.addMultiblockChemicalRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(24)}, new FluidStack[]{Materials.HypochlorousAcid.getFluid(1000), Materials.Ammonia.getGas(1000), Materials.Methanol.getFluid(2000)}, new FluidStack[]{Materials.Dimethylhydrazine.getFluid(1000), Materials.DilutedHydrochloricAcid.getFluid(1000), Materials.Water.getFluid(2000)}, null, 1040, 480);

        //Dimethylamine
        RA.addChemicalRecipe(Materials.Methanol.getCells(2), GT_Utility.getIntegratedCircuit(1), Materials.Ammonia.getGas(1000), Materials.Dimethylamine.getGas(1000), Materials.Water.getCells(2), 240, 120);
        RA.addChemicalRecipeForBasicMachineOnly(Materials.Ammonia.getCells(1), Materials.Empty.getCells(1), Materials.Methanol.getFluid(2000), Materials.Dimethylamine.getGas(1000), Materials.Water.getCells(2), GT_Values.NI, 240, 120);
        RA.addChemicalRecipe(Materials.Methanol.getCells(2), GT_Utility.getIntegratedCircuit(11), Materials.Ammonia.getGas(1000), Materials.Water.getFluid(2000), Materials.Dimethylamine.getCells(1), Materials.Empty.getCells(1), 240, 120);
        RA.addChemicalRecipe(Materials.Ammonia.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Methanol.getFluid(2000), Materials.Water.getFluid(2000), Materials.Dimethylamine.getCells(1), 240, 120);

        //Chloramine
        GT_Values.RA.addChemicalRecipe(Materials.Ammonia.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.HypochlorousAcid.getFluid(1000), Materials.Chloramine.getFluid(1000), Materials.Water.getCells(1), 160);
        GT_Values.RA.addChemicalRecipe(Materials.HypochlorousAcid.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Ammonia.getGas(1000), Materials.Chloramine.getFluid(1000), Materials.Water.getCells(1), 160);
        GT_Values.RA.addChemicalRecipe(Materials.Ammonia.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.HypochlorousAcid.getFluid(1000), Materials.Water.getFluid(1000), Materials.Chloramine.getCells(1), 160);
        GT_Values.RA.addChemicalRecipe(Materials.HypochlorousAcid.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Ammonia.getGas(1000), Materials.Water.getFluid(1000), Materials.Chloramine.getCells(1), 160);

        //HypochlorousAcid
        GT_Values.RA.addChemicalRecipe(Materials.Chlorine.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Water.getFluid(1000), Materials.HypochlorousAcid.getFluid(1000), Materials.DilutedHydrochloricAcid.getCells(1), Materials.Empty.getCells(1), 120);
        GT_Values.RA.addChemicalRecipe(Materials.Water.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Chlorine.getGas(1000), Materials.HypochlorousAcid.getFluid(1000), Materials.DilutedHydrochloricAcid.getCells(1), GT_Values.NI, 120);
        GT_Values.RA.addChemicalRecipe(Materials.Chlorine.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Water.getFluid(1000), Materials.DilutedHydrochloricAcid.getFluid(1000), Materials.HypochlorousAcid.getCells(1), Materials.Empty.getCells(1), 120);
        GT_Values.RA.addChemicalRecipe(Materials.Water.getCells(1), GT_Utility.getIntegratedCircuit(11), Materials.Chlorine.getGas(1000), Materials.DilutedHydrochloricAcid.getFluid(1000), Materials.HypochlorousAcid.getCells(1), GT_Values.NI, 120);


        GT_Values.RA.addChemicalRecipe(Materials.Nitrogen.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Oxygen.getGas(1000), Materials.NitricOxide.getFluid(2000), Materials.Empty.getCells(1), null, 120);
        GT_Values.RA.addChemicalRecipe(Materials.Oxygen.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Nitrogen.getGas(1000), Materials.NitricOxide.getFluid(2000), Materials.Empty.getCells(1), null, 120);
        GT_Values.RA.addChemicalRecipe(Materials.Oxygen.getCells(1), GT_Utility.getIntegratedCircuit(1), Materials.Nitrogen.getGas(1000), Materials.NitricOxide.getFluid(2000), Materials.Empty.getCells(1), null, 120);


        GT_Values.RA.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cadmium, 1L), GT_Utility.getIntegratedCircuit(1), Materials.Oxygen.getGas(2000), null, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CadmiumPeroxide, 10L), null, 1200,  120);

    }

    public void Mixer() {
        RA.addMixerRecipe(Materials.Dimethylhydrazine.getCells(1), null, null, null, Materials.DinitrogenTetroxide.getGas(1000), new FluidStack(ItemList.sRocketFuel, 2000), Materials.Empty.getCells(1), 60, 16);
        RA.addMixerRecipe(Materials.DinitrogenTetroxide.getCells(1), null, null, null, Materials.Dimethylhydrazine.getFluid(1000), new FluidStack(ItemList.sRocketFuel, 2000), Materials.Empty.getCells(1), 60, 16);
        RA.addMixerRecipe(Materials.Dimethylhydrazine.getCells(1), null, null, null, Materials.Oxygen.getGas(1000), new FluidStack(ItemList.sRocketFuel, 2000), Materials.Empty.getCells(1), 60, 16);
        RA.addMixerRecipe(Materials.Oxygen.getCells(1), null, null, null, Materials.Dimethylhydrazine.getFluid(1000), new FluidStack(ItemList.sRocketFuel, 2000), Materials.Empty.getCells(1), 60, 16);

        RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CadmiumPeroxide, 10L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesia, 2L), GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Utility.getIntegratedCircuit(2), null, Materials.Oxygen.getGas(5000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Pentacadmiummagnesiumhexaoxid, 2L), 1200, 120);
    }
}
