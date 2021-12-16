package astrocraft_giselle_addon.common.event;

import java.util.List;

import net.mrscauthd.astrocraft.gauge.IGaugeValue;

public interface IGaugeValueFetchEvent
{
	public List<IGaugeValue> getValues();
}
