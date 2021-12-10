package boss_tools_giselle_addon.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class BossToolsRoverHelper
{
	public static int getFuelAmount(Entity entity)
	{
		EntityDataManager entityData = entity.getEntityData();

		if (entity instanceof RoverEntity)
		{
			return entityData.get(RoverEntity.FUEL);
		}

		return 0;
	}

	public static void setCurrentFuel(Entity entity, int fuel)
	{
		fuel = MathHelper.clamp(fuel, 0, getFuelCapacity(entity));
		EntityDataManager entityData = entity.getEntityData();

		if (entity instanceof RoverEntity)
		{
			entityData.set(RoverEntity.FUEL, fuel);
		}

	}

	public static int getFuelCapacity(Entity entity)
	{
		return RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE;
	}

	private BossToolsRoverHelper()
	{

	}

}
