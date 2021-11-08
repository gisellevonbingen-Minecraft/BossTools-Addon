package boss_tools_giselle_addon;

import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsDataParameterBoolean;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsDataParameterInteger;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsNBT;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.events.Gravity;
import net.mrscauthd.boss_tools.events.Gravity.GravityType;
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

		if (target instanceof RoverEntity.CustomEntity)
		{
			e.setAdapter(new FuelAdapterBossToolsNBT(target, ModInnet.FUEL_BUCKET.get()));
		}
		else if (target instanceof RocketTier1Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsDataParameterBoolean(target, ModInnet.FUEL_BUCKET.get(), RocketTier1Entity.BUCKET));
		}
		else if (target instanceof RocketTier2Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsDataParameterInteger(target, ModInnet.FUEL_BUCKET.get(), RocketTier2Entity.BUCKETS, 3));
		}
		else if (target instanceof RocketTier3Entity)
		{
			e.setAdapter(new FuelAdapterBossToolsDataParameterInteger(target, ModInnet.FUEL_BUCKET.get(), RocketTier3Entity.BUCKETS, 3));
		}

	}

	@SubscribeEvent
	public void onLivingGravity(LivingGravityEvent e)
	{
		this.tryCancelGravity(e);
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingUpdateEvent e)
	{
		resetNormalizingWithCheckType(Gravity.GravityType.LIVING, e.getEntityLiving());
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent e)
	{
		if (e.phase == TickEvent.Phase.END)
		{
			resetNormalizingWithCheckType(Gravity.GravityType.PLAYER, e.player);
		}

	}

	public static void resetNormalizingWithCheckType(GravityType living, LivingEntity entityLiving)
	{
		if (Gravity.checkType(living, entityLiving) == true)
		{
			if (GravityNormalizerTileEntity.isNormalizing(entityLiving) == true)
			{
				GravityNormalizerTileEntity.setNormalizing(entityLiving, false);
			}

		}

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

		if (GravityNormalizerTileEntity.isNormalizing(entity) == true)
		{
			e.setCanceled(true);
		}

	}

}
