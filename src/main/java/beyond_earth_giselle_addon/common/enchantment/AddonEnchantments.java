package beyond_earth_giselle_addon.common.enchantment;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AddonEnchantments
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Enchantment.class, BeyondEarthAddon.MODID);
	public static final RegistryObject<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.register("space_breathing", EnchantmentSpaceBreathing::new);
	public static final RegistryObject<EnchantmentGravityNormalizing> GRAVITY_NORMALIZING = ENCHANTMENTS.register("gravity_normalizing", EnchantmentGravityNormalizing::new);
	public static final RegistryObject<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.register("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final RegistryObject<EnchantmentVenusAcidProof> VENUS_ACID_PROOF = ENCHANTMENTS.register("venus_acid_proof", EnchantmentVenusAcidProof::new);

	private AddonEnchantments()
	{

	}

}
