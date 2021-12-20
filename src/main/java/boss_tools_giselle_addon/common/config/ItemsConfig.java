package boss_tools_giselle_addon.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ItemsConfig
{
	public final ConfigValue<Integer> oxygenCan_OxygenCapacity;
	public final ConfigValue<Integer> oxygenCan_OxygenTransfer;

	public ItemsConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("oxygen_can");
		this.oxygenCan_OxygenCapacity = builder.define("oxygenCapacity", 24000);
		this.oxygenCan_OxygenTransfer = builder.define("oxygenTransfer", 256);
		builder.pop();
	}

}
