package boss_tools_giselle_addon.common.enchantment;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonEnchantments
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Enchantment.class, BossToolsAddon.MODID);
	public static final RegistryObject<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.register("space_breathing", EnchantmentSpaceBreathing::new);
	public static final RegistryObject<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.register("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final RegistryObject<EnchantmentVenusAcidProof> VENUS_ACID_PROOF = ENCHANTMENTS.register("venus_acid_proof", EnchantmentVenusAcidProof::new);

	private AddonEnchantments()
	{

	}

}
