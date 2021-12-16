package astrocraft_giselle_addon.common.adapter;

import astrocraft_giselle_addon.common.entity.AstroCraftRocketHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.mrscauthd.astrocraft.fluid.FluidUtil2;

public class FuelAdapterAstroCraftRocket extends FuelAdapter<Entity>
{
	public FuelAdapterAstroCraftRocket(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		int bucketSize = FluidUtil2.BUCKET_SIZE;

		Entity target = this.getTarget();
		int bucketsAmount = AstroCraftRocketHelper.getBucketsAmount(target);
		int bucketsCapacity = AstroCraftRocketHelper.getBucketsCapacity(target);
		int bucketsRemain = bucketsCapacity - bucketsAmount;

		int bucketsFilling = Mth.clamp(amount / bucketSize, 0, bucketsRemain);
		int fuelFiling = bucketsFilling * bucketSize;

		if (action.execute() == true)
		{
			AstroCraftRocketHelper.setBucketsAmount(target, bucketsAmount + bucketsFilling);
		}

		return fuelFiling;
	}

}
