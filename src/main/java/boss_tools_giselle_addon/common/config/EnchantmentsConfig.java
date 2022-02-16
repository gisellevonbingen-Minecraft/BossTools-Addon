package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EnchantmentsConfig
{
	public final ConfigValue<Boolean> tooltip_Enabled;
	public final ConfigValue<Boolean> tooltip_Ignore;

	public final ConfigValue<Integer> space_breathing_energyUsing;
	public final ConfigValue<Integer> space_breathing_oxygenDuration;

	public final ConfigValue<Integer> gravity_normalizing_energyUsing;

	public final ConfigValue<Integer> space_fire_proof_energyUsing;

	public final ConfigValue<Integer> venus_acid_proof_energyUsing;

	public EnchantmentsConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("common");

		builder.push("tooltip");

		builder.comment("show tooltip on THIS mod's enchanted book");
		this.tooltip_Enabled = builder.define("enabled", true);
		builder.comment("tooltip will don't show when 'Enchantment Descriptions' or 'CoFH Core' installed", //
				"but, if this set 'true' show tooltip with ignore that mods");
		this.tooltip_Ignore = builder.define("ignore", false);

		builder.pop();

		builder.pop();

		builder.push("space_breathing");
		this.space_breathing_energyUsing = builder.define("energyUsing", 10);
		this.space_breathing_oxygenDuration = builder.define("oxygenDuration", 4);
		builder.pop();

		builder.push("gravity_normalizing");
		this.gravity_normalizing_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("space_fire_proof");
		this.space_fire_proof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("venus_acid_proof");
		this.venus_acid_proof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();
	}

}
