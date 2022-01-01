package boss_tools_giselle_addon.common;

import boss_tools_giselle_addon.common.content.alien.AddonAlienTrade;
import boss_tools_giselle_addon.common.resources.ReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerReload
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onAddReloadListener(AddReloadListenerEvent event)
	{
		event.addListener(ReloadListener.INSTANCE);
		AddonAlienTrade.addReloadListener(event);
	}

}
