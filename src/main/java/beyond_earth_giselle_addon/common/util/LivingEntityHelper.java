package beyond_earth_giselle_addon.common.util;

import java.util.ArrayList;
import java.util.List;

import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.compat.curios.CuriosHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LivingEntityHelper
{
	public static boolean isPlayingMode(LivingEntity entity)
	{
		if (entity instanceof Player player)
		{
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

		if (entity instanceof Player player)
		{
			Inventory inventory = player.getInventory();
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
