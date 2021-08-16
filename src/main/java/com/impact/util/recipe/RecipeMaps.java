package com.impact.util.recipe;

public class RecipeMaps {
	
	public static final RecipeMap<SimpleRecipeBuilder> COMPRESSOR_RECIPES = new RecipeMap<>("compressor",
			1, 1, 1, 1, 0, 0, 0, 0,
			new SimpleRecipeBuilder().duration(400).EUt(2), false);
	
}
