package boss_tools_giselle_addon.common.util;

import java.util.function.Supplier;

public class LazyReference<T> implements Supplier<T>
{
	private T value;

	public LazyReference()
	{

	}

	public synchronized final void set(T value)
	{
		if (this.value == null)
		{
			this.value = value;
			this.onSet(value);
		}
		else
		{
			throw new IllegalCallerException();
		}

	}

	protected void onSet(T value)
	{

	}

	@Override
	public final T get()
	{
		return this.value;
	}

}
