package beyond_earth_giselle_addon.common.compat.mekanism.gear;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;

public class ModuleSpaceFireProofUnit implements ICustomModule<ModuleSpaceFireProofUnit>
{
	private FloatingLong energyUsing;

	@Override
	public void init(IModule<ModuleSpaceFireProofUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.energyUsing = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceFireProof_energyUsing.get());
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

}
