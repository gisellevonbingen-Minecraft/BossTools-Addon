package boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit;

import boss_tools_giselle_addon.config.AddonConfigs;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import net.minecraft.item.ItemStack;

public class ModuleSpaceFireProofUnit extends ModuleMekaSuit
{
	private FloatingLong energyUsing;

	@Override
	public void init(ModuleData<?> data, ItemStack container)
	{
		super.init(data, container);

		this.energyUsing = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceFireProof_energyUsing.get());
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

}
