package boss_tools_giselle_addon.common.tab;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.block.AddonBlocks;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import boss_tools_giselle_addon.common.item.AddonItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.RegistryObject;

public class AddonTabs
{
	public static final ItemGroup tab_main = new ItemGroup(BossToolsAddon.MODID + "_tab_main")
	{
		public void fillItemList(NonNullList<ItemStack> list)
		{
			super.fillItemList(list);

			for (RegistryObject<Enchantment> registryObject : AddonEnchantments.ENCHANTMENTS.getEntries())
			{
				Enchantment enchantment = registryObject.get();

				for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
				{
					ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentData(enchantment, i));
					list.add(enchantedBook);
				}

			}

		}

		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonItems.BLOCKS.get(AddonBlocks.FUEL_LOADER).get());
		}

	};

}
