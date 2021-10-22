package giselle.bosstools_addon.common.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

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
		MinecraftForge.EVENT_BUS.post(this);

		if (this.isCanceled() == true)
		{
			return null;
		}

		return this.getAdapter();
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

	@SuppressWarnings("unchecked")
	public <X extends T, Y extends R, Z> void setAdapter(BiFunction<X, Z, Y> function, Z argument)
	{
		this.setAdapter(function.apply((X) this.getTaget(), argument));
	}

	@SuppressWarnings("unchecked")
	public <X extends T, Y extends R> void setAdapter(Function<X, Y> function)
	{
		this.setAdapter(function.apply((X) this.getTaget()));
	}

	public void setAdapter(R adapter)
	{
		this.adapter = adapter;
	}

}
