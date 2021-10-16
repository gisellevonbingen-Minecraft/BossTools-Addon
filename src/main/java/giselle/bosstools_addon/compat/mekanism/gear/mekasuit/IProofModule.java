package giselle.bosstools_addon.compat.mekanism.gear.mekasuit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public interface IProofModule
{
	public boolean testDamage(DamageSource source);

	public boolean useProofResources(LivingEntity entity);
}
