package beyond_earth_giselle_addon.common.enchantment;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.util.ArmorUtils;
import beyond_earth_giselle_addon.common.util.ItemStackUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentEnergyStorageOrDamageable extends Enchantment
{
	public static boolean testWorkLeastIron(ItemStack stack)
	{
		return !AddonConfigs.Common.enchantments.work_least_iron.get() || ArmorUtils.isLeastIron(stack);
	}

	public static boolean testWorkFullParts(LivingEntity living)
	{
		if (!AddonConfigs.Common.enchantments.work_full_parts.get())
		{
			return true;
		}
		else if (AddonConfigs.Common.enchantments.work_least_iron.get())
		{
			return ArmorUtils.allEquipLeastIron(living);
		}
		else
		{
			return ArmorUtils.allEquip(living);
		}

	}

	public EnchantmentEnergyStorageOrDamageable(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots)
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
		else
		{
			return ItemStackUtils.hasUseableResources(stack) && testWorkLeastIron(stack);
		}

	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return false;
	}

	@Override
	public int getMinCost(int level)
	{
		return 1;
	}

	@Override
	public int getMaxCost(int level)
	{
		return this.getMinCost(level);
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

}
