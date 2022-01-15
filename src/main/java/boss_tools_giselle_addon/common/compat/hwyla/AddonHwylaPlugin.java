package boss_tools_giselle_addon.common.compat.hwyla;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.RenderableTextComponent;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import net.mrscauthd.boss_tools.gauge.GaugeValueSerializer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

@WailaPlugin
public class AddonHwylaPlugin implements IWailaPlugin
{
	public static final ResourceLocation TOOLTIP = BossToolsAddon.rl("hwlya_tooltip");
	public static final ResourceLocation DATA_KEY = BossToolsAddon.rl("datakey");

	public static ListNBT write(List<IGaugeValue> list)
	{
		ListNBT nbt = new ListNBT();
		list.stream().map(GaugeValueSerializer.Serializer::serialize).forEach(nbt::add);
		return nbt;
	}

	public static List<IGaugeValue> read(ListNBT nbt)
	{
		List<IGaugeValue> list = new ArrayList<>();
		nbt.stream().map(h -> (CompoundNBT) h).map(GaugeValueSerializer.Serializer::deserialize).forEach(list::add);
		return list;
	}

	public static ListNBT get(CompoundNBT compound)
	{
		return compound.getList(DATA_KEY.toString(), NBT.TAG_COMPOUND);
	}

	public static void put(CompoundNBT compound, ListNBT nbt)
	{
		compound.put(DATA_KEY.toString(), nbt);
	}

	public static void apeendBody(List<ITextComponent> tooltip, CompoundNBT serverData)
	{
		ListNBT list = AddonHwylaPlugin.get(serverData);

		if (list.size() > 0)
		{
			CompoundNBT compound = new CompoundNBT();
			AddonHwylaPlugin.put(compound, list);
			tooltip.add(new RenderableTextComponent(AddonHwylaPlugin.TOOLTIP, compound));
		}

	}

	@Override
	public void register(IRegistrar registrar)
	{
		registrar.registerBlockDataProvider(AddonBlockDataProvider.INSTANCE, TileEntity.class);
		registrar.registerComponentProvider(AddonBlockDataProvider.INSTANCE, TooltipPosition.BODY, TileEntity.class);

		registrar.registerEntityDataProvider(AddonEntityDataProvider.INSTANCE, Entity.class);
		registrar.registerComponentProvider(AddonEntityDataProvider.INSTANCE, TooltipPosition.BODY, Entity.class);

		registrar.registerTooltipRenderer(TOOLTIP, AddonTooltipRenderer.INSTANCE);
	}

}