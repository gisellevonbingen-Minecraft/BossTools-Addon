package beyond_earth_giselle_addon.client;

import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class EventListenerRenderOxygenCanOverlay
{
	@SubscribeEvent
	public static void onRenderSpaceSuitOverlayEvent(beyond_earth_giselle_addon.client.event.RenderSpaceSuitOverlayEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		IGaugeValue value = OxygenChargerUtils.getInventoryOxygenChargerStorage(player);

		if (value.getCapacity() > 0)
		{
			event.getComponents().add(GaugeTextHelper.getPercentText(value).build());
		}

	}

}
