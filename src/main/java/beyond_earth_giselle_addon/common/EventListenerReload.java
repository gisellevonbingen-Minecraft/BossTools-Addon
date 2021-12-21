package beyond_earth_giselle_addon.common;

import beyond_earth_giselle_addon.common.resources.ReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerReload
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAddReloadListener(AddReloadListenerEvent event)
	{
		event.addListener(ReloadListener.INSTANCE);
	}

}
