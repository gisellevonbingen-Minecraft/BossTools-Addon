package boss_tools_giselle_addon.common.capability;

import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public abstract class OxygenChargerWrapper implements IOxygenCharger
{
	private IChargeMode mode;

	public OxygenChargerWrapper()
	{
		this.mode = ChargeMode.NONE;
	}

	@Override
	public IChargeMode getChargeMode()
	{
		return this.mode;
	}

	@Override
	public void setChargeMode(IChargeMode mode)
	{
		if (this.getChargeMode() != mode)
		{
			this.mode = mode != null ? mode : ChargeMode.NONE;
			this.setChanged();
		}

	}

	@Override
	public void setChanged()
	{

	}

	@Override
	public abstract IOxygenStorage getOxygenStorage();

}
