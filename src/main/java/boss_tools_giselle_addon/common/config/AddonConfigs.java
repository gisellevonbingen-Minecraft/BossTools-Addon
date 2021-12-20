package boss_tools_giselle_addon.common.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class AddonConfigs
{
	public static final ConfigCommon Common;
	public static final ForgeConfigSpec CommonSpec;

	static
	{
		Pair<ConfigCommon, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(ConfigCommon::new);
		Common = common.getLeft();
		CommonSpec = common.getRight();
	}

}
