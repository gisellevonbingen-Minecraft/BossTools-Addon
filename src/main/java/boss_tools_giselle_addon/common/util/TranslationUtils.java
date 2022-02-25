package boss_tools_giselle_addon.common.util;

import java.util.HashMap;
import java.util.Map;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.capability.IChargeMode;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TranslationUtils
{
	private static final Map<IChargeMode, ITextComponent> CHANGE_MODES = new HashMap<>();

	public static final String CHARGE_MODE = BossToolsAddon.tl("description", "charge_mode");

	public static ITextComponent description(String key, ITextComponent component)
	{
		IFormattableTextComponent value = new StringTextComponent("").withStyle(TextFormatting.WHITE).append(component);
		return new TranslationTextComponent(key).withStyle(TextFormatting.BLUE).append(": ").append(value);
	}

	public static ITextComponent descriptionChargeMode(IChargeMode mode)
	{
		if (mode == null)
		{
			return StringTextComponent.EMPTY;
		}

		return CHANGE_MODES.computeIfAbsent(mode, k -> description(CHARGE_MODE, k.getDisplayName()));
	}

	private TranslationUtils()
	{

	}

}
