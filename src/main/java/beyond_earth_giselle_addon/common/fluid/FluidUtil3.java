package beyond_earth_giselle_addon.common.fluid;

import java.util.function.Predicate;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.beyond_earth.fluids.FluidUtil2;

public class FluidUtil3
{
	public static boolean canDrain(ItemStack itemStack, Predicate<Fluid> predicate)
	{
		return canDrainStack(itemStack, fs -> predicate.test(fs.getFluid()));
	}

	public static boolean canDrainStack(ItemStack itemStack, Predicate<FluidStack> predicate)
	{
		for (FluidStack fluidStack : FluidUtil2.getFluidStacks(itemStack))
		{
			if (predicate.test(fluidStack) == true && FluidUtil2.canDrain(itemStack, fluidStack.getFluid()) == true)
			{
				return true;
			}

		}

		return false;
	}

	private FluidUtil3()
	{

	}

}
