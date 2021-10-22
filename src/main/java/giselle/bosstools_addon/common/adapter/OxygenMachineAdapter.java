package giselle.bosstools_addon.common.adapter;

public abstract class OxygenMachineAdapter<T> extends AbstractAdapter<T>
{
	public OxygenMachineAdapter(T target)
	{
		super(target);
	}

	public abstract double getFuel();

	public abstract void setFuel(double fuel);

	public double getMaxFuel()
	{
		return getMaxFuel(this.getFallbackMaxFuel());
	}

	public abstract double getMaxFuel(double fallback);

	public abstract void setMaxFuel(double maxFuel);

	public abstract double getFallbackMaxFuel();
}
