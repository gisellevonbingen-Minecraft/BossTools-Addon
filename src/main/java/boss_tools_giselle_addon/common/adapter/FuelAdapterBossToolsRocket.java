package boss_tools_giselle_addon.common.adapter;

import boss_tools_giselle_addon.common.entity.BossToolsRocketHelper;
import net.minecraft.entity.Entity;

public class FuelAdapterBossToolsRocket extends FuelAdapter<Entity>
{
	public FuelAdapterBossToolsRocket(Entity target)
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
		int amount = BossToolsRocketHelper.getBucketsAmount(target);
		int capacity = BossToolsRocketHelper.getBucketsCapacity(target);
		return amount < capacity;
	}

}
