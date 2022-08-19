package beyond_earth_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;

import beyond_earth_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import beyond_earth_giselle_addon.common.item.crafting.RollingRecipe;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.compats.crafttweaker.recipe.CTItemStackToItemStackRecipeManager;

@ZenRegister
@ZenCodeType.Name(AddonCTConstants.RECIPE_MANAGER_ROLLING)
@IRecipeHandler.For(RollingRecipe.class)
public class AddonCTRollingRecipeManager extends CTItemStackToItemStackRecipeManager<RollingRecipe>
{
	public static final AddonCTRollingRecipeManager INSTANCE = new AddonCTRollingRecipeManager();

	private AddonCTRollingRecipeManager()
	{
		super(RollingRecipe::new);
	}

	@Override
	public RecipeType<RollingRecipe> getRecipeType()
	{
		return AddonRecipes.ROLLING;
	}

}
