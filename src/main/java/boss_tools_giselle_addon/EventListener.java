package boss_tools_giselle_addon;

import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsBucket;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsBuckets;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsFuel;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;

public class EventListener
{
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
			e.setAdapter(new FuelAdapterBossToolsFuel(target, ModInnet.FUEL_BUCKET.get(), RoverEntity.FUEL, 3000));
		}
		else if (target instanceof RocketTier1Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsBucket(target, ModInnet.FUEL_BUCKET.get(), RocketTier1Entity.BUCKET));
		}
		else if (target instanceof RocketTier2Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsBuckets(target, ModInnet.FUEL_BUCKET.get(), RocketTier2Entity.BUCKETS, 3));
		}
		else if (target instanceof RocketTier3Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsBuckets(target, ModInnet.FUEL_BUCKET.get(), RocketTier3Entity.BUCKETS, 3));
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
