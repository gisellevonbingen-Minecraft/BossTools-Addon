package boss_tools_giselle_addon.common.compat.mekanism;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleGravityNormalizingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.item.Rarity;

public class AddonMekanismModules
{
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(BossToolsAddon.MODID);
    
	public static final ModuleRegistryObject<ModuleSpaceBreathingUnit> SPACE_BREATHING_UNIT = MODULES.registerLegacy("space_breathing_unit", ModuleSpaceBreathingUnit::new, () -> AddonMekanismItems.SPACE_BREATHING_UNIT.get(), m -> m.rarity(Rarity.UNCOMMON).maxStackSize(4).rendersHUD());
	public static final ModuleRegistryObject<ModuleGravityNormalizingUnit> GRAVITY_NORMALIZING_UNIT = MODULES.registerLegacy("gravity_normalizing_unit", ModuleGravityNormalizingUnit::new, () -> AddonMekanismItems.GRAVITY_NORMALIZING_UNIT.get(), m -> m.rarity(Rarity.UNCOMMON));
	public static final ModuleRegistryObject<ModuleSpaceFireProofUnit> SPACE_FIRE_PROOF_UNIT = MODULES.registerLegacy("space_fire_proof_unit", ModuleSpaceFireProofUnit::new, () -> AddonMekanismItems.SPACE_FIRE_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));
	public static final ModuleRegistryObject<ModuleVenusAcidProofUnit> VENUS_ACID_PROOF_UNIT = MODULES.registerLegacy("venus_acid_proof_unit", ModuleVenusAcidProofUnit::new, () -> AddonMekanismItems.VENUS_ACID_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));

	private AddonMekanismModules()
	{

	}

}
