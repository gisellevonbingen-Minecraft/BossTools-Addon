package boss_tools_giselle_addon.common.adapter;

public abstract class FuelAdapter<T> extends AbstractAdapter<T>
{
	public FuelAdapter(T target)
	{
		super(target);
	}

	public abstract int getFuelSlot();

	public abstract boolean canInsertFuel();
}
