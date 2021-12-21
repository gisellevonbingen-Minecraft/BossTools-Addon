package boss_tools_giselle_addon.client;

import boss_tools_giselle_addon.common.item.crafting.IS2ISRecipeCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerQuitWorld
{
	public static boolean shouldReset = false;

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			Minecraft minecraft = Minecraft.getInstance();
			ClientWorld world = minecraft.level;

			if (world != null)
			{
				shouldReset = true;
			}
			else if (shouldReset == true)
			{
				shouldReset = false;
				IS2ISRecipeCache.clearCaches();
			}

		}

	}

	private EventListenerQuitWorld()
	{

	}

}
