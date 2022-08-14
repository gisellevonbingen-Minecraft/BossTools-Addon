package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import boss_tools_giselle_addon.common.util.ItemUsableResource;
import net.minecraft.entity.LivingEntity;

public class VenusAcidProofEnchantmentSession extends ProofEnchantmentSession
{
	public VenusAcidProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorageOrDamageable enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public int getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.venus_acid_proof_energy_using.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.venus_acid_proof_durability_using.get();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public int getProofDuration(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.venus_acid_proof_energy_duration.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.venus_acid_proof_durability_duration.get();
		}
		else
		{
			return 0;
		}

	}

}
