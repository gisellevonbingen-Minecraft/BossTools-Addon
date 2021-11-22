package boss_tools_giselle_addon.common.fluid;

import java.util.function.Predicate;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class FluidUtil3
{
	public static boolean canDrain(ItemStack itemStack, Predicate<Fluid> predicate)
	{
		return canDrain(itemStack, fs -> predicate.test(fs.getFluid()));
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
