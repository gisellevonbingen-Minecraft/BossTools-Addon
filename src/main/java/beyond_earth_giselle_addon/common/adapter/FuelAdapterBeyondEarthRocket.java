package beyond_earth_giselle_addon.common.adapter;

import beyond_earth_giselle_addon.common.entity.BeyondEarthRocketHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FuelAdapterBeyondEarthRocket extends FuelAdapter<Entity>
{
	public FuelAdapterBeyondEarthRocket(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		Entity target = this.getTarget();
		int fuelAmount = BeyondEarthRocketHelper.getFuelAmount(target);
		int fuelCapacity = BeyondEarthRocketHelper.getFuelCapacity(target);
		int fuelRemain = fuelCapacity - fuelAmount;

		int fuelFilling = Mth.clamp(amount, 0, fuelRemain);

		if (action.execute() == true)
		{
			BeyondEarthRocketHelper.setCurrentFuel(target, fuelAmount + fuelFilling);
		}

		return fuelFilling;
	}

}
