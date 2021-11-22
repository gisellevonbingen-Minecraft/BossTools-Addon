package boss_tools_giselle_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;

public class FuelAdapterBossToolsFuel extends FuelAdapter<Entity>
{
	private final DataParameter<Integer> parameter;
	private int capacity;

	public FuelAdapterBossToolsFuel(Entity target, DataParameter<Integer> parameter, int capacity)
	{
		super(target);
		this.parameter = parameter;
		this.capacity = capacity;
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
		return (this.getCapacity() - current) >= FluidUtil2.BUCKET_SIZE;
	}

	public DataParameter<Integer> getParameter()
	{
		return this.parameter;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

}
