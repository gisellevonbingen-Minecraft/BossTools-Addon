package boss_tools_giselle_addon.common.enchantment;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.ModInnet;

public class EnchantmentSpaceFireProof extends EnchantmentEnergyStorage
{
	protected EnchantmentSpaceFireProof()
	{
		super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, EquipmentSlotType.CHEST);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		Item item = stack.getItem();

		if (item == ModInnet.NETHERITE_SPACE_SUIT.get())
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
