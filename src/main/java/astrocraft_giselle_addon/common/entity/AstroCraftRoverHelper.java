package astrocraft_giselle_addon.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.mrscauthd.astrocraft.entity.RoverEntity;
import net.mrscauthd.astrocraft.fluid.FluidUtil2;

public class AstroCraftRoverHelper
{
	public static int getFuelAmount(Entity entity)
	{
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RoverEntity)
		{
			return entityData.get(RoverEntity.FUEL);
		}

		return 0;
	}

	public static void setCurrentFuel(Entity entity, int fuel)
	{
		fuel = Mth.clamp(fuel, 0, getFuelCapacity(entity));
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RoverEntity)
		{
			entityData.set(RoverEntity.FUEL, fuel);
		}

	}

	public static int getFuelCapacity(Entity entity)
	{
		return RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE;
	}

	private AstroCraftRoverHelper()
	{

	}

}
