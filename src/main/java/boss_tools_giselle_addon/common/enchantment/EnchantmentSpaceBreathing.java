package boss_tools_giselle_addon.common.enchantment;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.ModInnet;

public class EnchantmentSpaceBreathing extends EnchantmentEnergyStorage
{
	protected EnchantmentSpaceBreathing()
	{
		super(Rarity.UNCOMMON, EnchantmentType.ARMOR_HEAD, EquipmentSlotType.HEAD);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		Item item = stack.getItem();

		if (item == ModInnet.OXYGEN_MASK.get() || item == ModInnet.NETHERITE_OXYGEN_MASK.get())
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
