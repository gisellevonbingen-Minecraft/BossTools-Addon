package beyond_earth_giselle_addon.common.content.gravitynormalizing;

import beyond_earth_giselle_addon.common.util.GravityNormalizeUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.ItemGravityEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.LivingGravityEvent;

public class EventListenerGravityNormalizing
{
	@SubscribeEvent
	public static void onLivingGravity(LivingGravityEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

	@SubscribeEvent
	public static void onItemGravity(ItemGravityEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

}
