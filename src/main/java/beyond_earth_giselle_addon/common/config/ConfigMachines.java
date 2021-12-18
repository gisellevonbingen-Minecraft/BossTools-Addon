package beyond_earth_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigMachines
{
	public final ConfigValue<Integer> fuelLoader_capacity;
	public final ConfigValue<Integer> fuelLoader_transfer;
	public final ConfigValue<Integer> fuelLoader_range;

	public final ConfigValue<Integer> gravityNormalizer_energyUsingBase;
	public final ConfigValue<Integer> gravityNormalizer_energyUsingPerRange;

	public ConfigMachines(ForgeConfigSpec.Builder builder)
	{
		builder.push("fuel_loader");

		this.fuelLoader_capacity = builder.define("fluidCapacity", 8000);
		this.fuelLoader_transfer = builder.define("fluidTransfer", 256);
		builder.comment("blocks from FuelLoader to each direction");
		this.fuelLoader_range = builder.define("workingRange", 2);

		builder.pop();

		builder.push("gravity_normalizer");

		builder.comment("final energy usage = 'base' + range * 'perRange'");
		this.gravityNormalizer_energyUsingBase = builder.define("energyUsingBase", 0);
		this.gravityNormalizer_energyUsingPerRange = builder.define("energyUsingPerRange", 1);

		builder.pop();
	}

}
