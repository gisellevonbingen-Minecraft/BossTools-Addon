package boss_tools_giselle_addon.common.compat.hwyla;

import java.awt.Dimension;

import com.mojang.blaze3d.matrix.MatrixStack;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;
import net.mrscauthd.boss_tools.gauge.GaugeValueSerializer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class AddonTooltipRenderer implements ITooltipRenderer
{
	public static final AddonTooltipRenderer INSTANCE = new AddonTooltipRenderer();

	@Override
	public Dimension getSize(CompoundNBT compound, ICommonAccessor accessor)
	{
		return new Dimension(102, 15 * AddonHwylaPlugin.get(compound).size());
	}

	@Override
	public void draw(CompoundNBT compound, ICommonAccessor accessor, int x, int y)
	{
		ListNBT list = AddonHwylaPlugin.get(compound);
		MatrixStack stack = new MatrixStack();

		for (int i = 0; i < list.size(); i++)
		{
			IGaugeValue value = GaugeValueSerializer.Serializer.deserialize(list.getCompound(i));
			GaugeValueRenderer renderer = new GaugeValueRenderer(value);
			renderer.render(stack, x + 1, y);
			y += renderer.getHeight() + 1;
		}

	}

}