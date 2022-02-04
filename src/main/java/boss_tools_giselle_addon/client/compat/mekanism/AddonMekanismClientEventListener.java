package boss_tools_giselle_addon.client.compat.mekanism;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import mekanism.client.key.MekKeyHandler;
import mekanism.client.key.MekanismKeyHandler;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AddonMekanismClientEventListener
{
	@SubscribeEvent
	public static void onItemTooltipEvent(ItemTooltipEvent e)
	{
		Item item = e.getItemStack().getItem();

		if (MekKeyHandler.isKeyPressed(MekanismKeyHandler.detailsKey) == false && AddonConfigs.Common.mekanism.moduleGravitationalModulating_normalizable.get() == true)
		{
			if (item == MekanismItems.MODULE_GRAVITATIONAL_MODULATING.get())
			{
				e.getToolTip().add(new TranslationTextComponent(BossToolsAddon.tl("description", "instead_gravity_normalizing_unit")));
			}

		}

	}

	private AddonMekanismClientEventListener()
	{

	}

}
