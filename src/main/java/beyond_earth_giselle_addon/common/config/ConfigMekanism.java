package beyond_earth_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigMekanism
{
	public final ConfigValue<Integer> mekaSuitHelmet_OxygenCapacity;
	public final ConfigValue<Integer> mekaSuitHelmet_OxygenTransfer;

	public final ConfigValue<Integer> moduleSpaceBreathing_maxProduceRate;
	public final ConfigValue<Integer> moduleSpaceBreathing_oxygenUsing;
	public final ConfigValue<Integer> moduleSpaceBreathing_oxygenDuration;
	public final ConfigValue<Integer> moduleSpaceBreathing_energyUsing;

	public final ConfigValue<Integer> moduleSpaceFireProof_energyUsing;
	public final ConfigValue<Integer> moduleVenusAcidProof_energyUsing;
	public final ConfigValue<Integer> moduleSpaceGravityNormalizing_energyUsing;
	
	public final ConfigValue<Boolean> moduleGravitationalModulating_normalizable;
	public final ConfigValue<Integer> moduleGravitationalModulating_energyUsing;

	public ConfigMekanism(ForgeConfigSpec.Builder builder)
	{
		builder.push("mekasuit_helmet");
		this.mekaSuitHelmet_OxygenCapacity = builder.define("oxygenCapacity", 48000);
		this.mekaSuitHelmet_OxygenTransfer = builder.define("oxygenTransfer", 256);
		builder.pop();

		builder.push("module_space_breathing_unit");
		builder.comment("Fill amount of oxygen to helmet when player in water or rain", "in rain, produce efficiency is half");
		this.moduleSpaceBreathing_maxProduceRate = builder.define("maxProduceRate", 4);
		builder.comment("Oxygen gas usage when provide AstroCraft oxygen to player in space");
		this.moduleSpaceBreathing_oxygenUsing = builder.define("oxygenUsing", 1);
		builder.comment("Duration of provided oxygen (oxygen provide interval)");
		this.moduleSpaceBreathing_oxygenDuration = builder.define("oxygenDuration", 4);
		builder.comment("Energy usage when provide AstroCraft oxygen to player in space");
		this.moduleSpaceBreathing_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_space_fire_proof_unit");
		builder.comment("Energy usage when prevent fire every tick in AstroCraft hot planets (e.g. Venus, Mercury)");
		this.moduleSpaceFireProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_venus_acid_proof_unit");
		builder.comment("Energy usage when prevent acid rain damage every tick in AstroCraft venus");
		this.moduleVenusAcidProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_space_gravity_normalizing_unit");
		builder.comment("Energy usage when normalizing gravity every tick in AstroCraft dimensions");
		this.moduleSpaceGravityNormalizing_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_gravitational_modulating_unit");
		builder.comment("Can normalize gravity instead module_space_gravity_normalizing_unit");
		this.moduleGravitationalModulating_normalizable = builder.define("normalizable", true);
		builder.comment("Energy usage when normalizing gravity every tick in AstroCraft dimensions");
		this.moduleGravitationalModulating_energyUsing = builder.define("energyUsing", 10);
		builder.pop();
	}

}
