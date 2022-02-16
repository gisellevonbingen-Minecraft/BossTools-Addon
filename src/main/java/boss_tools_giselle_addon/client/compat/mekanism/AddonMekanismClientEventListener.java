package boss_tools_giselle_addon.client.compat.mekanism;

import java.util.List;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.item.ItemModule;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AddonMekanismClientEventListener
{
	private static TranslationTextComponent CACHED_GRAVITATIONAL_MODULATING_TEXT = null;

	public static ITextComponent getGravitationalModulatingModuleDescriptionText()
	{
		if (CACHED_GRAVITATIONAL_MODULATING_TEXT == null)
		{
			CACHED_GRAVITATIONAL_MODULATING_TEXT = new TranslationTextComponent(BossToolsAddon.tl("description", "instead_gravity_normalizing_unit"));
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
				List<ITextComponent> toolTip = e.getToolTip();
				String descriptionKey = moduleItem.getModuleData().getDescriptionTranslationKey();

				for (int i = 0; i < toolTip.size(); i++)
				{
					ITextComponent line = toolTip.get(i);

					if (line instanceof TranslationTextComponent)
					{
						TranslationTextComponent tline = (TranslationTextComponent) line;

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
