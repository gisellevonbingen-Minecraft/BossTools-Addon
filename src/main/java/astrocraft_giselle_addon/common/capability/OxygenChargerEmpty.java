package astrocraft_giselle_addon.common.capability;

import net.mrscauthd.astrocraft.capability.IOxygenStorage;

public class OxygenChargerEmpty extends ChargeModeHandlerEmpty implements IOxygenCharger
{
	public OxygenChargerEmpty()
	{

	}

	@Override
	public IOxygenStorage getOxygenStorage()
	{
		return new OxygenStorageEmpty();
	}

}
