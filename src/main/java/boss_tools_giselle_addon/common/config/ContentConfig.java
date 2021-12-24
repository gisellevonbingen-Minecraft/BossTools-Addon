package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ContentConfig
{
	public final ConfigValue<Boolean> alien_trade_removeDefaults;

	public ContentConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("alien_trade");

		this.alien_trade_removeDefaults = builder.define("removeDefaults", false);

		builder.pop();
	}

}
