package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EnchantmentsConfig
{
	public final ConfigValue<Integer> space_breathing_energyUsing;
	public final ConfigValue<Integer> space_breathing_durabilityUsing;
	public final ConfigValue<Integer> space_breathing_proofDuration;

	public final ConfigValue<Integer> gravity_normalizing_energyUsing;
	public final ConfigValue<Integer> gravity_normalizing_durabilityUsing;
	public final ConfigValue<Integer> gravity_normalizing_proofDuration;

	public final ConfigValue<Integer> space_fire_proof_energyUsing;
	public final ConfigValue<Integer> space_fire_proof_durabilityUsing;
	public final ConfigValue<Integer> space_fire_proof_proofDuration;

	public final ConfigValue<Integer> venus_acid_proof_energyUsing;
	public final ConfigValue<Integer> venus_acid_proof_durabilityUsing;
	public final ConfigValue<Integer> venus_acid_proof_proofDuration;

	public EnchantmentsConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("space_breathing");
		this.space_breathing_energyUsing = builder.define("energyUsing", 10);
		this.space_breathing_durabilityUsing = builder.define("durabilityUsing", 1);
		this.space_breathing_proofDuration = builder.define("proofDuration", 20);
		builder.pop();

		builder.push("gravity_normalizing");
		this.gravity_normalizing_energyUsing = builder.define("energyUsing", 10);
		this.gravity_normalizing_durabilityUsing = builder.define("durabilityUsing", 1);
		this.gravity_normalizing_proofDuration = builder.define("proofDuration", 20);
		builder.pop();

		builder.push("space_fire_proof");
		this.space_fire_proof_energyUsing = builder.define("energyUsing", 10);
		this.space_fire_proof_durabilityUsing = builder.define("durabilityUsing", 1);
		this.space_fire_proof_proofDuration = builder.define("proofDuration", 20);
		builder.pop();

		builder.push("venus_acid_proof");
		this.venus_acid_proof_energyUsing = builder.define("energyUsing", 10);
		this.venus_acid_proof_durabilityUsing = builder.define("durabilityUsing", 1);
		this.venus_acid_proof_proofDuration = builder.define("proofDuration", 20);
		builder.pop();
	}

}
