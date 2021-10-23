package giselle.bosstools_addon.compat.mekanism.gear.mekasuit;

import giselle.bosstools_addon.config.AddonConfigs;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ModuleSpaceFireProofUnit extends ModuleMekaSuit implements IProofModule
{
	private FloatingLong usingEnergy;

	@Override
	public void init(ModuleData<?> data, ItemStack container)
	{
		super.init(data, container);

		this.usingEnergy = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceFireProof_usingEnergy.get());
	}

	@Override
	public boolean testDamage(DamageSource source)
	{
		return source != null && source.isFire();
	}

	@Override
	public boolean useProofResources(LivingEntity entity)
	{
		FloatingLong usingEnergy = this.getUsingEnergy();

		if (this.canUseEnergy(entity, usingEnergy) == true)
		{
			this.useEnergy(entity, usingEnergy);
			return true;
		}

		return false;
	}

	public FloatingLong getUsingEnergy()
	{
		return this.usingEnergy;
	}

}
