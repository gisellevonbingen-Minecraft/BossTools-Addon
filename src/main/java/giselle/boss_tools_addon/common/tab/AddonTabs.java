package giselle.boss_tools_addon.common.tab;

import giselle.boss_tools_addon.BossToolsAddon;
import giselle.boss_tools_addon.common.item.AddonItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AddonTabs
{
	public static final ItemGroup tab_main = new ItemGroup(BossToolsAddon.MODID + "_tab_main")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonItems.OXYGEN_ACCEPTER.get());
		}

	};

}
