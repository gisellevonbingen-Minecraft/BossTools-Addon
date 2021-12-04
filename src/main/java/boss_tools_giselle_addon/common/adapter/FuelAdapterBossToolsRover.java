package boss_tools_giselle_addon.common.adapter;

import boss_tools_giselle_addon.common.entity.BossToolsRoverHelper;
import net.minecraft.entity.Entity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class FuelAdapterBossToolsRover extends FuelAdapter<Entity>
{
	public FuelAdapterBossToolsRover(Entity target)
	{
		super(target);
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public boolean canInsertFuel()
	{
		Entity target = this.getTarget();
		int amount = BossToolsRoverHelper.getFuelAmount(target);
		int capacity = BossToolsRoverHelper.getFuelCapacity(target);
		return (capacity - amount) >= FluidUtil2.BUCKET_SIZE;
	}

}
