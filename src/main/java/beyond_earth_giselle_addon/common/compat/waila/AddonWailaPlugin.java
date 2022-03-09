package beyond_earth_giselle_addon.common.compat.waila;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mrscauthd.beyond_earth.compat.waila.GaugeValueElement;
import net.mrscauthd.beyond_earth.gauge.GaugeValueRenderer;
import net.mrscauthd.beyond_earth.gauge.GaugeValueSerializer;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

@mcp.mobius.waila.api.WailaPlugin
public class AddonWailaPlugin implements IWailaPlugin
{
	public static final ResourceLocation DATA_KEY = BeyondEarthAddon.rl("waila_datakey");

	public static ListTag write(List<IGaugeValue> list)
	{
		ListTag nbt = new ListTag();
		list.stream().map(GaugeValueSerializer.Serializer::serialize).forEach(nbt::add);
		return nbt;
	}

	public static List<IGaugeValue> read(ListTag nbt)
	{
		List<IGaugeValue> list = new ArrayList<>();
		nbt.stream().map(h -> (CompoundTag) h).map(GaugeValueSerializer.Serializer::deserialize).forEach(list::add);
		return list;
	}

	public static ListTag get(CompoundTag compound)
	{
		return compound.getList(DATA_KEY.toString(), 10);
	}

	public static void put(CompoundTag compound, ListTag nbt)
	{
		compound.put(DATA_KEY.toString(), nbt);
	}

	public static void appendTooltip(ITooltip tooltip, CompoundTag serverData)
	{
		ListTag list = get(serverData);

		for (int i = 0; i < list.size(); i++)
		{
			IGaugeValue value = GaugeValueSerializer.Serializer.deserialize(list.getCompound(i));
			tooltip.add(new GaugeValueElement(new GaugeValueRenderer(value)));
		}

	}

	@Override
	public void register(IWailaCommonRegistration registration)
	{
		IWailaPlugin.super.register(registration);
		registration.registerBlockDataProvider(AddonBlockDataProvider.INSTANCE, BlockEntity.class);
		registration.registerEntityDataProvider(AddonEntityDataProvider.INSTANCE, Entity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration)
	{
		IWailaPlugin.super.registerClient(registration);
		registration.registerComponentProvider(AddonEntityDataProvider.INSTANCE, TooltipPosition.BODY, Entity.class);
		registration.registerComponentProvider(AddonBlockDataProvider.INSTANCE, TooltipPosition.BODY, Block.class);
	}

}
