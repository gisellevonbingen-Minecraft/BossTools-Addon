package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import net.minecraft.entity.LivingEntity;

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
