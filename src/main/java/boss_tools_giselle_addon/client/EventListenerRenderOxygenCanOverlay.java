package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.client.event.RenderSpaceSuitOverlayEvent;
import boss_tools_giselle_addon.common.capability.OxygenChargerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class EventListenerRenderOxygenCanOverlay
{
	@SubscribeEvent
	public static void onRenderSpaceSuitOverlayEvent(RenderSpaceSuitOverlayEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		IGaugeValue value = OxygenChargerUtils.getInventoryOxygenChargerStorage(player);

		if (value.getCapacity() > 0)
		{
			event.getComponents().add(GaugeTextHelper.getPercentText(value).build());
		}

	}

}
