package boss_tools_giselle_addon.common.compat.theoneprobe;

import java.util.function.Function;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ProbeInfoProvider implements IProbeInfoProvider, IProbeInfoEntityProvider, Function<ITheOneProbe, Void>
{
	public static int ELEMENT_ID;

	public ProbeInfoProvider()
	{

	}

	@Override
	public Void apply(ITheOneProbe top)
	{
		top.registerProvider(this);
		top.registerEntityProvider(this);
		top.registerProbeConfigProvider(ProbeConfigProvider.INSTANCE);
		ELEMENT_ID = top.registerElementFactory(GaugeValueElement::new);

		return null;
	}

	@Override
	public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData hitData)
	{

	}

	@Override
	public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData hitData)
	{
		EntityGaugeValueFetchEvent event = new EntityGaugeValueFetchEvent(entity);
		MinecraftForge.EVENT_BUS.post(event);
		event.getValues().stream().map(GaugeValueElement::new).forEach(probeInfo::element);
	}

	@Override
	public String getID()
	{
		return BossToolsAddon.rl("top").toString();
	}

}
