package boss_tools_giselle_addon.common.compat.jei;

import java.util.List;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class RecipeCategoryRecipeType<T extends RecipeType<? extends R>, R extends Recipe<Container>> extends RecipeCategory<R>
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
		super.registerRecipes(registration);

		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		RecipeManager recipeManager = level.getRecipeManager();

		RecipeType<? extends Recipe<Container>> recipeType = this.getRecipeType();
		List<?> recipes = recipeManager.getAllRecipesFor(recipeType);
		registration.addRecipes(recipes, this.getUid());
	}

}
