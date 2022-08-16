package beyond_earth_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.mrscauthd.beyond_earth.machines.tile.AbstractMachineBlockEntity;

public class MachinesConfig
{
	public final ConfigValue<Integer> fuelLoader_capacity;
	public final ConfigValue<Integer> fuelLoader_transfer;
	public final ConfigValue<Integer> fuelLoader_range;

	public final ConfigValue<Integer> electricBlastFurnace_energyCapcity;
	public final ConfigValue<Integer> electricBlastFurnace_energyTransfer;
	public final ConfigValue<Integer> electricBlastFurnace_energyUsing;

	public final ConfigValue<Integer> advancedCompressor_energyCapcity;
	public final ConfigValue<Integer> advancedCompressor_energyTransfer;
	public final ConfigValue<Integer> advancedCompressor_energyUsing;

	public final ConfigValue<Integer> gravityNormalizer_energyCapcity;
	public final ConfigValue<Integer> gravityNormalizer_energyTransfer;
	public final ConfigValue<Integer> gravityNormalizer_energyUsingBase;
	public final ConfigValue<Integer> gravityNormalizer_energyUsingPerRange;

	public MachinesConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("fuel_loader");

		this.fuelLoader_capacity = builder.define("fluidCapacity", 8000);
		this.fuelLoader_transfer = builder.define("fluidTransfer", 256);
		builder.comment("blocks from FuelLoader to each direction");
		this.fuelLoader_range = builder.define("workingRange", 2);

		builder.pop();

		builder.push("electric_blasting_furnace");

		this.electricBlastFurnace_energyCapcity = builder.define("energyCapacity", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_CAPACITY);
		this.electricBlastFurnace_energyTransfer = builder.define("energyTransfer", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_TRANSFER);
		this.electricBlastFurnace_energyUsing = builder.define("energyUsing", 1);

		builder.pop();

		builder.push("advanced_compressor");

		this.advancedCompressor_energyCapcity = builder.define("energyCapacity", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_CAPACITY);
		this.advancedCompressor_energyTransfer = builder.define("energyTransfer", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_TRANSFER);
		this.advancedCompressor_energyUsing = builder.define("energyUsing", 1);

		builder.pop();

		builder.push("gravity_normalizer");

		this.gravityNormalizer_energyCapcity = builder.define("energyCapacity", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_CAPACITY);
		this.gravityNormalizer_energyTransfer = builder.define("energyTransfer", AbstractMachineBlockEntity.DEFAULT_ENERGY_STORAGE_TRANSFER);
		builder.comment("final energy usage = 'base' + range * 'perRange'");
		this.gravityNormalizer_energyUsingBase = builder.define("energyUsingBase", 0);
		this.gravityNormalizer_energyUsingPerRange = builder.define("energyUsingPerRange", 1);

		builder.pop();
	}

}
