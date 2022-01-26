package boss_tools_giselle_addon.common.content.proof.fire;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.content.proof.SpaceProofEnchantmentSession;
import boss_tools_giselle_addon.common.enchantment.EnchantmentEnergyOrDurability;
import net.minecraft.entity.LivingEntity;

public class SpaceFireProofEnchantmentSession extends SpaceProofEnchantmentSession
{
	public SpaceFireProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyOrDurability enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public int getEnergyUsing()
	{
		return AddonConfigs.Common.enchantments.space_fire_proof_energyUsing.get();
	}

	@Override
	public int getDurabilityUsing()
	{
		return AddonConfigs.Common.enchantments.space_fire_proof_durabilityUsing.get();
	}

	@Override
	public int getProofDuration()
	{
		return AddonConfigs.Common.enchantments.space_fire_proof_proofDuration.get();
	}

}
