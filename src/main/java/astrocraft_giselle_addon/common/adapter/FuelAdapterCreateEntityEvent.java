package astrocraft_giselle_addon.common.adapter;

import net.minecraft.world.entity.Entity;

public class FuelAdapterCreateEntityEvent extends AdapterCreateEvent<Entity, FuelAdapter<? extends Entity>>
{
	public FuelAdapterCreateEntityEvent(Entity target)
	{
		super(target);
	}

}
