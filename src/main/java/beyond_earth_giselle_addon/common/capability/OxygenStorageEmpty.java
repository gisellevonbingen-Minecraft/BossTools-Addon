package beyond_earth_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.IOxygenStorage;

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
	public int getMaxCapacity()
	{
		return 0;
	}

	@Override
	public int getOxygen()
	{
		return 0;
	}

	@Override
	public int receiveOxygen(int maxReceive, boolean simulate)
	{
		return 0;
	}

	@Override
	public void setOxygen(int amount)
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
