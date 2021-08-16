package com.impact.util.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	
	protected final List<ItemStack> inputs;
	protected final List<ItemStack> outputs;
	protected final List<ItemStack> chanceOutput;
	
	protected final List<FluidStack> fluidInputs;
	protected final List<FluidStack> fluidOutputs;
	
	protected final int duration, EUt;
	protected final boolean hidden;
	
	
	public Recipe(List<ItemStack> inputs, List<ItemStack> outputs, List<ItemStack> chanceOutput,
				  List<FluidStack> fluidInputs, List<FluidStack> fluidOutputs, int duration, int EUt, boolean hidden) {
		this.inputs = new ArrayList<>();
		this.inputs.addAll(inputs);
		
		this.outputs = new ArrayList<>();
		this.outputs.addAll(outputs);
		
		this.chanceOutput = new ArrayList<>();
		this.chanceOutput.addAll(chanceOutput);
		
		this.fluidInputs = new ArrayList<>();
		this.fluidInputs.addAll(fluidInputs);
		
		this.fluidOutputs = new ArrayList<>();
		this.fluidOutputs.addAll(fluidOutputs);
		
		this.duration = duration;
		this.EUt = EUt;
		this.hidden = hidden;
		
		//сортируем не расходуемые входы до конца
		this.inputs.sort((is1, is2) -> is1.stackSize == 0 ? 1 : 0);
	}
	
	public boolean matches(boolean consumeIfSuccessful, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		Pair<Boolean, Integer[]> fluids = null;
		Pair<Boolean, Integer[]> items = null;
		
		fluids = matchesFluid(fluidInputs);
		if (!fluids.getKey()) return false;
		
		items = matchesItems(inputs);
		if (!items.getKey()) return false;
		
		if (consumeIfSuccessful) {
			Integer[] fluidAmountInTank = fluids.getValue();
			Integer[] itemAmountInSlot = items.getValue();
			for (int i = 0; i < fluidAmountInTank.length; i++) {
				FluidStack fluidStack = fluidInputs.get(i);
				int fluidAmount = fluidAmountInTank[i];
				if (fluidStack == null || fluidStack.amount == fluidAmount)
					continue;
				fluidStack.amount = fluidAmount;
				if (fluidStack.amount == 0)
					fluidInputs.set(i, null);
			}
			for (int i = 0; i < itemAmountInSlot.length; i++) {
				ItemStack itemInSlot = inputs.get(i);
				int itemAmount = itemAmountInSlot[i];
				if (itemInSlot.stackSize <= 0 || itemInSlot.stackSize == itemAmount)
					continue;
				itemInSlot.stackSize = itemAmountInSlot[i];
			}
		}
		
		return true;
	}
	
	private Pair<Boolean, Integer[]> matchesItems(List<ItemStack> inputs) {
		Integer[] itemAmountInSlot = new Integer[inputs.size()];
		
		for (int i = 0; i < itemAmountInSlot.length; i++) {
			ItemStack itemInSlot = inputs.get(i);
			itemAmountInSlot[i] = Math.max(itemInSlot.stackSize, 0);
		}
		
		for (ItemStack ingredient : this.inputs) {
			int ingredientAmount = ingredient.stackSize;
			boolean isNotConsumed = false;
			if (ingredientAmount == 0) {
				ingredientAmount = 1;
				isNotConsumed = true;
			}
			for (int i = 0; i < inputs.size(); i++) {
				ItemStack inputStack = inputs.get(i);
				if (inputStack.stackSize <= 0) continue;
				int itemAmountToConsume = Math.min(itemAmountInSlot[i], ingredientAmount);
				ingredientAmount -= itemAmountToConsume;
				if (!isNotConsumed) itemAmountInSlot[i] -= itemAmountToConsume;
				if (ingredientAmount == 0) break;
			}
			if (ingredientAmount > 0)
				return Pair.of(false, itemAmountInSlot);
		}
		return Pair.of(true, itemAmountInSlot);
	}
	
	private Pair<Boolean, Integer[]> matchesFluid(List<FluidStack> fluidInputs) {
		Integer[] fluidAmountInTank = new Integer[fluidInputs.size()];
		
		for (int i = 0; i < fluidAmountInTank.length; i++) {
			FluidStack fluidInTank = fluidInputs.get(i);
			fluidAmountInTank[i] = fluidInTank == null ? 0 : fluidInTank.amount;
		}
		
		for (FluidStack fluid : this.fluidInputs) {
			int fluidAmount = fluid.amount;
			boolean isNotConsumed = false;
			if (fluidAmount == 0) {
				fluidAmount = 1;
				isNotConsumed = true;
			}
			for (int i = 0; i < fluidInputs.size(); i++) {
				FluidStack tankFluid = fluidInputs.get(i);
				if (tankFluid == null || !tankFluid.isFluidEqual(fluid))
					continue;
				int fluidAmountToConsume = Math.min(fluidAmountInTank[i], fluidAmount);
				fluidAmount -= fluidAmountToConsume;
				if (!isNotConsumed) fluidAmountInTank[i] -= fluidAmountToConsume;
				if (fluidAmount == 0) break;
			}
			if (fluidAmount > 0)
				return Pair.of(false, fluidAmountInTank);
		}
		return Pair.of(true, fluidAmountInTank);
	}
	
	
	public List<ItemStack> getInputs() {
		return inputs;
	}
	
	public List<ItemStack> getOutputs() {
		return outputs;
	}
	
	public List<ItemStack> getChanceOutput() {
		return chanceOutput;
	}
	
	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}
	
	public List<FluidStack> getFluidOutputs() {
		return fluidOutputs;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getEUt() {
		return EUt;
	}
	
	public boolean isHidden() {
		return hidden;
	}
}
