package boss_tools_giselle_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;

public class FuelAdapterBossToolsDataParameterInteger extends FuelAdapter<Entity>
{
	private final Item fuelFullItem;
	private final DataParameter<Integer> parameter;
	private final int fillCount;

	public FuelAdapterBossToolsDataParameterInteger(Entity target, Item fuelFullItem, DataParameter<Integer> parameter, int fillCount)
	{
		super(target);
		this.fuelFullItem = fuelFullItem;
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

	@Override
	public final Item getFuelFullItem()
	{
		return this.fuelFullItem;
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
