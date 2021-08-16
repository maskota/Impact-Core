package com.impact.util.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class RecipeUtil {
	
	public static List<FluidStack> copyFluidList(List<FluidStack> fluidStacks) {
		FluidStack[] stacks = new FluidStack[fluidStacks.size()];
		for (int i = 0; i < fluidStacks.size(); i++) stacks[i] = fluidStacks.get(i).copy();
		return Lists.newArrayList(stacks);
	}
	
	public static ItemStack copy(ItemStack... stacks) {
		for (ItemStack stack : stacks) return stack != null ? stack.copy() : null;
		return null;
	}
	public static List<ItemStack> copyStackList(List<ItemStack> itemStacks) {
		ItemStack[] stacks = new ItemStack[itemStacks.size()];
		for (int i = 0; i < itemStacks.size(); i++) stacks[i] = copy(itemStacks.get(i));
		return Lists.newArrayList(stacks);
	}
	
	public static int amountOfNonNullElements(List<?> collection) {
		int amount = 0;
		for (Object object : collection) {
			if (object != null) amount++;
		}
		return amount;
	}
	
	public static int amountOfNonEmptyStacks(List<ItemStack> collection) {
		int amount = 0;
		for (ItemStack object : collection) {
			if (object != null && object.stackSize > 0) amount++;
		}
		return amount;
	}
}
