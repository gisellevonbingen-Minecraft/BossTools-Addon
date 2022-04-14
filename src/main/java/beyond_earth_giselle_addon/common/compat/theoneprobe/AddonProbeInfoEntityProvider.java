package beyond_earth_giselle_addon.common.compat.theoneprobe;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.compats.theoneprobe.GaugeValueElement;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class AddonProbeInfoEntityProvider implements IProbeInfoEntityProvider
{
	public static final AddonProbeInfoEntityProvider INSTANCE = new AddonProbeInfoEntityProvider();

	public AddonProbeInfoEntityProvider()
	{

	}

	@Override
	public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, Entity entity, IProbeHitEntityData hitData)
	{
		List<IGaugeValue> list = new ArrayList<>();
		EntityGaugeValueFetchEvent event = new EntityGaugeValueFetchEvent(entity);
		MinecraftForge.EVENT_BUS.post(event);
		list.addAll(event.getValues());
		list.stream().map(GaugeValueElement::new).forEach(probeInfo::element);
	}

	@Override
	public String getID()
	{
		return BeyondEarthAddon.rl("top_entity").toString();
	}

}
