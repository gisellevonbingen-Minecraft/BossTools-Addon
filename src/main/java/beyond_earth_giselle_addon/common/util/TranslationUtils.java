package beyond_earth_giselle_addon.common.util;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.capability.IChargeMode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class TranslationUtils
{
	public static Component descriptionChargeMode(IChargeMode mode)
	{
		MutableComponent value = new TextComponent("").withStyle(ChatFormatting.WHITE).append(mode != null ? mode.getDisplayName() : TextComponent.EMPTY);
		return new TranslatableComponent(BeyondEarthAddon.tl("description", "charge_mode")).withStyle(ChatFormatting.BLUE).append(": ").append(value);
	}

	private TranslationUtils()
	{

	}

}
