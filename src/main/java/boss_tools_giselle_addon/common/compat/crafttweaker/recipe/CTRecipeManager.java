package boss_tools_giselle_addon.common.compat.crafttweaker.recipe;

import java.util.List;
import java.util.function.Function;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;

import boss_tools_giselle_addon.common.compat.crafttweaker.AddonCraftTweakerCompat;
import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import boss_tools_giselle_addon.common.compat.crafttweaker.CTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipe;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;

@ZenRegister
@ZenCodeType.Name(CTConstants.RECIPE_MANAGER)
public abstract class CTRecipeManager<T extends BossToolsRecipe> implements IRecipeManager, IRecipeHandler<T>
{
	public CTRecipeManager()
	{

	}

	public void addRecipe(T recipe)
	{
		CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
	}

	public ResourceLocation getId(String name)
	{
		return AddonCraftTweakerCompat.rl(this.fixRecipeName(name));
	}

	protected String buildCommandString(IRecipeManager manager, T recipe, Object... values)
	{
		return this.buildCommandString(manager, "addRecipe", recipe, values);
	}

	protected String buildCommandString(IRecipeManager manager, String method, T recipe, Object... values)
	{
		StringBuilder builder = new StringBuilder(manager.getCommandString()).append('.').append(method).append("(\"").append(recipe.getId().getPath()).append('"');

		for (Object value : values)
		{
			builder.append(", ").append(this.toStringValue(value));
		}

		return builder.append(");").toString();
	}

	protected <R> String toStringValues(List<R> values, String prefix, String suffix, Function<R, String> toStringFunction)
	{
		StringBuilder builder = new StringBuilder(prefix);

		for (int i = 0; i < values.size(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}

			R child = values.get(i);
			builder.append(toStringFunction.apply(child));
		}

		builder.append(suffix);
		return builder.toString();
	}

	protected <R> String toStringValues(List<R> values, Function<R, String> toStringFunction)
	{
		return this.toStringValues(values, "[", "]", toStringFunction);
	}

	protected String toStringValue(Object value)
	{
		if (value instanceof List<?>)
		{
			return this.toStringValues((List<?>) value, this::toStringValue);
		}
		else if (value instanceof ItemStack)
		{
			return this.toStringValue(new MCItemStack((ItemStack) value));
		}
		else if (value instanceof Ingredient)
		{
			return this.toStringValue(IIngredient.fromIngredient((Ingredient) value));
		}
		else if (value instanceof FluidStack)
		{
			return this.toStringValue(new MCFluidStack((FluidStack) value));
		}
		else if (value instanceof FluidIngredient)
		{
			return this.toStringValue(CTUtils.toFluidIngredient((FluidIngredient) value));
		}
		else if (value instanceof CommandStringDisplayable)
		{
			return ((CommandStringDisplayable) value).getCommandString();
		}
		else
		{
			return String.valueOf(value);
		}

	}

}
