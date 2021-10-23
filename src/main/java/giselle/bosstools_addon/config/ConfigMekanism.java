package giselle.bosstools_addon.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigMekanism
{
	public final ConfigValue<Long> mekaSuitHelmet_OxygenCapacity;
	public final ConfigValue<Long> mekaSuitHelmet_OxygenTransfer;

	public final ConfigValue<Long> moduleSpaceBreathing_maxProduceRate;
	public final ConfigValue<Long> moduleSpaceBreathing_usingOxygen;
	public final ConfigValue<Long> moduleSpaceBreathing_usingEnergy;
	
	public final ConfigValue<Long> moduleSpaceFireProof_usingEnergy;
	public final ConfigValue<Long> moduleVenusAcidProof_usingEnergy;

	public ConfigMekanism(ForgeConfigSpec.Builder builder)
	{
		builder.push("mekasuit_helmet");
		this.mekaSuitHelmet_OxygenCapacity = builder.define("oxygenCapacity", 48000L);
		this.mekaSuitHelmet_OxygenTransfer = builder.define("oxygenTransfer", 256L);
		builder.pop();
		
		builder.push("module_space_breathing_unit");
		builder.comment("Fill amount of oxygen to helmet when player in water or rain", "in rain, produce efficiency is half");
		this.moduleSpaceBreathing_maxProduceRate = builder.define("maxProduceRate", 4L);
		builder.comment("Usage oxygen gas when provide Space-BossTools oxygen to player in space", "It will every 4 ticks");
		this.moduleSpaceBreathing_usingOxygen = builder.define("usingOxygen", 1L);
		builder.comment("Usage energy when provide Space-BossTools oxygen to player in space", "It will every 4 ticks");
		this.moduleSpaceBreathing_usingEnergy = builder.define("usingEnergy", 10L);
		builder.pop();

		builder.push("module_space_fire_proof_unit");
		builder.comment("Usage energy when prevent fire damage every tick in Space-BossTools dimensions");
		this.moduleSpaceFireProof_usingEnergy = builder.define("usingEnergy", 10L);
		builder.pop();

		builder.push("module_venus_acid_proof_unit");
		builder.comment("Usage energy when prevent acid rain damage every tick in Space-BossTools venus");
		this.moduleVenusAcidProof_usingEnergy = builder.define("usingEnergy", 10L);
		builder.pop();
	}

}
