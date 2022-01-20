package beyond_earth_giselle_addon.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier1Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier2Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier3Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;

public class BeyondEarthRocketHelper
{
	public static boolean isBeyondEarthRocket(Entity entity)
	{
		if (entity instanceof RocketTier1Entity)
		{
			return true;
		}
		else if (entity instanceof RocketTier2Entity)
		{
			return true;
		}
		else if (entity instanceof RocketTier3Entity)
		{
			return true;
		}
		else if (entity instanceof RocketTier4Entity)
		{
			return true;
		}

		return false;
	}

	public static int getBucketsAmount(Entity entity)
	{
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RocketTier1Entity)
		{
			return entityData.get(RocketTier1Entity.BUCKET) ? 1 : 0;
		}
		else if (entity instanceof RocketTier2Entity)
		{
			return entityData.get(RocketTier2Entity.BUCKETS);
		}
		else if (entity instanceof RocketTier3Entity)
		{
			return entityData.get(RocketTier3Entity.BUCKETS);
		}
		else if (entity instanceof RocketTier4Entity)
		{
			return entityData.get(RocketTier4Entity.BUCKETS);
		}

		return 0;
	}

	public static void setBucketsAmount(Entity entity, int buckets)
	{
		buckets = Mth.clamp(buckets, 0, getBucketsCapacity(entity));
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RocketTier1Entity)
		{
			entityData.set(RocketTier1Entity.BUCKET, buckets > 0);
		}
		else if (entity instanceof RocketTier2Entity)
		{
			entityData.set(RocketTier2Entity.BUCKETS, buckets);
		}
		else if (entity instanceof RocketTier3Entity)
		{
			entityData.set(RocketTier3Entity.BUCKETS, buckets);
		}
		else if (entity instanceof RocketTier4Entity)
		{
			entityData.set(RocketTier4Entity.BUCKETS, buckets);
		}

	}

	public static int getBucketsCapacity(Entity entity)
	{
		if (entity instanceof RocketTier1Entity)
		{
			return RocketTier1Entity.FUEL_BUCKETS;
		}
		else if (entity instanceof RocketTier2Entity)
		{
			return RocketTier2Entity.FUEL_BUCKETS;
		}
		else if (entity instanceof RocketTier3Entity)
		{
			return RocketTier3Entity.FUEL_BUCKETS;
		}
		else if (entity instanceof RocketTier4Entity)
		{
			return RocketTier4Entity.FUEL_BUCKETS;
		}

		return 0;
	}

	public static int getFuelAmount(Entity entity)
	{
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RocketTier1Entity)
		{
			return entityData.get(RocketTier1Entity.FUEL);
		}
		else if (entity instanceof RocketTier2Entity)
		{
			return entityData.get(RocketTier2Entity.FUEL);
		}
		else if (entity instanceof RocketTier3Entity)
		{
			return entityData.get(RocketTier3Entity.FUEL);
		}
		else if (entity instanceof RocketTier4Entity)
		{
			return entityData.get(RocketTier4Entity.FUEL);
		}

		return 0;
	}

	public static void setCurrentFuel(Entity entity, int fuel)
	{
		fuel = Mth.clamp(fuel, 0, getFuelCapacity(entity));
		SynchedEntityData entityData = entity.getEntityData();

		if (entity instanceof RocketTier1Entity)
		{
			entityData.set(RocketTier1Entity.FUEL, fuel);
		}
		else if (entity instanceof RocketTier2Entity)
		{
			entityData.set(RocketTier2Entity.FUEL, fuel);
		}
		else if (entity instanceof RocketTier3Entity)
		{
			entityData.set(RocketTier3Entity.FUEL, fuel);
		}
		else if (entity instanceof RocketTier4Entity)
		{
			entityData.set(RocketTier4Entity.FUEL, fuel);
		}

	}

	public static int getFuelCapacity(Entity entity)
	{
		return 300;
	}

	private BeyondEarthRocketHelper()
	{

	}

}
