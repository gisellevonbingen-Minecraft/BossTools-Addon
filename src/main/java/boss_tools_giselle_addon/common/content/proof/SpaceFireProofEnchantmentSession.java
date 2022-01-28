package boss_tools_giselle_addon.common.content.proof;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyStorage;
import net.minecraft.entity.LivingEntity;

public class SpaceFireProofEnchantmentSession extends ProofEnchantmentSession
{
	public SpaceFireProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorage enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public int getEnergyUsing()
	{
		return AddonConfigs.Common.enchantments.space_fire_proof_energyUsing.get();
	}

}
