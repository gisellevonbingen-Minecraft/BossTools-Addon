package beyond_earth_giselle_addon.common.content.gravity;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forge.EntityGravityEvent;
import net.mrscauthd.beyond_earth.events.forge.ItemGravityEvent;

public class EventListenerGravityNormalizing
{
	@SubscribeEvent
	public static void onLivingGravity(EntityGravityEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

	@SubscribeEvent
	public static void onItemGravity(ItemGravityEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

}
