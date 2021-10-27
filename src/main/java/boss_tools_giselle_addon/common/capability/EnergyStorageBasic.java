package boss_tools_giselle_addon.common.capability;

import java.util.Optional;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageBasic implements IEnergyStorage, INBTSerializable<CompoundNBT>
{
	private final IEnergyStorageHolder holder;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;
	protected int energy;

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxReceive, int maxExtract, int energy)
	{
		this.holder = holder;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.energy = energy;
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxReceive, int maxExtract)
	{
		this(holder, capacity, maxReceive, maxExtract, 0);
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity, int maxTransfer)
	{
		this(holder, capacity, maxTransfer, maxTransfer);
	}

	public EnergyStorageBasic(IEnergyStorageHolder holder, int capacity)
	{
		this(holder, capacity, capacity);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		if (!this.canReceive())
		{
			return 0;
		}
		else
		{
			int energyReceived = Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), maxReceive);
			if (!simulate && energyReceived > 0)
			{
				this.energy += energyReceived;
				Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, +energyReceived));
			}

			return energyReceived;
		}
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if (!this.canExtract())
		{
			return 0;
		}
		else
		{
			int energyExtracted = Math.min(this.getEnergyStored(), maxExtract);
			if (!simulate && energyExtracted > 0)
			{
				this.energy -= energyExtracted;
				Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onEnergyChanged(this, -energyExtracted));
			}

			return energyExtracted;
		}
	}

	public int getMaxExtract()
	{
		return this.maxExtract;
	}

	@Override
	public boolean canExtract()
	{
		return this.getMaxExtract() > 0;
	}

	public int getMaxReceive()
	{
		return this.maxReceive;
	}

	@Override
	public boolean canReceive()
	{
		return this.getMaxReceive() > 0;
	}

	public IEnergyStorageHolder getHolder()
	{
		return this.holder;
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("energy", this.energy);
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT compound)
	{
		this.energy = compound.getInt("energy");
	}

	@Override
	public int getEnergyStored()
	{
		return this.energy;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return this.capacity;
	}

}
