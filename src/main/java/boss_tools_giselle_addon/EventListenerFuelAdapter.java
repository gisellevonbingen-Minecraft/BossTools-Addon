package boss_tools_giselle_addon;

import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsRocket;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsRover;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.entity.BossToolsRocketHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.entity.RoverEntity;

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
