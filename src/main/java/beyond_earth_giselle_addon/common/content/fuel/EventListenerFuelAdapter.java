package beyond_earth_giselle_addon.common.content.fuel;

import beyond_earth_giselle_addon.common.adapter.FuelAdapterBeyondEarthRocket;
import beyond_earth_giselle_addon.common.adapter.FuelAdapterBeyondEarthRover;
import beyond_earth_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import beyond_earth_giselle_addon.common.entity.BeyondEarthRocketHelper;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.entities.RoverEntity;

public class EventListenerFuelAdapter
{
	@SubscribeEvent
	public static void onFuelAdapterCreateEntity(FuelAdapterCreateEntityEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		Entity target = e.getTaget();

		if (target instanceof RoverEntity)
		{
			e.setAdapter(new FuelAdapterBeyondEarthRover(target));
		}
		else if (BeyondEarthRocketHelper.isBeyondEarthRocket(target))
		{
			e.setAdapter(new FuelAdapterBeyondEarthRocket(target));
		}

	}

}
