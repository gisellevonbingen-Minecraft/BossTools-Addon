package beyond_earth_giselle_addon.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.mrscauthd.beyond_earth.common.entities.IRocketEntity;
import net.mrscauthd.beyond_earth.common.util.FluidUtil2;

public class BeyondEarthRocketHelper
{
	public static boolean isBeyondEarthRocket(Entity entity)
	{
		return entity instanceof IRocketEntity;
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
		return 3 * FluidUtil2.BUCKET_SIZE;
	}

	private BeyondEarthRocketHelper()
	{

	}

}
