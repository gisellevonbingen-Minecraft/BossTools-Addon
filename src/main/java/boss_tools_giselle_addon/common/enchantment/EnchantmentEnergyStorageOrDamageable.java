package boss_tools_giselle_addon.common.enchantment;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.util.ArmorUtils;
import boss_tools_giselle_addon.common.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

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

	public EnchantmentEnergyStorageOrDamageable(Rarity rarity, EnchantmentType type, EquipmentSlotType... slots)
	{
		super(rarity, type, slots);
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
