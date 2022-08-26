package beyond_earth_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;

public abstract class OxygenChargerWrapper implements IOxygenCharger, INBTSerializable<CompoundTag>
{
	public static final String KEY_CHARGE_MODE = "chargemode";

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
		this.mode = IChargeMode.find(this.getAvailableChargeModes(), tag.getString(KEY_CHARGE_MODE));
	}

	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag compound = new CompoundTag();
		compound.put(KEY_CHARGE_MODE, IChargeMode.writeNBT(this.mode));

		return compound;
	}

	@Override
	public abstract IOxygenStorage getOxygenStorage();

}
