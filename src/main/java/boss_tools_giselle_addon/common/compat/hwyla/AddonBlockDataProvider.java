package boss_tools_giselle_addon.common.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.event.TileGaugeValueFetchEvent;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class AddonBlockDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider
{
	public static final AddonBlockDataProvider INSTANCE = new AddonBlockDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity tileEntity)
	{
		List<IGaugeValue> list = new ArrayList<>();
		TileGaugeValueFetchEvent event = new TileGaugeValueFetchEvent(tileEntity);
		MinecraftForge.EVENT_BUS.post(event);
		list.addAll(event.getValues());
		AddonHwylaPlugin.put(data, AddonHwylaPlugin.write(list));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		IComponentProvider.super.appendBody(tooltip, accessor, config);
		AddonHwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
