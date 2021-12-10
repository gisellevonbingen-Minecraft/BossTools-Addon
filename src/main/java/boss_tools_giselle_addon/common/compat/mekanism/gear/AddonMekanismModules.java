package boss_tools_giselle_addon.common.compat.mekanism.gear;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import boss_tools_giselle_addon.common.compat.mekanism.api.text.StringLangEntry;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleGravityNormalizingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleSpaceBreathingUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleSpaceFireProofUnit;
import boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit.ModuleVenusAcidProofUnit;
import mekanism.common.content.gear.Modules.ModuleData;
import net.minecraft.item.Rarity;

public class AddonMekanismModules
{
	private static final List<ModuleData<?>> MODULES = new ArrayList<>();
	public static final ModuleData<ModuleSpaceBreathingUnit> SPACE_BREATHING_UNIT;
	public static final ModuleData<ModuleGravityNormalizingUnit> GRAVITY_NORMALIZING_UNIT;
	public static final ModuleData<ModuleSpaceFireProofUnit> SPACE_FIRE_PROOF_UNIT;
	public static final ModuleData<ModuleVenusAcidProofUnit> VENUS_ACID_PROOF_UNIT;

	static
	{
		SPACE_BREATHING_UNIT = register("space_breathing_unit", ModuleSpaceBreathingUnit::new).rarity(Rarity.UNCOMMON).setRendersHUD();
		GRAVITY_NORMALIZING_UNIT = register("gravity_normalizing_unit", ModuleGravityNormalizingUnit::new).rarity(Rarity.UNCOMMON);
		SPACE_FIRE_PROOF_UNIT = register("space_fire_proof_unit", ModuleSpaceFireProofUnit::new).rarity(Rarity.RARE);
		VENUS_ACID_PROOF_UNIT = register("venus_acid_proof_unit", ModuleVenusAcidProofUnit::new).rarity(Rarity.RARE);
	}

	public static List<ModuleData<?>> getModules()
	{
		return new ArrayList<>(MODULES);
	}

	public static <M extends mekanism.common.content.gear.Module> ModuleData<M> register(String name, Supplier<M> moduleSupplier)
	{
		return register(name, moduleSupplier, 1);
	}

	public static <M extends mekanism.common.content.gear.Module> ModuleData<M> register(String name, Supplier<M> moduleSupplier, int maxStackSize)
	{
		ModuleData<M> module = ModulesHelper.register(name, new StringLangEntry("module", name), new StringLangEntry("description", name), moduleSupplier, maxStackSize);
		MODULES.add(module);
		return module;
	}

	private AddonMekanismModules()
	{

	}

}
