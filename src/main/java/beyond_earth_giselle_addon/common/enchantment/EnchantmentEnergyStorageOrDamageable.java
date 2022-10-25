package beyond_earth_giselle_addon.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnchantmentEnergyStorageOrDamageable extends Enchantment
{
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
			return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
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
