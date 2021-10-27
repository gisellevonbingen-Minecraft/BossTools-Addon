package boss_tools_giselle_addon.common.capability;

import net.minecraftforge.energy.IEnergyStorage;

@FunctionalInterface
public interface IEnergyStorageHolder
{

	void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta);

}
