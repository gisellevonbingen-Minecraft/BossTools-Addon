package boss_tools_giselle_addon.common.tab;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.item.AddonItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AddonTabs
{
	public static final ItemGroup tab_main = new ItemGroup(BossToolsAddon.MODID + "_tab_main")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonItems.BLOCKS.get(AddonBlocks.FUEL_LOADER).get());
		}

	};

}
