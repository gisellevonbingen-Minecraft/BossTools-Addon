package giselle.bosstools_addon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig
{
	public final ForgeConfigSpec.BooleanValue hideBlastFurnaceRecipes;
	public final ForgeConfigSpec.BooleanValue hideCompressorRecipes;
	public final ForgeConfigSpec.BooleanValue hideFuelRefineryRecipes;
	public final ForgeConfigSpec.BooleanValue hideCoalGeneratorRecipes;
	public final ForgeConfigSpec.BooleanValue hideOxygenGeneratorRecipes;
	public final ForgeConfigSpec.BooleanValue hideOxygenLoaderRecipes;
	public final ForgeConfigSpec.BooleanValue hideNasaWorkbenchRecipes;
	public final ForgeConfigSpec.BooleanValue hideRoverRecipes;
	public final ForgeConfigSpec.BooleanValue hideRocketTier1Recipes;
	public final ForgeConfigSpec.BooleanValue hideRocketTier2Recipes;
	public final ForgeConfigSpec.BooleanValue hideRocketTier3Recipes;

	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("if true, hide that machine(vehicle)'s recipes");
		builder.push("recipes");

		this.hideBlastFurnaceRecipes = builder.define("hideBlastFurnaceRecipes", false);
		this.hideCompressorRecipes = builder.define("hideCompressorRecipes", false);
		this.hideFuelRefineryRecipes = builder.define("hideFuelRefineryRecipes", false);
		this.hideCoalGeneratorRecipes = builder.define("hideCoalGeneratorRecipes", false);
		this.hideOxygenGeneratorRecipes = builder.define("hideOxygenGeneratorRecipes", false);
		this.hideOxygenLoaderRecipes = builder.define("hideOxygenLoaderRecipes", false);
		this.hideNasaWorkbenchRecipes = builder.define("hideNasaWorkbenchRecipes", false);
		this.hideRoverRecipes = builder.define("hideRoverRecipes", false);
		this.hideRocketTier1Recipes = builder.define("hideRocketTier1Recipes", false);
		this.hideRocketTier2Recipes = builder.define("hideRocketTier2Recipes", false);
		this.hideRocketTier3Recipes = builder.define("hideRocketTier3Recipes", false);

		builder.pop();
	}

}
