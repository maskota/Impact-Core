package com.impact.mods.gregtech.enums;

import net.minecraft.item.ItemStack;

public interface IRecipeAdder {

    public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt);

    public boolean addPhotonContainer(ItemStack[] aInputs, ItemStack[] aOutputs, int aDuration, int aEUt, int photonsAmount);
}
