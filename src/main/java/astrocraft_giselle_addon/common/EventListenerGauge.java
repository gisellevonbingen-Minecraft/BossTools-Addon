package astrocraft_giselle_addon.common;

import astrocraft_giselle_addon.common.entity.AstroCraftRocketHelper;
import astrocraft_giselle_addon.common.entity.AstroCraftRoverHelper;
import astrocraft_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.astrocraft.entity.RoverEntity;
import net.mrscauthd.astrocraft.gauge.GaugeValueHelper;
import net.mrscauthd.astrocraft.gauge.GaugeValueSimple;

public class EventListenerGauge
{
	@SubscribeEvent
	public static void onEntityGaugeValueFetch(EntityGaugeValueFetchEvent e)
	{
		Entity entity = e.getEntity();

		if (entity instanceof RoverEntity)
		{
			int amount = AstroCraftRoverHelper.getFuelAmount(entity);
			int capacity = AstroCraftRoverHelper.getFuelCapacity(entity);
			e.getValues().add(GaugeValueHelper.getFuel(amount, capacity));
		}
		else if (AstroCraftRocketHelper.isAstroCraftRocket(entity))
		{
			int amount = AstroCraftRocketHelper.getFuelAmount(entity);
			int capacity = AstroCraftRocketHelper.getFuelCapacity(entity);
			e.getValues().add(new GaugeValueSimple(GaugeValueHelper.FUEL_NAME, (int) (amount / (capacity / 100.0D)), 100, (Component) null, "%").color(GaugeValueHelper.FUEL_COLOR));
		}

	}

}
