package astrocraft_giselle_addon.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.mrscauthd.astrocraft.gauge.IGaugeValue;

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
