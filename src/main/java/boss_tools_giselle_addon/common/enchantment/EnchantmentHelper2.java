package boss_tools_giselle_addon.common.enchantment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class EnchantmentHelper2
{
	private static final Map<Enchantment, String> DESCRIPTION_KEYS = new HashMap<>();
	private static final Map<Enchantment, ITextComponent> DESCRIPTION_TEXTS = new HashMap<>();
	private static final List<String> DESCRIPTION_SUFFIXES = Lists.newArrayList("desc", "description");

	public static List<String> getDescriptionSuffixes()
	{
		return Collections.unmodifiableList(DESCRIPTION_SUFFIXES);
	}

	public static boolean addDescriptionSuffix(String suffix)
	{
		return DESCRIPTION_SUFFIXES.add(suffix);
	}

	public static String getDescriptionKey(Enchantment enchantment)
	{
		return DESCRIPTION_KEYS.computeIfAbsent(enchantment, e ->
		{
			String descriptionId = e.getDescriptionId();

			for (String suffix : DESCRIPTION_SUFFIXES)
			{
				String key = descriptionId + "." + suffix;

				if (I18n.exists(key) == true)
				{
					return key;
				}

			}

			return descriptionId + DESCRIPTION_SUFFIXES.get(0);
		});

	}

	public static ITextComponent getDescriptionText(Enchantment enchantment)
	{
		return DESCRIPTION_TEXTS.computeIfAbsent(enchantment, e ->
		{
			return new TranslationTextComponent(getDescriptionKey(enchantment)).withStyle(TextFormatting.GOLD);
		});
	}

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
