package beyond_earth_giselle_addon.common.adapter;

import beyond_earth_giselle_addon.common.entity.BeyondEarthRocketHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.mrscauthd.beyond_earth.fluids.FluidUtil2;

public class FuelAdapterBeyondEarthRocket extends FuelAdapter<Entity>
{
	public FuelAdapterBeyondEarthRocket(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		int bucketSize = FluidUtil2.BUCKET_SIZE;

		Entity target = this.getTarget();
		int bucketsAmount = BeyondEarthRocketHelper.getBucketsAmount(target);
		int bucketsCapacity = BeyondEarthRocketHelper.getBucketsCapacity(target);
		int bucketsRemain = bucketsCapacity - bucketsAmount;

		int bucketsFilling = Mth.clamp(amount / bucketSize, 0, bucketsRemain);
		int fuelFiling = bucketsFilling * bucketSize;

		if (action.execute() == true)
		{
			BeyondEarthRocketHelper.setBucketsAmount(target, bucketsAmount + bucketsFilling);
		}

		return fuelFiling;
	}

}
