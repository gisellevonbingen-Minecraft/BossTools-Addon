package astrocraft_giselle_addon.common.adapter;

import astrocraft_giselle_addon.common.entity.AstroCraftRoverHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FuelAdapterAstroCraftRover extends FuelAdapter<Entity>
{
	public FuelAdapterAstroCraftRover(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		Entity target = this.getTarget();
		int fuelAmount = AstroCraftRoverHelper.getFuelAmount(target);
		int fuelCapacity = AstroCraftRoverHelper.getFuelCapacity(target);
		int fuelRemain = fuelCapacity - fuelAmount;

		int fuelFilling = Mth.clamp(amount, 0, fuelRemain);

		if (action.execute() == true)
		{
			AstroCraftRoverHelper.setCurrentFuel(target, fuelAmount + fuelFilling);
		}

		return fuelFilling;
	}

}
