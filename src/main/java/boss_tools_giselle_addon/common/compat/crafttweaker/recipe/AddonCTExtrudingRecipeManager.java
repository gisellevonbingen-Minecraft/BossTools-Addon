package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import boss_tools_giselle_addon.common.item.crafting.ExtrudingRecipe;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.item.crafting.IRecipeType;

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
	public IRecipeType<ExtrudingRecipe> getRecipeType()
	{
		return AddonRecipes.EXTRUDING;
	}

}
