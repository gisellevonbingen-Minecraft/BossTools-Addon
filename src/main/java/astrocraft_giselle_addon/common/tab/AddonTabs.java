package astrocraft_giselle_addon.common.tab;

import astrocraft_giselle_addon.common.BossToolsAddon;
import astrocraft_giselle_addon.common.block.AddonBlocks;
import astrocraft_giselle_addon.common.item.AddonItems;
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
