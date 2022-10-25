package beyond_earth_giselle_addon.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.mrscauthd.beyond_earth.common.entities.RoverEntity;
import net.mrscauthd.beyond_earth.common.util.FluidUtil2;

public class BeyondEarthRoverHelper
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
		return 3 * FluidUtil2.BUCKET_SIZE;
	}

	private BeyondEarthRoverHelper()
	{

	}

}
