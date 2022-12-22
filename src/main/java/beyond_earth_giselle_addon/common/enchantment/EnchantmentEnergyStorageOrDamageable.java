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
	public static boolean testEnchantLeastIron(ItemStack stack)
	{
		return !AddonConfigs.Common.enchantments.enchant_least_iron.get() || ArmorUtils.isLeastIron(stack);
	}

	public static boolean testWorkLeastIron(LivingEntity living)
	{
		return !AddonConfigs.Common.enchantments.work_full_parts_least_iron.get() || ArmorUtils.allEquipLeastIron(living);
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
			return ItemStackUtils.hasUseableResources(stack) && testEnchantLeastIron(stack);
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
