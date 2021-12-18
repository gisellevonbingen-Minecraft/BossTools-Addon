package beyond_earth_giselle_addon.common;

import beyond_earth_giselle_addon.common.entity.BeyondEarthRocketHelper;
import beyond_earth_giselle_addon.common.entity.BeyondEarthRoverHelper;
import beyond_earth_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.entity.RoverEntity;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueSimple;

public class EventListenerGauge
{
	@SubscribeEvent
	public static void onEntityGaugeValueFetch(EntityGaugeValueFetchEvent e)
	{
		Entity entity = e.getEntity();

		if (entity instanceof RoverEntity)
		{
			int amount = BeyondEarthRoverHelper.getFuelAmount(entity);
			int capacity = BeyondEarthRoverHelper.getFuelCapacity(entity);
			e.getValues().add(GaugeValueHelper.getFuel(amount, capacity));
		}
		else if (BeyondEarthRocketHelper.isAstroCraftRocket(entity))
		{
			int amount = BeyondEarthRocketHelper.getFuelAmount(entity);
			int capacity = BeyondEarthRocketHelper.getFuelCapacity(entity);
			e.getValues().add(new GaugeValueSimple(GaugeValueHelper.FUEL_NAME, (int) (amount / (capacity / 100.0D)), 100, (Component) null, "%").color(GaugeValueHelper.FUEL_COLOR));
		}

	}

}
