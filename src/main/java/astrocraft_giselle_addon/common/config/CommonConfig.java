package astrocraft_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
	public final ConfigMachines machines;

	public final ConfigMekanism mekanism;

	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("machines");
		this.machines = new ConfigMachines(builder);
		builder.pop();

		builder.push("compats");

		builder.push("mekanism");
		this.mekanism = new ConfigMekanism(builder);
		builder.pop();

		builder.pop();
	}

}
