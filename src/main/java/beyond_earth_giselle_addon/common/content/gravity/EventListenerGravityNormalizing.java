package beyond_earth_giselle_addon.common.content.gravity;

import beyond_earth_giselle_addon.common.content.proof.LivingSpaceGravityProofEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forge.ItemGravityEvent;

public class EventListenerGravityNormalizing
{
	@SubscribeEvent
	public static void onLivingGravity(LivingSpaceGravityProofEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

	@SubscribeEvent
	public static void onItemGravity(ItemGravityEvent e)
	{
		GravityNormalizeUtils.tryCancelGravity(e);
	}

}
