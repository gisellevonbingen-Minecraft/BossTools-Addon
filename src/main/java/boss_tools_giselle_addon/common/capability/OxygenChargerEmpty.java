package boss_tools_giselle_addon.common.capability;

import net.mrscauthd.boss_tools.capability.IOxygenStorage;

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
