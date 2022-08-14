package beyond_earth_giselle_addon.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.mrscauthd.beyond_earth.registries.ItemsRegistry;

public class EnchantmentSpaceBreathing extends EnchantmentEnergyStorageOrDamageable
{
	public EnchantmentSpaceBreathing()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		Item item = stack.getItem();

		if (item == ItemsRegistry.OXYGEN_MASK.get() || item == ItemsRegistry.NETHERITE_OXYGEN_MASK.get())
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
