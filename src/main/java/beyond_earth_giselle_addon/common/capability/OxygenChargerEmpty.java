package beyond_earth_giselle_addon.common.capability;

import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;

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
