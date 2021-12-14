package boss_tools_giselle_addon.common.event;

import java.util.List;

import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public interface IGaugeValueFetchEvent
{
	public List<IGaugeValue> getValues();
}
