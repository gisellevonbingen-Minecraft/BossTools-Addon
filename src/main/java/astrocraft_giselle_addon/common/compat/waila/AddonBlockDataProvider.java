package astrocraft_giselle_addon.common.compat.waila;

import java.util.ArrayList;
import java.util.List;

import astrocraft_giselle_addon.common.event.BlockGaugeValueFetchEvent;
import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.astrocraft.compat.waila.WailaPlugin;
import net.mrscauthd.astrocraft.gauge.IGaugeValue;

public class AddonBlockDataProvider implements IServerDataProvider<BlockEntity>, IComponentProvider
{
	public static final AddonBlockDataProvider INSTANCE = new AddonBlockDataProvider();

	@Override
	public void appendServerData(CompoundTag data, ServerPlayer player, Level level, BlockEntity blockEntity, boolean b)
	{
		List<IGaugeValue> list = new ArrayList<>();
		BlockGaugeValueFetchEvent event = new BlockGaugeValueFetchEvent(blockEntity);
		MinecraftForge.EVENT_BUS.post(event);
		list.addAll(event.getValues());
		WailaPlugin.put(data, WailaPlugin.write(list));
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config)
	{

	}

}
