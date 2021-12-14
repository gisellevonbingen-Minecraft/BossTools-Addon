package boss_tools_giselle_addon.common.compat.theoneprobe;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.compat.theoneprobe.GaugeValueElement;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class AddonProbeInfoEntityProvider implements IProbeInfoEntityProvider
{
	public static final AddonProbeInfoEntityProvider INSTANCE = new AddonProbeInfoEntityProvider();

	public AddonProbeInfoEntityProvider()
	{

	}

	@Override
	public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity player, World level, Entity entity, IProbeHitEntityData hitData)
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
		return BossToolsAddon.rl("top_entity").toString();
	}

}
