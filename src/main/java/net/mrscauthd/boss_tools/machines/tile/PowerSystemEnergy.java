package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.BossToolsMod;

public abstract class PowerSystemEnergy extends PowerSystem {
	private final IEnergyStorage energyStorage;

	public PowerSystemEnergy(AbstractMachineBlockEntity blockEntity) {
		this(blockEntity, blockEntity.getPrimaryEnergyStorage());
	}

	public PowerSystemEnergy(AbstractMachineBlockEntity blockEntity, IEnergyStorage energyStorage) {
		super(blockEntity);
		this.energyStorage = energyStorage;
	}

	@Override
	public int getStored() {
		return this.getEnergyStorage().getEnergyStored();
	}

	@Override
	public int getCapacity() {
		return this.getEnergyStorage().getMaxEnergyStored();
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return this.getEnergyStorage().receiveEnergy(amount, simulate);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return this.getEnergyStorage().extractEnergy(amount, simulate);
	}

	public final IEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}

	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(BossToolsMod.ModId, "energy");
	}
}
