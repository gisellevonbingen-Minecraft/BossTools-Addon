package boss_tools_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AddonJeiTooltipHelper
{
	public static final String INCOMPATIABLE1_KEY = BossToolsAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode1");
	public static final ITextComponent INCOMPATIABLE1_TEXT = new TranslationTextComponent(INCOMPATIABLE1_KEY);

	public static final String INCOMPATIABLE2_KEY = BossToolsAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode2");
	private static final Map<Object, ITextComponent> INCOMPATIABLE2_MAP = new HashMap<>();

	public static List<ITextComponent> getIncompatibleModeTooltip(Object require)
	{
		List<ITextComponent> tooltip = new ArrayList<>();
		tooltip.add(INCOMPATIABLE1_TEXT);

		if (require != null)
		{
			tooltip.add(INCOMPATIABLE2_MAP.computeIfAbsent(require, AddonJeiTooltipHelper::createIncompatibleModeText2));
		}

		return tooltip;
	}

	public static ITextComponent createIncompatibleModeText2(Object require)
	{
		return new TranslationTextComponent(INCOMPATIABLE2_KEY, require);
	}

	private AddonJeiTooltipHelper()
	{

	}

}
