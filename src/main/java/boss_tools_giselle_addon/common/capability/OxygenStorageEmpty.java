package boss_tools_giselle_addon.common.capability;

import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public class OxygenStorageEmpty implements IOxygenStorage
{
	public OxygenStorageEmpty()
	{

	}

	@Override
	public int extractOxygen(int maxExtract, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getMaxOxygenStored()
	{
		return 0;
	}

	@Override
	public int getOxygenStored()
	{
		return 0;
	}

	@Override
	public int receiveOxygen(int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public void setOxygenStored(int amount)
	{

	}

}
