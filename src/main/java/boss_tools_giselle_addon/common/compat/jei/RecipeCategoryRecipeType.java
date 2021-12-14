package boss_tools_giselle_addon.common.compat.jei;

import java.util.List;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class RecipeCategoryRecipeType<T extends IRecipeType<? extends R>, R extends IRecipe<IInventory>> extends RecipeCategory<R>
{
	private final T recipeType;

	public RecipeCategoryRecipeType(Class<? extends R> recipeClass, T recipeType)
	{
		super(recipeClass);
		this.recipeType = recipeType;
	}

	@Override
	public ResourceLocation getKey()
	{
		return Registry.RECIPE_TYPE.getKey(this.getRecipeType());
	}

	public T getRecipeType()
	{
		return this.recipeType;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientWorld level = minecraft.level;
		RecipeManager recipeManager = level.getRecipeManager();

		IRecipeType<? extends IRecipe<IInventory>> recipeType = this.getRecipeType();
		List<?> recipes = recipeManager.getAllRecipesFor(recipeType);
		registration.addRecipes(recipes, this.getUid());
	}

}
