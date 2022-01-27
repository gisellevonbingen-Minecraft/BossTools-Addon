package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
	public final ItemsConfig items;

	public final MachinesConfig machines;

	public final EnchantmentsConfig enchantments;

	public final MekanismConfig mekanism;

	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("items");
		this.items = new ItemsConfig(builder);
		builder.pop();

		builder.push("machines");
		this.machines = new MachinesConfig(builder);
		builder.pop();

		builder.push("enchantments");
		this.enchantments = new EnchantmentsConfig(builder);
		builder.pop();

		builder.push("compats");

		builder.push("mekanism");
		this.mekanism = new MekanismConfig(builder);
		builder.pop();

		builder.pop();
	}

}
