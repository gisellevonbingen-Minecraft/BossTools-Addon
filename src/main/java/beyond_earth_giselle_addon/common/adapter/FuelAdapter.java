package beyond_earth_giselle_addon.common.adapter;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class FuelAdapter<T> extends AbstractAdapter<T>
{
	public FuelAdapter(T target)
	{
		super(target);
	}

	public abstract int fill(int amount, FluidAction action);
}
