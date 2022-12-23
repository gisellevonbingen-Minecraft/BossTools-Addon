package beyond_earth_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EnchantmentsConfig
{
	public final ConfigValue<Boolean> tooltip_Enabled;
	public final ConfigValue<Boolean> tooltip_Ignore;

	public final ConfigValue<Boolean> work_least_iron;
	public final ConfigValue<Boolean> work_full_parts;

	public final ConfigValue<Integer> space_breathing_energy_using;
	public final ConfigValue<Integer> space_breathing_energy_duration;
	public final ConfigValue<Integer> space_breathing_energy_oxygen;
	public final ConfigValue<Integer> space_breathing_durabiltiy_using;
	public final ConfigValue<Integer> space_breathing_durability_duration;
	public final ConfigValue<Integer> space_breathing_durability_oxygen;

	public final ConfigValue<Integer> gravity_normalizing_energy_using;
	public final ConfigValue<Integer> gravity_normalizing_energy_duration;
	public final ConfigValue<Integer> gravity_normalizing_durability_using;
	public final ConfigValue<Integer> gravity_normalizing_durability_duration;

	public final ConfigValue<Integer> space_fire_proof_energy_using;
	public final ConfigValue<Integer> space_fire_proof_energy_duration;
	public final ConfigValue<Integer> space_fire_proof_durability_using;
	public final ConfigValue<Integer> space_fire_proof_durability_duration;

	public final ConfigValue<Integer> venus_acid_proof_energy_using;
	public final ConfigValue<Integer> venus_acid_proof_energy_duration;
	public final ConfigValue<Integer> venus_acid_proof_durability_using;
	public final ConfigValue<Integer> venus_acid_proof_durability_duration;

	public EnchantmentsConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("common");

		builder.push("tooltip");

		builder.comment("Show tooltip on THIS mod's enchanted book");
		this.tooltip_Enabled = builder.define("enabled", true);
		builder.comment("Tooltip will don't show when 'Enchantment Descriptions' or 'CoFH Core' installed", //
				"But, if this set 'true' show tooltip with ignore that mods");
		this.tooltip_Ignore = builder.define("ignore", false);

		builder.pop();

		builder.comment("To enchantment applying and working, Item should be have defence points equals or greater than Iron Armors");
		this.work_least_iron = builder.define("work_least_iron", false);
		builder.comment("To enchantment working, Player should be need equip all parts of armors", "If 'work_least_iron' is enabled, that armors should be least iron armors");
		this.work_full_parts = builder.define("work_full_parts", false);

		builder.pop();

		builder.push("space_breathing");
		this.space_breathing_energy_using = builder.comment("Energy usage for space breathing").define("energyUsing", 10);
		this.space_breathing_energy_duration = builder.comment("Space breathing duration using energy").define("oxygenDuration", 4);
		this.space_breathing_energy_oxygen = builder.comment("Oxygen usage using energy").define("energyOxygen", 1);
		this.space_breathing_durabiltiy_using = builder.comment("Durability usage for space breathing").define("durabilityUsing", 1);
		this.space_breathing_durability_duration = builder.comment("Space breathing duration using durability").define("oxygenDurationUsingDurability", 60);
		this.space_breathing_durability_oxygen = builder.comment("Oxygen usage using durability").define("durabilityOxygen", 15);
		builder.pop();

		builder.push("gravity_normalizing");
		this.gravity_normalizing_energy_using = builder.comment("Energy usage for gravity normalizing").define("energyUsing", 10);
		this.gravity_normalizing_energy_duration = builder.comment("Gravity Normalizing duration using energy").define("energyDuration", 1);
		this.gravity_normalizing_durability_using = builder.comment("Durability usage for gravity normalizing").define("durabilityUsing", 1);
		this.gravity_normalizing_durability_duration = builder.comment("Gravity Normalizing duration using durability").define("durabilityDuration", 60);
		builder.pop();

		builder.push("space_fire_proof");
		this.space_fire_proof_energy_using = builder.comment("Energy usage for space fire proof").define("energyUsing", 10);
		this.space_fire_proof_energy_duration = builder.comment("Space fire proof duration using energy").define("energyDuration", 1);
		this.space_fire_proof_durability_using = builder.comment("Durability usage for space fire proof").define("durabilityUsing", 1);
		this.space_fire_proof_durability_duration = builder.comment("Space fire proof duration using durability").define("durabilityDuration", 60);
		builder.pop();

		builder.push("venus_acid_proof");
		this.venus_acid_proof_energy_using = builder.comment("Energy usage for venus acid proof").define("energyUsing", 10);
		this.venus_acid_proof_energy_duration = builder.comment("Venus acid proof duration using energy").define("energyDuration", 1);
		this.venus_acid_proof_durability_using = builder.comment("Durability usage for venus acid proof").define("durabilityUsing", 1);
		this.venus_acid_proof_durability_duration = builder.comment("Venus acid proof duration using durability").define("durabilityDuration", 60);
		builder.pop();
	}

}
