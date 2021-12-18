package beyond_earth_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundTag;

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

	@Override
	public CompoundTag serializeNBT()
	{
		return new CompoundTag();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{

	}

}
