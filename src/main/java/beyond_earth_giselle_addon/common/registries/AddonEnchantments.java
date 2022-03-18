package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentGravityNormalizing;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentSpaceBreathing;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentSpaceFireProof;
import beyond_earth_giselle_addon.common.enchantment.EnchantmentVenusAcidProof;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonEnchantments
{
	public static final DeferredRegisterWrapper<Enchantment> ENCHANTMENTS = DeferredRegisterWrapper.create(BeyondEarthAddon.MODID, ForgeRegistries.ENCHANTMENTS);
	public static final RegistryObject<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.register("space_breathing", EnchantmentSpaceBreathing::new);
	public static final RegistryObject<EnchantmentGravityNormalizing> GRAVITY_NORMALIZING = ENCHANTMENTS.register("gravity_normalizing", EnchantmentGravityNormalizing::new);
	public static final RegistryObject<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.register("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final RegistryObject<EnchantmentVenusAcidProof> VENUS_ACID_PROOF = ENCHANTMENTS.register("venus_acid_proof", EnchantmentVenusAcidProof::new);

	private AddonEnchantments()
	{

	}

}
