package beyond_earth_giselle_addon.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.mrscauthd.beyond_earth.registries.ItemsRegistry;

public class EnchantmentSpaceFireProof extends EnchantmentEnergyStorageOrDamageable
{
	public EnchantmentSpaceFireProof()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		Item item = stack.getItem();

		if (item == ItemsRegistry.NETHERITE_SPACE_SUIT.get())
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
