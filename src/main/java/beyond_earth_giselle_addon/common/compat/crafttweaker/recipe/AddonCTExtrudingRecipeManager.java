package beyond_earth_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;

import beyond_earth_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import beyond_earth_giselle_addon.common.item.crafting.ExtrudingRecipe;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.compats.crafttweaker.recipe.CTItemStackToItemStackRecipeManager;

@ZenRegister
@ZenCodeType.Name(AddonCTConstants.RECIPE_MANAGER_EXTRUDING)
@IRecipeHandler.For(ExtrudingRecipe.class)
public class AddonCTExtrudingRecipeManager extends CTItemStackToItemStackRecipeManager<ExtrudingRecipe>
{
	public static final AddonCTExtrudingRecipeManager INSTANCE = new AddonCTExtrudingRecipeManager();

	private AddonCTExtrudingRecipeManager()
	{
		super(ExtrudingRecipe::new);
	}

	@Override
	public int getDefaultCookTime()
	{
		return 50;
	}

	@Override
	public RecipeType<ExtrudingRecipe> getRecipeType()
	{
		return AddonRecipes.EXTRUDING;
	}

}
