package boss_tools_giselle_addon;

import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsRocket;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsRover;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.entity.BossToolsRocketHelper;
import boss_tools_giselle_addon.common.entity.BossToolsRoverHelper;
import boss_tools_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueSimple;

public class EventListener
{
	@SubscribeEvent
	public void onEntityGaugeValueFetch(EntityGaugeValueFetchEvent e)
	{
		Entity entity = e.getEntity();

		if (entity instanceof RoverEntity)
		{
			int amount = BossToolsRoverHelper.getFuelAmount(entity);
			int capacity = BossToolsRoverHelper.getFuelCapacity(entity);
			e.getValues().add(GaugeValueHelper.getFuel(amount, capacity));
		}
		else if (BossToolsRocketHelper.isBossToolsRocket(entity))
		{
			int amount = BossToolsRocketHelper.getFuelAmount(entity);
			int capacity = BossToolsRocketHelper.getFuelCapacity(entity);
			e.getValues().add(new GaugeValueSimple(GaugeValueHelper.FUEL_NAME, (int)(amount / (capacity / 100.0D)), 100, (ITextComponent) null, "%").color(GaugeValueHelper.FUEL_COLOR));
		}

	}

	@SubscribeEvent
	public void onFuelAdapterCreateEntity(FuelAdapterCreateEntityEvent e)
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

	@SubscribeEvent
	public void onLivingGravity(LivingGravityEvent e)
	{
		this.tryCancelGravity(e);
	}

	@SubscribeEvent
	public void onItemGravity(ItemGravityEvent e)
	{
		this.tryCancelGravity(e);
	}

	public void tryCancelGravity(EntityEvent e)
	{
		if (e.isCanceled() == true)
		{
			return;
		}

		Entity entity = e.getEntity();

		if (GravityNormalizeUtils.isNormalizing(entity) == true)
		{
			e.setCanceled(true);
			GravityNormalizeUtils.setNormalizing(entity, false);
		}

	}

}
