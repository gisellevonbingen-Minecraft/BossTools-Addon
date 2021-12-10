package boss_tools_giselle_addon.common.compat.hwyla;

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

public class EntityDataProvider implements IServerDataProvider<Entity>, IEntityComponentProvider
{

	public static final EntityDataProvider INSTANCE = new EntityDataProvider();

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, Entity entity)
	{
		EntityGaugeValueFetchEvent event = new EntityGaugeValueFetchEvent(entity);
		MinecraftForge.EVENT_BUS.post(event);
		HwylaPlugin.put(data, HwylaPlugin.write(event.getValues()));
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config)
	{
		IEntityComponentProvider.super.appendBody(tooltip, accessor, config);
		HwylaPlugin.apeendBody(tooltip, accessor.getServerData());
	}

}
