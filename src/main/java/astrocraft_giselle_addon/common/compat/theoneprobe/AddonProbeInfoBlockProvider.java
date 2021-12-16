package astrocraft_giselle_addon.common.compat.theoneprobe;

import java.util.ArrayList;
import java.util.List;

import astrocraft_giselle_addon.common.BossToolsAddon;
import astrocraft_giselle_addon.common.event.BlockGaugeValueFetchEvent;
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
import net.mrscauthd.astrocraft.compat.theoneprobe.GaugeValueElement;
import net.mrscauthd.astrocraft.gauge.IGaugeValue;

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
		return BossToolsAddon.rl("top_block");
	}

}
