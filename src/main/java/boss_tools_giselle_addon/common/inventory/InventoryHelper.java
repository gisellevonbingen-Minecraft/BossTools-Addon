package boss_tools_giselle_addon.common.inventory;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class InventoryHelper
{
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
			IItemHandlerModifiable curiosItemHandler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);

			if (curiosItemHandler != null)
			{
				for (ItemStack stack : ItemHandlerHelper2.getStacks(curiosItemHandler))
				{
					if (stack.isEmpty() == false)
					{
						list.add(stack);
					}

				}

			}

		}

		return list;
	}

	private InventoryHelper()
	{

	}

}
