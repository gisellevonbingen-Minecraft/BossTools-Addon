package giselle.bosstools_addon.common.item;

import giselle.bosstools_addon.BossToolsAddon;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AddonTabs
{
	public static final ItemGroup tab_main = new ItemGroup(BossToolsAddon.MODID + "_tab_main")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonItems.SPACE_BREATH_GEAR.get());
		}

	};

}
