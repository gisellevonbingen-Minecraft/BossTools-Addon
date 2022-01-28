package beyond_earth_giselle_addon.common.content.proof;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentEnergyStorage;
import net.minecraft.world.entity.LivingEntity;

public class VenusAcidProofEnchantmentSession extends ProofEnchantmentSession
{
	public VenusAcidProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorage enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public int getEnergyUsing()
	{
		return AddonConfigs.Common.enchantments.venus_acid_proof_energyUsing.get();
	}

}
