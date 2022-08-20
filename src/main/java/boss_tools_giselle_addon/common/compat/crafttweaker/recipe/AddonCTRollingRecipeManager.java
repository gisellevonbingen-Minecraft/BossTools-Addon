package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.AddonCTConstants;
import boss_tools_giselle_addon.common.item.crafting.RollingRecipe;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.item.crafting.IRecipeType;

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
	public IRecipeType<RollingRecipe> getRecipeType()
	{
		return AddonRecipes.ROLLING;
	}

}
