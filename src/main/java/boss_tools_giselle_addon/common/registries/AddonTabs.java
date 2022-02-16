package boss_tools_giselle_addon.common.registries;

import boss_tools_giselle_addon.common.BossToolsAddon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AddonTabs
{
	public static final ItemGroup tab_main = new ItemGroup(BossToolsAddon.MODID + "_tab_main")
	{
		public void fillItemList(NonNullList<ItemStack> list)
		{
			super.fillItemList(list);

			for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getObjects())
			{
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
			return new ItemStack(AddonBlocks.FUEL_LOADER);
		}

	};

}
