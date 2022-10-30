package beyond_earth_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
	public final ItemsConfig items;

	public final MachinesConfig machines;

	public final EnchantmentsConfig enchantments;

	public final RecipesConfig recipes;

	public final MekanismConfig mekanism;
	public final PneumaticCraftConfig pneumaticcraft;

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

		builder.push("recipes");
		this.recipes = new RecipesConfig(builder);
		builder.pop();

		builder.push("compats");

		builder.push("mekanism");
		this.mekanism = new MekanismConfig(builder);
		builder.pop();

		builder.push("pneumaticcraft");
		this.pneumaticcraft = new PneumaticCraftConfig(builder);
		builder.pop();

		builder.pop();
	}

}
