package beyond_earth_giselle_addon.common.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipe;

public class IS2ISRecipeTypeMinecraftBlasting extends IS2ISRecipeType<RecipeType<? extends BlastingRecipe>, BlastingRecipe>
{
	@Override
	public boolean testRecipeType(RecipeType<? extends Recipe<Container>> recipeType)
	{
		return recipeType == RecipeType.BLASTING;
	}

	@Override
	public BlastingRecipe find(RecipeManager recipeManager, Level level, RecipeType<? extends BlastingRecipe> recipeType, ItemStack itemStack)
	{
		Container container = new SimpleContainer(itemStack);
		RecipeType<? extends Recipe<Container>> type2 = recipeType;
		return (BlastingRecipe) recipeManager.getRecipeFor(type2, container, level).orElse(null);
	}

	@Override
	public ItemStackToItemStackRecipe createCache(BlastingRecipe recipe)
	{
		Ingredient ingredient = recipe.getIngredients().get(0);
		ItemStack resultItem = recipe.getResultItem();
		int cookingTime = recipe.getCookingTime();
		float experience = recipe.getExperience();
		return new ItemStackToItemStackRecipeWrapper(recipe, ingredient, resultItem, cookingTime, experience);
	}

}
