package beyond_earth_giselle_addon.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityChargeModeHandler
{
	public static Capability<IChargeModeHandler> CHARGE_MODE_HANDLER = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	public static void register(RegisterCapabilitiesEvent event)
	{
		event.register(IOxygenCharger.class);
	}

	private CapabilityChargeModeHandler()
	{

	}

}
