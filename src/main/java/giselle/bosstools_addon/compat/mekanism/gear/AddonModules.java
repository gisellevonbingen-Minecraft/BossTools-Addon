package giselle.bosstools_addon.compat.mekanism.gear;

import giselle.bosstools_addon.compat.mekanism.api.text.StringLangEntry;
import giselle.bosstools_addon.compat.mekanism.gear.mekasuit.ModuleSpaceBreathingUnit;
import giselle.bosstools_addon.compat.mekanism.gear.mekasuit.ModuleSpaceFireProofUnit;
import giselle.bosstools_addon.compat.mekanism.gear.mekasuit.ModuleVenusAcidProofUnit;
import mekanism.common.content.gear.Modules.ModuleData;
import net.minecraft.item.Rarity;

public class AddonModules
{
	public static final ModuleData<ModuleSpaceBreathingUnit> SPACE_BREATHING_UNIT;
	public static final ModuleData<ModuleSpaceFireProofUnit> SPACE_FIRE_PROOF_UNIT;
	public static final ModuleData<ModuleVenusAcidProofUnit> VENUS_ACID_PROOF_UNIT;

	static
	{
		SPACE_BREATHING_UNIT = ModulesHelper.register("space_breathing_unit", new StringLangEntry("module", "space_breathing_unit"), new StringLangEntry("description", "space_breathing_unit"), ModuleSpaceBreathingUnit::new).rarity(Rarity.UNCOMMON).setRendersHUD();
		SPACE_FIRE_PROOF_UNIT = ModulesHelper.register("space_fire_proof_unit", new StringLangEntry("module", "space_fire_proof_unit"), new StringLangEntry("description", "space_fire_proof_unit"), ModuleSpaceFireProofUnit::new).rarity(Rarity.RARE);
		VENUS_ACID_PROOF_UNIT = ModulesHelper.register("venus_acid_proof_unit", new StringLangEntry("module", "venus_acid_proof_unit"), new StringLangEntry("description", "venus_acid_proof_unit"), ModuleVenusAcidProofUnit::new).rarity(Rarity.RARE);
	}

	public static void registerAll()
	{

	}

	private AddonModules()
	{

	}

}
