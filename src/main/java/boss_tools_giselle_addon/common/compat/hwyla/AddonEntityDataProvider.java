package boss_tools_giselle_addon.common.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.event.EntityGaugeValueFetchEvent;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class AddonEntityDataProvider implements IServerDataProvider<Entity>, IEntityComponentProvider
{
	public static final AddonEntityDataProvider INSTANCE = new AddonEntityDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, Entity entity)
	{
		List<IGaugeValue> list = new ArrayList<>();
		EntityGaugeValueFetchEvent event = new EntityGaugeValueFetchEvent(entity);
		MinecraftForge.EVENT_BUS.post(event);
		list.addAll(event.getValues());
		AddonHwylaPlugin.put(data, AddonHwylaPlugin.write(list));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config)
	{
		IEntityComponentProvider.super.appendBody(tooltip, accessor, config);
		AddonHwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
