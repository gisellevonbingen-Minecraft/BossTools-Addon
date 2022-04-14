package beyond_earth_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;

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
	public void deserializeNBT(CompoundTag tag)
	{
		CapabilityOxygenCharger.readNBT(this, tag);
	}

	@Override
	public CompoundTag serializeNBT()
	{
		return CapabilityOxygenCharger.writeNBT(this);
	}

	@Override
	public abstract IOxygenStorage getOxygenStorage();

}
