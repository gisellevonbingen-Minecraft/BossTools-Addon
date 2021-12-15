package boss_tools_giselle_addon.common.tab;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.item.AddonItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AddonTabs
{
	public static final CreativeModeTab tab_main = new CreativeModeTab(BossToolsAddon.MODID + "_tab_main")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonItems.BLOCKS.get(AddonBlocks.FUEL_LOADER).get());
		}

	};

}
