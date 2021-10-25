package boss_tools_giselle_addon.common.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class FluidContainerRegistration
{
	private Item emtpy;
	private Item full;
	private Fluid fluid;
	private int capacity;

	public FluidContainerRegistration(Item emtpy, Item full, Fluid fluid, int capacity)
	{
		this.emtpy = emtpy;
		this.full = full;
		this.fluid = fluid;
		this.capacity = capacity;
	}

	public boolean testFull(Item full, Fluid fluid)
	{
		if (fluid != null && fluid != this.getFluid())
		{
			return false;
		}

		return this.getFull() == full;
	}

	public boolean testEmpty(Item empty, Fluid fluid)
	{
		if (fluid != null && fluid != this.getFluid())
		{
			return false;
		}

		return this.getEmtpy() == empty;
	}

	public Item getEmtpy()
	{
		return this.emtpy;
	}

	public Item getFull()
	{
		return this.full;
	}

	public Fluid getFluid()
	{
		return this.fluid;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

}
