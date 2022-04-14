package beyond_earth_giselle_addon.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.mrscauthd.beyond_earth.entities.IRocketEntity;
import net.mrscauthd.beyond_earth.entities.RocketTier1Entity;
import net.mrscauthd.beyond_earth.entities.RocketTier2Entity;
import net.mrscauthd.beyond_earth.entities.RocketTier3Entity;
import net.mrscauthd.beyond_earth.entities.RocketTier4Entity;

public class BeyondEarthRocketHelper
{
	public static boolean isBeyondEarthRocket(Entity entity)
	{
		return entity instanceof IRocketEntity;
	}

	public static int getBucketsAmount(Entity entity)
	{
		SynchedEntityData entityData = entity.getEntityData();
		return entityData.get(IRocketEntity.BUCKETS);
	}

	public static void setBucketsAmount(Entity entity, int buckets)
	{
		buckets = Mth.clamp(buckets, 0, getBucketsCapacity(entity));
		SynchedEntityData entityData = entity.getEntityData();
		entityData.set(IRocketEntity.BUCKETS, buckets);
	}

	public static int getBucketsCapacity(Entity entity)
	{
		if (entity instanceof RocketTier1Entity)
		{
			return 1;
		}
		else if (entity instanceof RocketTier2Entity)
		{
			return 3;
		}
		else if (entity instanceof RocketTier3Entity)
		{
			return 3;
		}
		else if (entity instanceof RocketTier4Entity)
		{
			return 3;
		}

		return 0;
	}

	public static int getFuelAmount(Entity entity)
	{
		SynchedEntityData entityData = entity.getEntityData();
		return entityData.get(IRocketEntity.FUEL);
	}

	public static void setCurrentFuel(Entity entity, int fuel)
	{
		fuel = Mth.clamp(fuel, 0, getFuelCapacity(entity));
		SynchedEntityData entityData = entity.getEntityData();
		entityData.set(IRocketEntity.FUEL, fuel);
	}

	public static int getFuelCapacity(Entity entity)
	{
		return 300;
	}

	private BeyondEarthRocketHelper()
	{

	}

}
