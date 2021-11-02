package boss_tools_giselle_addon.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigMachines
{
	public final ConfigValue<Integer> fuelloader_capacity;
	public final ConfigValue<Integer> fuelloader_transfer;
	public final ConfigValue<Double> fuelloader_range;

	public ConfigMachines(ForgeConfigSpec.Builder builder)
	{
		builder.push("fuel_loader");
		
		this.fuelloader_capacity = builder.define("fluidCapacity", 8000);
		this.fuelloader_transfer = builder.define("fluidTransfer", 256);
		builder.comment("blocks from FuelLoader to each direction");
		this.fuelloader_range = builder.define("workingRange", 2.0D);
		
		builder.pop();
	}

}
