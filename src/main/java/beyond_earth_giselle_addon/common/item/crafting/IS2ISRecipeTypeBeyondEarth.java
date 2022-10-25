package beyond_earth_giselle_addon.common.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipe;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipeType;

public class IS2ISRecipeTypeBeyondEarth extends IS2ISRecipeType<ItemStackToItemStackRecipeType<ItemStackToItemStackRecipe>, ItemStackToItemStackRecipe>
{
	public IS2ISRecipeTypeBeyondEarth()
	{

	}

	@Override
	public boolean testRecipeType(RecipeType<? extends Recipe<Container>> recipeType)
	{
		return recipeType instanceof ItemStackToItemStackRecipeType;
	}

	@Override
	public ItemStackToItemStackRecipe find(RecipeManager recipeManager, Level level, ItemStackToItemStackRecipeType<ItemStackToItemStackRecipe> recipeType, ItemStack itemStack)
	{
		return recipeType.findFirst(level, r -> r.test(itemStack));
	}

	@Override
	public ItemStackToItemStackRecipe createCache(ItemStackToItemStackRecipe recipe)
	{
		return recipe;
	}

}
