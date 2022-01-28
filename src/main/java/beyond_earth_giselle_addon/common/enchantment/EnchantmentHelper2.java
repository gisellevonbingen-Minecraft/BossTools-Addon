package beyond_earth_giselle_addon.common.enchantment;

import javax.annotation.Nonnull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentHelper2
{
	@Nonnull
	public static Pair<ItemStack, Integer> getEnchantmentItemAndLevel(Enchantment enchantment, LivingEntity entity)
	{
		Iterable<ItemStack> iterable = enchantment.getSlotItems(entity).values();
		ItemStack stack = ItemStack.EMPTY;
		int level = 0;

		if (iterable != null)
		{
			for (ItemStack itemstack : iterable)
			{
				int i = EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack);

				if (i > level)
				{
					stack = itemstack;
					level = i;
				}

			}

		}

		return Pair.of(stack, level);
	}

	private EnchantmentHelper2()
	{

	}

}
