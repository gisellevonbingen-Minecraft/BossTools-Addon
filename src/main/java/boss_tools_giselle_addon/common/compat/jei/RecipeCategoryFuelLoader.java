package boss_tools_giselle_addon.common.compat.jei;

import boss_tools_giselle_addon.common.block.AddonBlocks;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

public class RecipeCategoryFuelLoader extends RecipeCategory<Fluid>
{
	public RecipeCategoryFuelLoader(Class<? extends Fluid> recipeClass)
	{
		super(recipeClass);
	}

	@Override
	public void setIngredients(Fluid recipe, IIngredients ingredients)
	{

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Fluid recipe, IIngredients ingredients)
	{

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{

	}

	@Override
	public ResourceLocation getKey()
	{
		return AddonBlocks.FUEL_LOADER.getId();
	}

}
