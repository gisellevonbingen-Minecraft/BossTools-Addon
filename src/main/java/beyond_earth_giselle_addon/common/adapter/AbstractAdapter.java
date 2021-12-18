package beyond_earth_giselle_addon.common.adapter;

public class AbstractAdapter<T>
{
	private final T target;

	public AbstractAdapter(T target)
	{
		this.target = target;
	}
	
	public final T getTarget()
	{
		return this.target;
	}
	
}
