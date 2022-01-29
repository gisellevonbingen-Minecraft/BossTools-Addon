package beyond_earth_giselle_addon.common.adapter;

import beyond_earth_giselle_addon.common.entity.BeyondEarthRoverHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FuelAdapterBeyondEarthRover extends FuelAdapter<Entity>
{
	public FuelAdapterBeyondEarthRover(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		Entity target = this.getTarget();
		int fuelAmount = BeyondEarthRoverHelper.getFuelAmount(target);
		int fuelCapacity = BeyondEarthRoverHelper.getFuelCapacity(target);
		int fuelRemain = fuelCapacity - fuelAmount;

		int fuelFilling = Mth.clamp(amount, 0, fuelRemain / BeyondEarthRoverHelper.FUEL_BUCKET_MULTIPLIER);

		if (action.execute() == true)
		{
			BeyondEarthRoverHelper.setCurrentFuel(target, fuelAmount + fuelFilling * BeyondEarthRoverHelper.FUEL_BUCKET_MULTIPLIER);
		}

		return fuelFilling;
	}

}
