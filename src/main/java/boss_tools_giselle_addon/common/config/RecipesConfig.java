package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class RecipesConfig
{
	public final ConfigValue<Boolean> recycling_enabled;

	public RecipesConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("recycling");

		this.recycling_enabled = builder.comment("Compressed and plate items can restore into be each ingot (a.k.a recycling)", "1. Minecraft - Smelting", "2. Minecraft - Blasting", "3. Immersive Enginering - Recycling").define("enabled", true);

		builder.pop();
	}

}
