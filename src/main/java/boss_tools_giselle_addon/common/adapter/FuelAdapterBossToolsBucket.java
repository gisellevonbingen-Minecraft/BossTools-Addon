package boss_tools_giselle_addon.common.adapter;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;

public class FuelAdapterBossToolsBucket extends FuelAdapter<Entity>
{
	private final Item fuelFullItem;
	private final DataParameter<Boolean> parameter;

	public FuelAdapterBossToolsBucket(Entity target, Item fuelFullItem, DataParameter<Boolean> parameter)
	{
		super(target);
		this.fuelFullItem = fuelFullItem;
		this.parameter = parameter;
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public boolean canInsertFuel()
	{
		Boolean current = this.getTarget().getEntityData().get(this.getParameter());
		return current == false;
	}

	@Override
	public final Item getFuelFullItem()
	{
		return this.fuelFullItem;
	}

	public DataParameter<Boolean> getParameter()
	{
		return parameter;
	}

}
