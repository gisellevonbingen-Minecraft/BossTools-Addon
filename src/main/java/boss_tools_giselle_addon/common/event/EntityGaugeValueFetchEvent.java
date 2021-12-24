package boss_tools_giselle_addon.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class EntityGaugeValueFetchEvent extends EntityEvent implements IGaugeValueFetchEvent
{
	private final List<IGaugeValue> values;

	public EntityGaugeValueFetchEvent(Entity entity)
	{
		super(entity);
		this.values = new ArrayList<>();
	}

	@Override
	public List<IGaugeValue> getValues()
	{
		return this.values;
	}

}