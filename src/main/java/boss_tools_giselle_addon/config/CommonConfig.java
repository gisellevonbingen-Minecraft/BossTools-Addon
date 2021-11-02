package boss_tools_giselle_addon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
	public final ConfigMachines machines;

	public final ConfigMekanism mekanism;

	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("if true, hide that machine(vehicle)'s recipes");

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
