package giselle.boss_tools_addon.compat.mekanism.gear.mekasuit;

import giselle.boss_tools_addon.config.AddonConfigs;
import mekanism.api.math.FloatingLong;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ModuleVenusAcidProofUnit extends ModuleMekaSuit implements IProofModule
{
	public static final String PROOF_SOURCE_NAME = "venus.acid";

	private FloatingLong usingEnergy;

	@Override
	public void init(ModuleData<?> data, ItemStack container)
	{
		super.init(data, container);

		this.usingEnergy = FloatingLong.create(AddonConfigs.Common.mekanism.moduleVenusAcidProof_usingEnergy.get());
	}

	@Override
	public boolean testDamage(DamageSource source)
	{
		return source != null && PROOF_SOURCE_NAME.equals(source.getMsgId());
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
