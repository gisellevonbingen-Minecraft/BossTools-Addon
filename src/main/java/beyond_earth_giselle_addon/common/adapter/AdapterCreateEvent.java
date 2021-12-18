package beyond_earth_giselle_addon.common.adapter;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public abstract class AdapterCreateEvent<T, R extends AbstractAdapter<? extends T>> extends Event
{
	private final T taget;
	private R adapter;

	public AdapterCreateEvent(T target)
	{
		this.taget = target;
	}

	public R resolve()
	{
		if (MinecraftForge.EVENT_BUS.post(this))
		{
			return null;
		}
		else
		{
			return this.getAdapter();
		}

	}

	@Override
	public boolean isCancelable()
	{
		return true;
	}

	public final T getTaget()
	{
		return this.taget;
	}

	public R getAdapter()
	{
		return this.adapter;
	}

	public void setAdapter(R adapter)
	{
		this.adapter = adapter;
	}

}
