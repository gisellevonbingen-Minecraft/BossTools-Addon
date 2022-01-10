package beyond_earth_giselle_addon.client;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.capability.CapabilityOxygenCharger;
import beyond_earth_giselle_addon.common.capability.ChargeMode;
import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.compat.curios.CuriosHelper;
import beyond_earth_giselle_addon.common.item.AddonItems;
import beyond_earth_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueSimple;

public class EventListenerRenderOxygenCanOverlay
{
	@SubscribeEvent
	public static void onRenderSpaceSuitOverlayEvent(beyond_earth_giselle_addon.client.event.RenderSpaceSuitOverlayEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		List<ItemStack> itemStacks = new ArrayList<>(player.getInventory().items);

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

			IOxygenCharger oxygenCharger = stack.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);

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
