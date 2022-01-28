package boss_tools_giselle_addon.client;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.client.event.RenderSpaceSuitOverlayEvent;
import boss_tools_giselle_addon.common.capability.ChargeMode;
import boss_tools_giselle_addon.common.capability.IOxygenCharger;
import boss_tools_giselle_addon.common.capability.OxygenChargerUtils;
import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.compat.curios.CuriosHelper;
import boss_tools_giselle_addon.common.item.AddonItems;
import boss_tools_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueSimple;

public class EventListenerRenderOxygenCanOverlay
{
	@SubscribeEvent
	public static void onRenderSpaceSuitOverlayEvent(RenderSpaceSuitOverlayEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		List<ItemStack> itemStacks = new ArrayList<>(player.inventory.items);

		if (AddonCompatibleManager.CURIOS.isLoaded() == true)
		{
			itemStacks.addAll(CuriosHelper.getEquippedCurios(player));
		}

		long stored = 0L;
		long capacity = 0L;

		for (ItemStack stack : itemStacks)
		{
			if (stack.isEmpty() == true)
			{
				continue;
			}

			IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

			if (oxygenCharger != null && oxygenCharger.getChargeMode() != ChargeMode.NONE)
			{
				IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
				stored = Math.min(stored + oxygenStorage.getOxygenStored(), Integer.MAX_VALUE);
				capacity = Math.min(capacity + oxygenStorage.getMaxOxygenStored(), Integer.MAX_VALUE);
			}

		}

		if (capacity > 0)
		{
			OxygenCanItem item = AddonItems.OXYGEN_CAN.get();
			GaugeValueSimple value = new GaugeValueSimple(item.getRegistryName(), (int) stored, (int) capacity, item.getName(item.getDefaultInstance()));
			event.getComponents().add(GaugeTextHelper.getPercentText(value).build());
		}

	}

}
