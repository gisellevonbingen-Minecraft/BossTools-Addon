package boss_tools_giselle_addon.common;

import boss_tools_giselle_addon.common.entity.BossToolsRocketHelper;
import boss_tools_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueSimple;

public class EventListenerGauge
{
	@SubscribeEvent
	public static void onEntityGaugeValueFetch(EntityGaugeValueFetchEvent e)
	{
		Entity entity = e.getEntity();

		if (BossToolsRocketHelper.isBossToolsRocket(entity))
		{
			int amount = BossToolsRocketHelper.getFuelAmount(entity);
			int capacity = BossToolsRocketHelper.getFuelCapacity(entity);
			e.getValues().add(new GaugeValueSimple(GaugeValueHelper.FUEL_NAME, (int) (amount / (capacity / 100.0D)), 100, (ITextComponent) null, "%").color(GaugeValueHelper.FUEL_COLOR));
		}

	}

}
