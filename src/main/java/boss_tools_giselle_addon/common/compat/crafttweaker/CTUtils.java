package boss_tools_giselle_addon.common.compat.crafttweaker;

import java.util.stream.Collectors;

import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;

import boss_tools_giselle_addon.common.item.crafting.FluidIngredientHelper;
import boss_tools_giselle_addon.common.item.crafting.IngredientStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;

public class CTUtils
{
	@SuppressWarnings("unchecked")
	public static IngredientStack toItemIngredientStack(CommandStringDisplayable input)
	{
		if (input instanceof IIngredientWithAmount)
		{
			return toItemIngredientStack((IIngredientWithAmount) input);
		}
		else if (input instanceof IIngredient)
		{
			return toItemIngredientStack((IIngredient) input);
		}
		else if (input instanceof MCTagWithAmount<?>)
		{
			return toItemIngredientStack((MCTagWithAmount<Item>) input);
		}
		else if (input instanceof MCTag<?>)
		{
			return toItemIngredientStack((MCTag<Item>) input);
		}
		else
		{
			return IngredientStack.EMPTY;
		}

	}

	public static IngredientStack toItemIngredientStack(IIngredient input)
	{
		return new IngredientStack(input.asVanillaIngredient(), 1);
	}

	public static IngredientStack toItemIngredientStack(IIngredientWithAmount input)
	{
		return new IngredientStack(input.getIngredient().asVanillaIngredient(), input.getAmount());
	}

	public static IngredientStack toItemIngredientStack(MCTagWithAmount<Item> input)
	{
		@SuppressWarnings("unchecked")
		ITag<Item> tagKey = input.getTag().getInternalRaw();
		return new IngredientStack(Ingredient.of(tagKey), input.getAmount());
	}

	public static IngredientStack toItemIngredientStack(MCTag<Item> input)
	{
		@SuppressWarnings("unchecked")
		ITag<Item> tagKey = input.getInternalRaw();
		return new IngredientStack(Ingredient.of(tagKey), 1);
	}

	@SuppressWarnings("unchecked")
	public static FluidIngredient toFluidIngredient(CommandStringDisplayable input)
	{
		if (input instanceof IFluidStack)
		{
			return toFluidIngredient((IFluidStack) input);
		}
		else if (input instanceof CTFluidIngredient)
		{
			return toFluidIngredient((CTFluidIngredient) input);
		}
		else if (input instanceof MCTagWithAmount<?>)
		{
			return toFluidIngredient((MCTagWithAmount<Fluid>) input);
		}
		else if (input instanceof MCTag<?>)
		{
			return toFluidIngredient((MCTag<Fluid>) input);
		}
		else
		{
			return new FluidIngredient.Empty();
		}

	}

	public static FluidIngredient toFluidIngredient(IFluidStack input)
	{
		return FluidIngredient.of(input.getImmutableInternal());
	}

	public static FluidIngredient toFluidIngredient(CTFluidIngredient input)
	{
		return input.mapTo(FluidIngredient::of, FluidIngredient::of, FluidIngredientHelper::of);
	}

	public static FluidIngredient toFluidIngredient(MCTagWithAmount<Fluid> input)
	{
		return toFluidIngredient(new CTFluidIngredient.FluidTagWithAmountIngredient(input));
	}

	public static FluidIngredient toFluidIngredient(MCTag<Fluid> input)
	{
		@SuppressWarnings("unchecked")
		ITag<Fluid> tagKey = input.getInternalRaw();
		return FluidIngredient.of(tagKey);
	}

	public static CTFluidIngredient toFluidIngredient(FluidIngredient input)
	{
		return new CTFluidIngredient.CompoundFluidIngredient(input.toStacks().stream().map(CTUtils::asFluidIngredient).collect(Collectors.toList()));
	}

	public static CTFluidIngredient asFluidIngredient(FluidStack fs)
	{
		return new MCFluidStack(fs).asFluidIngredient();
	}

}
