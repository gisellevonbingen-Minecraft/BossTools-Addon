package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.BossToolsAddon;
import boss_tools_giselle_addon.config.AddonConfigs;
import mekanism.client.MekKeyHandler;
import mekanism.client.MekanismKeyHandler;
import mekanism.common.content.gear.Modules;
import mekanism.common.registries.MekanismItems;
import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
	public ClientEventHandler()
	{

	}

	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent e)
	{
		Item item = e.getItemStack().getItem();

		if (MekKeyHandler.isKeyDown(MekanismKeyHandler.detailsKey) == false && AddonConfigs.Common.mekanism.moduleGravitationalModulating_normalizable.get() == true)
		{
			if (item == MekanismItems.MODULES.get(Modules.GRAVITATIONAL_MODULATING_UNIT).get())
			{
				e.getToolTip().add(new TranslationTextComponent(BossToolsAddon.tl("description", "instead_gravity_normalizing_unit")));
			}

		}

	}

}
