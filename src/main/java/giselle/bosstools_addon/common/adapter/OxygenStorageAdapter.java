package giselle.bosstools_addon.common.adapter;

public abstract class OxygenStorageAdapter<T> extends AbstractAdapter<T>
{
	public OxygenStorageAdapter(T target)
	{
		super(target);
	}

	public abstract double getStoredOxygen();

	public abstract void setStoredOxygen(double oxygen);

	public abstract double getOxygenCapacity();

}
