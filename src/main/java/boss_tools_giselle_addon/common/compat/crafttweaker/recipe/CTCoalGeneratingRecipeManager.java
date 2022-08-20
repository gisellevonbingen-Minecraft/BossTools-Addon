package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.item.crafting.IRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.GeneratingRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_COAL_GENERATING)
@IRecipeHandler.For(GeneratingRecipe.class)
public class CTCoalGeneratingRecipeManager extends CTRecipeManager<GeneratingRecipe>
{
	public static final CTCoalGeneratingRecipeManager INSTANCE = new CTCoalGeneratingRecipeManager();

	@ZenCodeType.Method
	public void addRecipe(String name, int burnTime, IIngredient ingredient)
	{
		this.addRecipe(new GeneratingRecipe(this.getId(name), ingredient.asVanillaIngredient(), burnTime));
	}

	@Override
	public IRecipeType<GeneratingRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.GENERATING;
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, GeneratingRecipe recipe)
	{
		return this.buildCommandString(manager, recipe, recipe.getIngredient(), recipe.getBurnTime());
	}

}
