package boss_tools_giselle_addon.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipe;

public class IS2ISRecipeTypeMinecraftBlasting extends IS2ISRecipeType<IRecipeType<? extends BlastingRecipe>, BlastingRecipe>
{
	@Override
	public boolean testRecipeType(IRecipeType<? extends IRecipe<IInventory>> recipeType)
	{
		return recipeType == IRecipeType.BLASTING;
	}

	@Override
	public BlastingRecipe find(RecipeManager recipeManager, World world, IRecipeType<? extends BlastingRecipe> recipeType, ItemStack itemStack)
	{
		Inventory inventory = new Inventory(itemStack);
		IRecipeType<? extends IRecipe<IInventory>> type2 = recipeType;
		return (BlastingRecipe) recipeManager.getRecipeFor(type2, inventory, world).orElse(null);
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
