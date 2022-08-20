package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import boss_tools_giselle_addon.common.compat.crafttweaker.CTUtils;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipeAbstract;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER_OXYGEN)
public abstract class CTOxygenMakingRecipeManager<T extends OxygenMakingRecipeAbstract> extends CTRecipeManager<T>
{
	private final OxygenMakingRecipeConstructor<T> constructor;

	public CTOxygenMakingRecipeManager(OxygenMakingRecipeConstructor<T> constructor)
	{
		this.constructor = constructor;
	}

	@ZenCodeType.Method
	public void addRecipe(String name, int oxygenOutput, CommandStringDisplayable fluidInput)
	{
		ResourceLocation id = this.getId(name);
		T recipe = this.getConstructor().construct(id, CTUtils.toFluidIngredient(fluidInput), oxygenOutput);
		this.addRecipe(recipe);
	}

	@Override
	public String dumpToCommandString(IRecipeManager manager, T recipe)
	{
		return this.buildCommandString(manager, recipe, recipe.getInput(), recipe.getOxygen());
	}

	public OxygenMakingRecipeConstructor<T> getConstructor()
	{
		return this.constructor;
	}

	public interface OxygenMakingRecipeConstructor<T>
	{
		public T construct(ResourceLocation id, FluidIngredient input, int oxygen);
	}

}
