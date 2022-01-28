package boss_tools_giselle_addon.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnchantmentEnergyStorage extends Enchantment
{
	protected EnchantmentEnergyStorage(Rarity rarity, EnchantmentType type, EquipmentSlotType... slots)
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
			return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
		}

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
