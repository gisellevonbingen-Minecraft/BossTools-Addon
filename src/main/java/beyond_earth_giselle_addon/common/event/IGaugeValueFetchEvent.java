package beyond_earth_giselle_addon.common.event;

import java.util.List;

import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public interface IGaugeValueFetchEvent
{
	public List<IGaugeValue> getValues();
}
