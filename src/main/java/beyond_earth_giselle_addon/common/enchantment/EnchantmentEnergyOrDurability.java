package beyond_earth_giselle_addon.common.enchantment;

import beyond_earth_giselle_addon.common.util.ItemEnergyDurabilityUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentEnergyOrDurability extends Enchantment
{
	protected EnchantmentEnergyOrDurability(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots)
	{
		super(rarity, category, slots);
	}

	@Override
	public boolean isTradeable()
	{
		return false;
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		if (stack.canApplyAtEnchantingTable(this) == false)
		{
			return false;
		}
		else if (ItemEnergyDurabilityUtils.extractEnergyOrDurability(stack, 0, 0, true, null, null) == false)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return false;
	}

	public int getMinCost(int level)
	{
		return 1;
	}

	public int getMaxCost(int level)
	{
		return this.getMinCost(level);
	}

	public int getMaxLevel()
	{
		return 1;
	}

}
