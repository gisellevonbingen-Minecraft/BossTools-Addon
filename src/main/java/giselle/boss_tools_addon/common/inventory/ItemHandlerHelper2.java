package giselle.boss_tools_addon.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerHelper2
{
	public static int indexOf(IItemHandler itemHandler, Item item)
	{
		int slots = itemHandler.getSlots();

		for (int i = 0; i < slots; i++)
		{
			ItemStack stackInSlot = itemHandler.getStackInSlot(i);

			if (stackInSlot.isEmpty() == false && stackInSlot.getItem() == item)
			{
				return i;
			}

		}

		return -1;
	}

	public static NonNullList<ItemStack> getStacks(IItemHandler handler)
	{
		NonNullList<ItemStack> list = NonNullList.withSize(handler.getSlots(), ItemStack.EMPTY);

		for (int i = 0; i < handler.getSlots(); i++)
		{
			ItemStack stack = handler.getStackInSlot(i);
			list.set(i, stack);
		}

		return list;
	}

	private ItemHandlerHelper2()
	{

	}

}
