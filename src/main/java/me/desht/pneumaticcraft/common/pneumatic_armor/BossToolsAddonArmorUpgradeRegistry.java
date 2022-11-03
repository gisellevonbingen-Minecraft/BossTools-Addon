package me.desht.pneumaticcraft.common.pneumatic_armor;

import java.util.List;

import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;

public class BossToolsAddonArmorUpgradeRegistry
{
	public static void registerUpgradeHandler(List<IArmorUpgradeHandler<?>> handlers)
	{
		for (IArmorUpgradeHandler<?> handler : handlers)
		{
			registerUpgradeHandler(handler);
		}

	}

	public static void registerUpgradeHandler(IArmorUpgradeHandler<?> handler)
	{
		ArmorUpgradeRegistry registry = ArmorUpgradeRegistry.getInstance();
		registry.registerUpgradeHandler(handler);
	}

}
