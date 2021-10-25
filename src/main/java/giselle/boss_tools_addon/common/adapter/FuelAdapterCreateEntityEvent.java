package giselle.boss_tools_addon.common.adapter;

import net.minecraft.entity.Entity;

public class FuelAdapterCreateEntityEvent extends AdapterCreateEvent<Entity, FuelAdapter<? extends Entity>>
{
	public FuelAdapterCreateEntityEvent(Entity target)
	{
		super(target);
	}

}
