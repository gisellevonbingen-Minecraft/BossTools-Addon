package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class PneumaticCraftConfig
{
	public final ConfigValue<Boolean> upgradesWorkFullParts;
	
	public final ConfigValue<Integer> upgrade_space_breating_oxygenDuration;
	public final ConfigValue<Integer> upgrade_space_breating_airUsing;

	public final ConfigValue<Integer> upgrade_space_fire_proof_airUsing;
	public final ConfigValue<Integer> upgrade_venus_acid_proof_airUsing;
	public final ConfigValue<Integer> upgrade_gravity_normalizing_airUsing;

	public PneumaticCraftConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("Upgrades to work, Player should be need equip all parts of Pneumatic-Armor");
		this.upgradesWorkFullParts = builder.define("upgrades_work_full_parts", false);
		
		builder.push("space_breating_upgrade");
		builder.comment("Duration of provided oxygen (oxygen provide interval)");
		this.upgrade_space_breating_oxygenDuration = builder.define("oxygenDuration", 4);
		builder.comment("Air usage when provide Space-BossTools oxygen to player in space");
		this.upgrade_space_breating_airUsing = builder.define("airUsing", 20);
		builder.pop();

		builder.push("space_fire_proof_upgrade");
		builder.comment("Air usage when prevent fire every tick in Space-BossTools hot planets (e.g. Venus, Mercury)");
		this.upgrade_space_fire_proof_airUsing = builder.define("airUsing", 5);
		builder.pop();

		builder.push("venus_acid_proof_upgrade");
		builder.comment("Aire usage when prevent acid rain damage every tick in Space-BossTools venus");
		this.upgrade_venus_acid_proof_airUsing = builder.define("airUsing", 5);
		builder.pop();

		builder.push("gravity_normalizing_upgrade");
		builder.comment("Air usage when normalizing gravity every tick in Space-BossTools dimensions");
		this.upgrade_gravity_normalizing_airUsing = builder.define("airUsing", 5);
		builder.pop();
	}

}
