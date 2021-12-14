package boss_tools_giselle_addon.common.compat.theoneprobe;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.event.TileGaugeValueFetchEvent;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.compat.theoneprobe.GaugeValueElement;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class AddonProbeInfoBlockProvider implements IProbeInfoProvider
{
	public static final AddonProbeInfoBlockProvider INSTANCE = new AddonProbeInfoBlockProvider();

	public AddonProbeInfoBlockProvider()
	{

	}

	@Override
	public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, PlayerEntity player, World level, BlockState blockState, IProbeHitData hitData)
	{
		List<IGaugeValue> list = new ArrayList<>();
		TileEntity tileEntity = level.getBlockEntity(hitData.getPos());

		if (tileEntity != null)
		{
			TileGaugeValueFetchEvent event = new TileGaugeValueFetchEvent(tileEntity);
			MinecraftForge.EVENT_BUS.post(event);
			list.addAll(event.getValues());
		}

		list.stream().map(GaugeValueElement::new).forEach(probeInfo::element);
	}

	@Override
	public String getID()
	{
		return BossToolsAddon.rl("top_block").toString();
	}

}
