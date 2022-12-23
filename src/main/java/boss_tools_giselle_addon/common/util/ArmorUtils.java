package boss_tools_giselle_addon.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.EquipmentSlotType.Group;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class ArmorUtils
{
	public static boolean allEquip(LivingEntity living)
	{
		for (EquipmentSlotType slot : EquipmentSlotType.values())
		{
			if (slot.getType() == Group.ARMOR)
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
		for (EquipmentSlotType slot : EquipmentSlotType.values())
		{
			if (slot.getType() == Group.ARMOR)
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
		return getPrimaryDefence(stack) >= getPrimaryDefence(ArmorMaterial.IRON);
	}

	public static int getPrimaryDefence(ItemStack stack)
	{
		if (stack.getItem() instanceof ArmorItem)
		{
			ArmorItem armorItem = (ArmorItem) stack.getItem();
			return getPrimaryDefence(armorItem.getMaterial());
		}
		else
		{
			return 0;
		}

	}

	public static int getPrimaryDefence(IArmorMaterial material)
	{
		return material.getDefenseForSlot(EquipmentSlotType.CHEST);
	}

	private ArmorUtils()
	{

	}

}
