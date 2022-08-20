package boss_tools_giselle_addon.common.item.crafting;

import java.util.List;
import java.util.stream.Stream;

import net.mrscauthd.boss_tools.crafting.FluidIngredient;

public class FluidIngredientHelper
{
	public static FluidIngredient of(Stream<FluidIngredient> fluids)
	{
		int amount = fluids.findAny().map(FluidIngredient::getAmount).orElse(0);
		return new FluidIngredient.FluidMatch(fluids.map(FluidIngredient::getFluids).flatMap(List::stream).toList(), amount);
	}

	private FluidIngredientHelper()
	{

	}

}
