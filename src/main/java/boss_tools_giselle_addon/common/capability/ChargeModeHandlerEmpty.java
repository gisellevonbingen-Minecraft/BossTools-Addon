package boss_tools_giselle_addon.common.capability;

public class ChargeModeHandlerEmpty implements IChargeModeHandler
{
	public ChargeModeHandlerEmpty()
	{

	}

	@Override
	public void setChargeMode(IChargeMode mode)
	{

	}

	@Override
	public IChargeMode getChargeMode()
	{
		return ChargeMode.NONE;
	}

	@Override
	public void setChanged()
	{

	}

}
