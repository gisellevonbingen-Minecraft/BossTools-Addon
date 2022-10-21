package boss_tools_giselle_addon.common.util;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.compat.curios.CuriosHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LivingEntityHelper
{
	public static boolean isPlayingMode(LivingEntity entity)
	{
		if (entity instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entity;
			return player.isCreative() == false && player.isSpectator() == false;
		}
		else
		{
			return true;
		}

	}

	public static List<ItemStack> getInventoryStacks(LivingEntity entity)
	{
		List<ItemStack> list = new ArrayList<>();

		if (entity instanceof PlayerEntity)
		{
			IInventory inventory = ((PlayerEntity) entity).inventory;
			int size = inventory.getContainerSize();

			for (int i = 0; i < size; i++)
			{
				ItemStack stack = inventory.getItem(i);

				if (stack.isEmpty() == false)
				{
					list.add(stack);
				}

			}

		}
		else
		{
			for (ItemStack stack : entity.getAllSlots())
			{
				if (stack.isEmpty() == false)
				{
					list.add(stack);
				}

			}

		}

		if (AddonCompatibleManager.CURIOS.isLoaded() == true)
		{
			for (ItemStack stack : CuriosHelper.getEquippedCurios(entity))
			{
				if (stack.isEmpty() == false)
				{
					list.add(stack);
				}

			}

		}

		return list;
	}

	private LivingEntityHelper()
	{

	}

}
