package beyond_earth_giselle_addon.common.compat.theoneprobe;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.event.BlockGaugeValueFetchEvent;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.beyond_earth.compats.theoneprobe.GaugeValueElement;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class AddonProbeInfoBlockProvider implements IProbeInfoProvider
{
	public static final AddonProbeInfoBlockProvider INSTANCE = new AddonProbeInfoBlockProvider();

	public AddonProbeInfoBlockProvider()
	{

	}

	@Override
	public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, BlockState blockState, IProbeHitData hitData)
	{
		List<IGaugeValue> list = new ArrayList<>();
		BlockEntity blockEntity = level.getBlockEntity(hitData.getPos());

		if (blockEntity != null)
		{
			BlockGaugeValueFetchEvent event = new BlockGaugeValueFetchEvent(blockEntity);
			MinecraftForge.EVENT_BUS.post(event);
			list.addAll(event.getValues());
		}

		list.stream().map(GaugeValueElement::new).forEach(probeInfo::element);
	}

	@Override
	public ResourceLocation getID()
	{
		return BeyondEarthAddon.rl("top_block");
	}

}
