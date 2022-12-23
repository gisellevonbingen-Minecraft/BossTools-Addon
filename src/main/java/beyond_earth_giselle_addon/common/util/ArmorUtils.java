package beyond_earth_giselle_addon.common.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class ArmorUtils
{
	public static boolean allEquip(LivingEntity living)
	{
		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			if (slot.getType() == EquipmentSlot.Type.ARMOR)
			{
				ItemStack stack = living.getItemBySlot(slot);

				if (stack.isEmpty())
				{
					return false;
				}

			}

		}

		return true;
	}

	public static boolean allEquipLeastIron(LivingEntity living)
	{
		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			if (slot.getType() == EquipmentSlot.Type.ARMOR)
			{
				ItemStack stack = living.getItemBySlot(slot);

				if (isLeastIron(stack) == false)
				{
					return false;
				}

			}

		}

		return true;
	}

	public static boolean isLeastIron(ItemStack stack)
	{
		return getPrimaryDefence(stack) >= getPrimaryDefence(ArmorMaterials.IRON);
	}

	public static int getPrimaryDefence(ItemStack stack)
	{
		if (stack.getItem() instanceof ArmorItem armorItem)
		{
			return getPrimaryDefence(armorItem.getMaterial());
		}
		else
		{
			return 0;
		}

	}

	public static int getPrimaryDefence(ArmorMaterial material)
	{
		return material.getDefenseForSlot(EquipmentSlot.CHEST);
	}

	private ArmorUtils()
	{

	}

}
