package beyond_earth_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.network.chat.Component;

public class AddonJeiTooltipHelper
{
	public static final String INCOMPATIABLE1_KEY = BeyondEarthAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode1");
	public static final Component INCOMPATIABLE1_TEXT = Component.translatable(INCOMPATIABLE1_KEY);

	public static final String INCOMPATIABLE2_KEY = BeyondEarthAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode2");
	private static final Map<Object, Component> INCOMPATIABLE2_MAP = new HashMap<>();

	public static List<Component> getIncompatibleModeTooltip(Object require)
	{
		List<Component> tooltip = new ArrayList<>();
		tooltip.add(INCOMPATIABLE1_TEXT);

		if (require != null)
		{
			tooltip.add(INCOMPATIABLE2_MAP.computeIfAbsent(require, AddonJeiTooltipHelper::createIncompatibleModeText2));
		}

		return tooltip;
	}

	public static Component createIncompatibleModeText2(Object require)
	{
		return Component.translatable(INCOMPATIABLE2_KEY, require);
	}

	private AddonJeiTooltipHelper()
	{

	}

}
