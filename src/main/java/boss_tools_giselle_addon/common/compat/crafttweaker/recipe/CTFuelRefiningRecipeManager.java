package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import boss_tools_giselle_addon.common.compat.crafttweaker.CTUtils;
import net.minecraft.item.crafting.IRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_FUEL_REFINING)
@IRecipeHandler.For(FuelRefiningRecipe.class)
public class CTFuelRefiningRecipeManager extends CTRecipeManager<FuelRefiningRecipe>
{
	public static final CTFuelRefiningRecipeManager INSTANCE = new CTFuelRefiningRecipeManager();

	@ZenCodeType.Method
	public void addRecipe(String name, IFluidStack fluidOutput, CommandStringDisplayable fluidInput)
	{
		this.addRecipe(new FuelRefiningRecipe(this.getId(name), CTUtils.toFluidIngredient(fluidInput), CTUtils.toFluidIngredient(fluidOutput.asFluidIngredient())));
	}

	@Override
	public IRecipeType<FuelRefiningRecipe> getRecipeType()
	{
		return BossToolsRecipeTypes.FUELREFINING;
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, FuelRefiningRecipe recipe)
	{
		return this.buildCommandString(manager, recipe, recipe.getInput(), recipe.getOutput());
	}

}
