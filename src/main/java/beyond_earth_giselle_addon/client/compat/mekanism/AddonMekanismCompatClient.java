package beyond_earth_giselle_addon.client.compat.mekanism;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class AddonMekanismCompatClient
{
	public AddonMekanismCompatClient()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(AddonMekanismClientEventListener.class);
	}

}
