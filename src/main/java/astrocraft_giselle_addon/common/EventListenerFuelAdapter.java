package astrocraft_giselle_addon.common;

import astrocraft_giselle_addon.common.adapter.FuelAdapterBossToolsRocket;
import astrocraft_giselle_addon.common.adapter.FuelAdapterBossToolsRover;
import astrocraft_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import astrocraft_giselle_addon.common.entity.BossToolsRocketHelper;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.astrocraft.entity.RoverEntity;

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
			e.setAdapter(new FuelAdapterBossToolsRover(target));
		}
		else if (BossToolsRocketHelper.isBossToolsRocket(target))
		{
			e.setAdapter(new FuelAdapterBossToolsRocket(target));
		}

	}

}
