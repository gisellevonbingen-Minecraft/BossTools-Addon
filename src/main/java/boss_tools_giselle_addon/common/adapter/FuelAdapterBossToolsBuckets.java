package boss_tools_giselle_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;

public class FuelAdapterBossToolsBuckets extends FuelAdapter<Entity>
{
	private final DataParameter<Integer> parameter;
	private final int fillCount;

	public FuelAdapterBossToolsBuckets(Entity target, DataParameter<Integer> parameter, int fillCount)
	{
		super(target);
		this.parameter = parameter;
		this.fillCount = fillCount;
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public boolean canInsertFuel()
	{
		Integer current = this.getTarget().getEntityData().get(this.getParameter());
		return current < this.getFillCount();
	}

	public DataParameter<Integer> getParameter()
	{
		return parameter;
	}

	public int getFillCount()
	{
		return this.fillCount;
	}

}
