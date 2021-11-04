package boss_tools_giselle_addon;

import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsDataParameterBoolean;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsDataParameterInteger;
import boss_tools_giselle_addon.common.adapter.FuelAdapterBossToolsNBT;
import boss_tools_giselle_addon.common.adapter.FuelAdapterCreateEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;

public class EventListener
{
	@SubscribeEvent
	public void onFuelAdapterCreateEntity(FuelAdapterCreateEntityEvent e)
	{
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

}
