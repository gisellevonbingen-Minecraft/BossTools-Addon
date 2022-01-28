package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import net.minecraft.world.entity.LivingEntity;

public class SpaceGravityProofEnchantmentSession extends ProofEnchantmentSession
{
	public SpaceGravityProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyOrDurability enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public int getEnergyUsing()
	{
		return AddonConfigs.Common.enchantments.gravity_normalizing_energyUsing.get();
	}

	@Override
	public int getDurabilityUsing()
	{
		return AddonConfigs.Common.enchantments.gravity_normalizing_durabilityUsing.get();
	}

	@Override
	public int getProofDuration()
	{
		return AddonConfigs.Common.enchantments.gravity_normalizing_proofDuration.get();
	}

}
