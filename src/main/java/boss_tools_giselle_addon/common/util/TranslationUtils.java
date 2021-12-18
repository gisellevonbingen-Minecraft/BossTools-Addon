package boss_tools_giselle_addon.common.util;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.capability.IChargeMode;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TranslationUtils
{
	public static ITextComponent descriptionChargeMode(IChargeMode mode)
	{
		IFormattableTextComponent value = new StringTextComponent("").withStyle(TextFormatting.WHITE).append(mode != null ? mode.getDisplayName() : StringTextComponent.EMPTY);
		return new TranslationTextComponent(BossToolsAddon.tl("description", "charge_mode")).withStyle(TextFormatting.BLUE).append(": ").append(value);
	}

	private TranslationUtils()
	{

	}

}
