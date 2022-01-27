package boss_tools_giselle_addon.common;

import boss_tools_giselle_addon.common.command.AddonCommand;
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
