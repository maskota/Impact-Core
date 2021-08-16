package com.impact.util.recipe;

public class SimpleRecipeBuilder extends RecipeBuilder<SimpleRecipeBuilder> {
	
	public SimpleRecipeBuilder() {
	}
	
	public SimpleRecipeBuilder(Recipe recipe, RecipeMap<SimpleRecipeBuilder> recipeMap) {
		super(recipe, recipeMap);
	}
	
	public SimpleRecipeBuilder(RecipeBuilder<SimpleRecipeBuilder> recipeBuilder) {
		super(recipeBuilder);
	}
	
	@Override
	public SimpleRecipeBuilder copy() {
		return new SimpleRecipeBuilder(this);
	}
	
	public ValidationResult<Recipe> build() {
		return ValidationResult.newResult(new Recipe(inputs, outputs, chanceOutput, fluidInputs, fluidOutputs, duration, EUt, hidden));
	}
}
