package beyond_earth_giselle_addon.common;

import beyond_earth_giselle_addon.common.commands.AddonCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerCommand
{
	@SubscribeEvent
	public static void onServerStarting(RegisterCommandsEvent e)
	{
		e.getDispatcher().register(AddonCommand.builder());
	}

}
