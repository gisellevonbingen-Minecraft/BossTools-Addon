package boss_tools_giselle_addon.common.content.gravitynormalizing;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingGravityEvent;

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