package beyond_earth_giselle_addon.common.enchantment;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.config.EnchantmentsConfig;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class EventListenerEnchantmentTooltip
{
	private static final Set<String> DESCRIPTION_MODS = Sets.newHashSet("enchdesc", "cofh_core");

	private static boolean tooltipEnabeldCached = false;
	private static boolean tooltipEnabled = false;

	public static boolean tooltipEnabled()
	{
		if (tooltipEnabeldCached == true)
		{
			return tooltipEnabled;
		}
		else
		{
			EnchantmentsConfig config = AddonConfigs.Common.enchantments;
			return tooltipEnabled = config.tooltip_Enabled.get() && (config.tooltip_Ignore.get() || !isDescriptionModsLoaded());
		}

	}

	public static void addDescriptionMod(String modid)
	{
		if (DESCRIPTION_MODS.add(modid) == true)
		{
			tooltipEnabled = tooltipEnabled() && !ModList.get().isLoaded(modid);
		}

	}

	public static boolean isDescriptionModsLoaded()
	{
		for (String mod : DESCRIPTION_MODS)
		{
			if (ModList.get().isLoaded(mod) == true)
			{
				return true;
			}

		}

		return false;
	}

	public static Set<String> getDescriptionMods()
	{
		return Collections.unmodifiableSet(DESCRIPTION_MODS);
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent e)
	{
		ItemStack itemstack = e.getItemStack();

		if (itemstack.getItem() instanceof EnchantedBookItem)
		{
			if (tooltipEnabled() == false)
			{
				return;
			}

			List<Component> lines = e.getToolTip();

			for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getObjects())
			{
				for (Component line : lines)
				{
					if (line instanceof TranslatableComponent)
					{
						TranslatableComponent tline = (TranslatableComponent) line;

						if (tline.getKey().equals(enchantment.getDescriptionId()) == true)
						{
							lines.add(lines.indexOf(line) + 1, EnchantmentHelper2.getDescriptionText(enchantment));
							break;
						}

					}

				}

			}

		}

	}

}
