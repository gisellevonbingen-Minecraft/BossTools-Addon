package beyond_earth_giselle_addon.common.util;

import java.util.HashMap;
import java.util.Map;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.capability.IChargeMode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class TranslationUtils
{
	private static final Map<IChargeMode, Component> CHANGE_MODES = new HashMap<>();

	public static final String CHARGE_MODE = BeyondEarthAddon.tl("description", "charge_mode");

	public static Component description(String key, Component component)
	{
		MutableComponent value = new TextComponent("").withStyle(ChatFormatting.WHITE).append(component);
		return new TranslatableComponent(key).withStyle(ChatFormatting.BLUE).append(": ").append(value);
	}

	public static Component descriptionChargeMode(IChargeMode mode)
	{
		if (mode == null)
		{
			return TextComponent.EMPTY;
		}

		return CHANGE_MODES.computeIfAbsent(mode, k -> description(CHARGE_MODE, k.getDisplayName()));
	}

	private TranslationUtils()
	{

	}

}
