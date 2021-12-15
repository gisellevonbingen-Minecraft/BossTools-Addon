package boss_tools_giselle_addon.common.capability;

import net.mrscauthd.boss_tools.capability.IOxygenStorageHolder;
import net.mrscauthd.boss_tools.capability.OxygenStorage;

public class RatedOxygenStorage extends OxygenStorage
{
	private final int transfer;

	public RatedOxygenStorage(IOxygenStorageHolder holder, int capacity)
	{
		this(holder, capacity, 0);
	}

	public RatedOxygenStorage(IOxygenStorageHolder holder, int capacity, int oxygen)
	{
		this(holder, capacity, oxygen, Integer.MAX_VALUE);
	}

	public RatedOxygenStorage(IOxygenStorageHolder holder, int capacity, int oxygen, int transfer)
	{
		super(holder, capacity, oxygen);
		this.transfer = transfer;
	}

	@Override
	public int receiveOxygen(int maxReceive, boolean simulate)
	{
		maxReceive = this.clampTransfer(maxReceive);
		return super.receiveOxygen(maxReceive, simulate);
	}

	@Override
	public int extractOxygen(int maxExtract, boolean simulate)
	{
		maxExtract = this.clampTransfer(maxExtract);
		return super.extractOxygen(maxExtract, simulate);
	}

	public int clampTransfer(int amount)
	{
		return Math.min(amount, this.getTransfer());
	}

	public int getTransfer()
	{
		return this.transfer;
	}

}
