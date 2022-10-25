package beyond_earth_giselle_addon.common.compat.jei;

import java.util.List;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class RecipeCategoryRecipeType<T extends net.minecraft.world.item.crafting.RecipeType<R>, R extends Recipe<Container>> extends RecipeCategory<R>
{
	private final T mcRecipeType;

	public RecipeCategoryRecipeType(Class<? extends R> recipeClass, T recipeType)
	{
		super(new RecipeType<>(ForgeRegistries.RECIPE_TYPES.getKey(recipeType), recipeClass));
		this.mcRecipeType = recipeType;
	}

	public T getMCRecipeType()
	{
		return this.mcRecipeType;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		super.registerRecipes(registration);

		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		RecipeManager recipeManager = level.getRecipeManager();

		T recipeType = this.getMCRecipeType();
		List<R> recipes = recipeManager.getAllRecipesFor(recipeType);
		registration.addRecipes(this.getRecipeType(), recipes);
	}

}
