package beyond_earth_giselle_addon.client.compat.mekanism;

import java.util.List;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.item.ItemModule;
import mekanism.common.registries.MekanismItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AddonMekanismClientEventListener
{
	private static TranslatableComponent CACHED_GRAVITATIONAL_MODULATING_TEXT = null;

	public static Component getGravitationalModulatingModuleDescriptionText()
	{
		if (CACHED_GRAVITATIONAL_MODULATING_TEXT == null)
		{
			CACHED_GRAVITATIONAL_MODULATING_TEXT = new TranslatableComponent(BeyondEarthAddon.tl("description", "instead_gravity_normalizing_unit"));
		}

		return CACHED_GRAVITATIONAL_MODULATING_TEXT;
	}

	@SubscribeEvent
	public static void onItemTooltipEvent(ItemTooltipEvent e)
	{
		Item item = e.getItemStack().getItem();

		if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey) == false && AddonConfigs.Common.mekanism.moduleGravitationalModulating_normalizable.get() == true)
		{
			ItemModule moduleItem = MekanismItems.MODULE_GRAVITATIONAL_MODULATING.get();

			if (item == moduleItem)
			{
				List<Component> toolTip = e.getToolTip();
				String descriptionKey = moduleItem.getModuleData().getDescriptionTranslationKey();

				for (int i = 0; i < toolTip.size(); i++)
				{
					Component line = toolTip.get(i);

					if (line instanceof TranslatableComponent)
					{
						TranslatableComponent tline = (TranslatableComponent) line;

						if (tline.getKey().equals(descriptionKey) == true)
						{
							toolTip.add(i + 1, getGravitationalModulatingModuleDescriptionText());
							break;
						}

					}

				}

			}

		}

	}

	private AddonMekanismClientEventListener()
	{

	}

}
