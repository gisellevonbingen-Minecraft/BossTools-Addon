package boss_tools_giselle_addon.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class BlockDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider
{
	public static final BlockDataProvider INSTANCE = new BlockDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity tileEntity)
	{
		List<IGaugeValue> list = new ArrayList<>();

		HwylaPlugin.put(data, HwylaPlugin.write(list));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config)
	{
		IComponentProvider.super.appendBody(tooltip, accessor, config);
		HwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
