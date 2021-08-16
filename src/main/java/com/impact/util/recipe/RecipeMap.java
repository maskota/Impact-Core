package com.impact.util.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class RecipeMap<R extends RecipeBuilder<R>> {
	
	private static final Map<String, RecipeMap<?>> RECIPE_MAP_REGISTRY = new HashMap<>();
	
	public final String unlocalizedName;
	private final R recipeBuilderSample;
	
	private final int minInputs, maxInputs;
	private final int minOutputs, maxOutputs;
	private final int minFluidInputs, maxFluidInputs;
	private final int minFluidOutputs, maxFluidOutputs;
	
	public final boolean isHidden;
	
	private final Map<FluidKey, Collection<Recipe>> recipeFluidMap = new HashMap<>();
	private final List<Recipe> recipeList = new ArrayList<>();
	
	public RecipeMap(String unlocalizedName, int minInputs, int maxInputs, int minOutputs, int maxOutputs,
					 int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
					 R defaultRecipe, boolean isHidden) {
		this.unlocalizedName = unlocalizedName;
		
		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = minOutputs;
		this.minFluidOutputs = minFluidOutputs;
		
		this.maxInputs = maxInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.maxOutputs = maxOutputs;
		this.maxFluidOutputs = maxFluidOutputs;
		
		this.isHidden = isHidden;
		defaultRecipe.setRecipeMap(this);
		this.recipeBuilderSample = defaultRecipe;
		RECIPE_MAP_REGISTRY.put(unlocalizedName, this);
	}
	
	public static List<RecipeMap<?>> getRecipeMaps() {
		return ImmutableList.copyOf(RECIPE_MAP_REGISTRY.values());
	}
	
	public static void sortMaps() {
		for (RecipeMap<?> rmap : RECIPE_MAP_REGISTRY.values()) {
			rmap.recipeList.sort(Comparator.comparingInt(Recipe::getDuration)
					.thenComparingInt(Recipe::getEUt));
		}
	}
	
	public Collection<Recipe> getRecipesForFluid(FluidStack fluid) {
		return recipeFluidMap.getOrDefault(new FluidKey(fluid), Collections.emptySet());
	}
	
	public static RecipeMap<?> getByName(String unlocalizedName) {
		return RECIPE_MAP_REGISTRY.get(unlocalizedName);
	}
	
	//internal usage only, use buildAndRegister()
	public void addRecipe(ValidationResult<Recipe> validationResult) {
		Recipe recipe = validationResult.getResult();
		recipeList.add(recipe);
		
		for (FluidStack fluid : recipe.getFluidInputs()) {
			recipeFluidMap.computeIfAbsent(new FluidKey(fluid), k -> new HashSet<>(1)).add(recipe);
		}
	}
		
		
		public boolean removeRecipe(Recipe recipe) {
		//if we actually removed this recipe
		if (recipeList.remove(recipe)) {
			//also iterate trough fluid mappings and remove recipe from them
			recipeFluidMap.values().forEach(fluidMap ->
					fluidMap.removeIf(fluidRecipe -> fluidRecipe == recipe));
			return true;
		}
		return false;
	}
	
	public Recipe findRecipe(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs, int outputFluidTankCapacity) {
		if (recipeList.isEmpty())
			return null;
		if (minFluidInputs > 0 && RecipeUtil.amountOfNonNullElements(fluidInputs) < minFluidInputs) {
			return null;
		}
		if (minInputs > 0 && RecipeUtil.amountOfNonEmptyStacks(inputs) < minInputs) {
			return null;
		}
		if (maxInputs > 0) {
			return findByInputs(voltage, inputs, fluidInputs);
		} else {
			return findByFluidInputs(voltage, inputs, fluidInputs);
		}
	}
	
	private Recipe findByFluidInputs(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		for (FluidStack fluid : fluidInputs) {
			if (fluid == null) continue;
			Collection<Recipe> recipes = recipeFluidMap.get(new FluidKey(fluid));
			if (recipes == null) continue;
			for (Recipe tmpRecipe : recipes) {
				if (tmpRecipe.matches(false, inputs, fluidInputs)) {
					return voltage >= tmpRecipe.getEUt() ? tmpRecipe : null;
				}
			}
		}
		return null;
	}
	
	private Recipe findByInputs(long voltage, List<ItemStack> inputs, List<FluidStack> fluidInputs) {
		for (Recipe recipe : recipeList) {
			if (recipe.matches(false, inputs, fluidInputs)) {
				return voltage >= recipe.getEUt() ? recipe : null;
			}
		}
		return null;
	}
	
	public R recipeBuilder() {
		return recipeBuilderSample.copy();
	}
}
