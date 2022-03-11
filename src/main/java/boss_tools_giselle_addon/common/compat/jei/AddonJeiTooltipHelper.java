package boss_tools_giselle_addon.common.compat.jei;

import java.util.HashMap;
import java.util.Map;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AddonJeiTooltipHelper
{
	public static final String INCOMPATIABLE1_KEY = BossToolsAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode1");
	public static final ITextComponent INCOMPATIABLE1_TEXT = new TranslationTextComponent(INCOMPATIABLE1_KEY);

	public static final String INCOMPATIABLE2_KEY = BossToolsAddon.tl(AddonJeiPlugin.JEI_TOOLTIP, "incompatible_mode2");
	private static final Map<Object, ITextComponent> INCOMPATIABLE_MAP = new HashMap<>();

	public static ITextComponent getIncompatibleModeText(Object require)
	{
		if (require == null)
		{
			return INCOMPATIABLE1_TEXT;
		}
		else
		{
			return INCOMPATIABLE_MAP.computeIfAbsent(require, AddonJeiTooltipHelper::createIncompatibleModeText);
		}

	}

	public static ITextComponent createIncompatibleModeText(Object require)
	{
		return new TranslationTextComponent(INCOMPATIABLE1_KEY).append("\n").append(new TranslationTextComponent(INCOMPATIABLE2_KEY, require));
	}

	private AddonJeiTooltipHelper()
	{

	}

}
