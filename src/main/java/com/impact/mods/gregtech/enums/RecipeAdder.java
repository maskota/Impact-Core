package com.impact.mods.gregtech.enums;

import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import net.minecraft.item.ItemStack;

public class RecipeAdder implements IRecipeAdder {

    @Override
    public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt) {
        GTMTE_RailAssembler.sTrackAssemblerRecipes.addRecipe(true, aInputs, new ItemStack[]{aOutput}, null,
                null, null, null, aDuration, aEUt, 0);
        return true;
    }

    @Override
    public boolean addPhotonContainer(ItemStack[] aInputs, ItemStack[] aOutputs, int aDuration, int aEUt, int photonsAmount) {
        GT_RecipeMaps.sPhotonContainer.addRecipe(true, aInputs, aOutputs, null,
                null, null, null, aDuration, aEUt, photonsAmount);
        return true;
    }

    @Override
    public boolean addMESPRecipes(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt, int photonsAmount) {
        GT_RecipeMaps.sMESystemProvider.addRecipe(true, aInputs, new ItemStack[]{aOutput}, null,
                null, null, null, aDuration, aEUt, photonsAmount);
        return true;
    }
}