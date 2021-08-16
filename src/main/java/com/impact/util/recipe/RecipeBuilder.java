package com.impact.util.recipe;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

public abstract class RecipeBuilder<R extends RecipeBuilder<R>> {
	
	protected RecipeMap<R> recipeMap;

	protected final List<ItemStack> inputs;
	protected final List<ItemStack> outputs;
	protected final List<ItemStack> chanceOutput;
	
	protected final List<FluidStack> fluidInputs;
	protected final List<FluidStack> fluidOutputs;
	
	protected int duration, EUt;
	protected boolean hidden = false;
	
	
	protected RecipeBuilder() {
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
		this.chanceOutput = new ArrayList<>();
		
		this.fluidInputs = new ArrayList<>();
		this.fluidOutputs = new ArrayList<>();
	}
	
	protected RecipeBuilder(Recipe recipe, RecipeMap<R> recipeMap) {
		this.recipeMap = recipeMap;
		this.inputs = new ArrayList<>();
		this.inputs.addAll(recipe.getInputs());
		this.outputs = new ArrayList<>();
		this.outputs.addAll(RecipeUtil.copyStackList(recipe.getOutputs()));
		this.chanceOutput = new ArrayList<>(recipe.getChanceOutput());
		
		this.fluidInputs = RecipeUtil.copyFluidList(recipe.getFluidInputs());
		this.fluidOutputs = RecipeUtil.copyFluidList(recipe.getFluidOutputs());
		
		this.duration = recipe.getDuration();
		this.EUt = recipe.getEUt();
		this.hidden = recipe.isHidden();
	}
	
	protected RecipeBuilder(RecipeBuilder<R> recipeBuilder) {
		this.recipeMap = recipeBuilder.recipeMap;
		this.inputs = new ArrayList<>();
		this.inputs.addAll(recipeBuilder.getInputs());
		this.outputs = new ArrayList<>();
		this.outputs.addAll(RecipeUtil.copyStackList(recipeBuilder.getOutputs()));
		this.chanceOutput = new ArrayList<>(recipeBuilder.getChancedOutputs());
		
		this.fluidInputs = RecipeUtil.copyFluidList(recipeBuilder.getFluidInputs());
		this.fluidOutputs = RecipeUtil.copyFluidList(recipeBuilder.getFluidOutputs());
		this.duration = recipeBuilder.duration;
		this.EUt = recipeBuilder.EUt;
		this.hidden = recipeBuilder.hidden;
	}
	
	public R inputs(Collection<ItemStack> inputs) {
		inputs.forEach(stack -> {
			if (!(stack == null || stack.stackSize <= 0)) {
				this.inputs.add(stack);
			}
		});
		return (R) this;
	}
	
	public R input(ItemStack input) {
		return inputs(input);
	}
	
	public R inputs(ItemStack... in) {
		return inputs(Arrays.asList(in));
	}
	
	public R input(OrePrefixes orePrefix, Materials material) {
		return input(GT_OreDictUnificator.get(orePrefix, material, 1));
	}
	
	public R input(OrePrefixes orePrefix, Materials material, int count) {
		return input(GT_OreDictUnificator.get(orePrefix, material, count));
	}
	
	public R input(Item item) {
		return input(item, 1);
	}
	
	public R input(Item item, int count) {
		return inputs(new ItemStack(item, count));
	}
	
	public R input(Item item, int count, int meta) {
		return inputs(new ItemStack(item, count, meta));
	}
	
	@SuppressWarnings("unused")
	public R input(Item item, int count, boolean wild) {
		return inputs(new ItemStack(item, count, GT_Values.W));
	}
	
	public R input(Block item) {
		return input(item, 1);
	}
	
	public R input(Block item, int count) {
		return inputs(new ItemStack(item, count));
	}
	
	public R outputs(ItemStack... outputs) {
		return outputs(Arrays.asList(outputs));
	}
	
	public R outputs(Collection<ItemStack> outputs) {
		outputs = new ArrayList<>(outputs);
		outputs.removeIf(Objects::isNull);
		this.outputs.addAll(outputs);
		return (R) this;
	}
	
	public R output(OrePrefixes orePrefix, Materials material) {
		return outputs(GT_OreDictUnificator.get(orePrefix, material, 1));
	}
	
	public R output(OrePrefixes orePrefix, Materials material, int count) {
		return outputs(GT_OreDictUnificator.get(orePrefix, material, count));
	}
	
	public R fluidInputs(FluidStack... inputs) {
		return fluidInputs(Arrays.asList(inputs));
	}
	
	public R fluidInputs(Collection<FluidStack> inputs) {
		this.fluidInputs.addAll(inputs);
		this.fluidInputs.removeIf(Objects::isNull);
		return (R) this;
	}
	
	public R fluidOutputs(FluidStack... outputs) {
		return fluidOutputs(Arrays.asList(outputs));
	}
	
	public R fluidOutputs(Collection<FluidStack> outputs) {
		outputs = new ArrayList<>(outputs);
		outputs.removeIf(Objects::isNull);
		this.fluidOutputs.addAll(outputs);
		return (R) this;
	}
	
	public R chancedOutput(ItemStack stack, int chance) {
		if (stack == null) {
			return (R) this;
		}
		if (0 >= chance || chance > 10000) {
			return (R) this;
		}
		this.chanceOutput.add(stack);
		return (R) this;
	}
	
	public R duration(int duration) {
		this.duration = duration;
		return (R) this;
	}
	
	public R EUt(int EUt) {
		this.EUt = EUt;
		return (R) this;
	}
	
	public R hidden() {
		this.hidden = true;
		return (R) this;
	}
	
	public abstract R copy();
	
	public abstract ValidationResult<Recipe> build();
	
	public void buildAndRegister() {
		ValidationResult<Recipe> validationResult = build();
		recipeMap.addRecipe(validationResult);
	}
	
	public R setRecipeMap(RecipeMap<R> recipeMap) {
		this.recipeMap = recipeMap;
		return (R) this;
	}
	
	public List<ItemStack> getInputs() {
		return inputs;
	}
	
	public List<ItemStack> getOutputs() {
		return outputs;
	}
	
	public List<ItemStack> getChancedOutputs() {
		return chanceOutput;
	}
	
	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}
	
	public List<FluidStack> getFluidOutputs() {
		return fluidOutputs;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("recipeMap", recipeMap)
				.append("inputs", inputs)
				.append("outputs", outputs)
				.append("chancedOutputs", chanceOutput)
				.append("fluidInputs", fluidInputs)
				.append("fluidOutputs", fluidOutputs)
				.append("duration", duration)
				.append("EUt", EUt)
				.append("hidden", hidden)
				.toString();
	}
}
