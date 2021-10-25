package boss_tools_giselle_addon.common.adapter;

import net.minecraft.item.Item;

public abstract class FuelAdapter<T> extends AbstractAdapter<T>
{
	public FuelAdapter(T target)
	{
		super(target);
	}

	public abstract int getFuelSlot();

	public abstract boolean canInsertFuel();

	public abstract Item getFuelFullItem();
}
