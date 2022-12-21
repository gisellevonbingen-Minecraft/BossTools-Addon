package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class MekanismConfig
{
	public final ConfigValue<Boolean> modulesWorkFullParts;
	
	public final ConfigValue<Integer> moduleSpaceBreathing_oxygenDuration;
	public final ConfigValue<Integer> moduleSpaceBreathing_energyUsingProvide;
	public final ConfigValue<Integer> moduleSpaceBreathing_energyUsingProduce;

	public final ConfigValue<Integer> moduleSpaceFireProof_energyUsing;
	public final ConfigValue<Integer> moduleVenusAcidProof_energyUsing;
	public final ConfigValue<Integer> moduleGravityNormalizing_energyUsing;

	public final ConfigValue<Boolean> moduleGravitationalModulating_normalizable;
	public final ConfigValue<Integer> moduleGravitationalModulating_energyUsing;

	public MekanismConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("Modules to work, Player should be need equip all parts of Meka-Suit");
		this.modulesWorkFullParts = builder.define("modules_work_full_parts", false);
		
		builder.push("module_space_breathing_unit");
		builder.comment("Duration of provided oxygen (oxygen provide interval)");
		this.moduleSpaceBreathing_oxygenDuration = builder.define("oxygenDuration", 4);
		builder.comment("Energy usage when provide Space-BossTools oxygen to player in space");
		this.moduleSpaceBreathing_energyUsingProvide = builder.define("energyUsing", 10);
		builder.comment("Energy usage per mb when produce Mekanism oxygen to player in water, rain");
		this.moduleSpaceBreathing_energyUsingProduce = builder.define("energyUsingProduce", 200);
		builder.pop();

		builder.push("module_space_fire_proof_unit");
		builder.comment("Energy usage when prevent fire every tick in Space-BossTools hot planets (e.g. Venus, Mercury)");
		this.moduleSpaceFireProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_venus_acid_proof_unit");
		builder.comment("Energy usage when prevent acid rain damage every tick in Space-BossTools venus");
		this.moduleVenusAcidProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_space_gravity_normalizing_unit");
		builder.comment("Energy usage when normalizing gravity every tick in Space-BossTools dimensions");
		this.moduleGravityNormalizing_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_gravitational_modulating_unit");
		builder.comment("Can normalize gravity instead module_space_gravity_normalizing_unit");
		this.moduleGravitationalModulating_normalizable = builder.define("normalizable", true);
		builder.comment("Energy usage when normalizing gravity every tick in Space-BossTools dimensions");
		this.moduleGravitationalModulating_energyUsing = builder.define("energyUsing", 10);
		builder.pop();
	}

}
