package beyond_earth_giselle_addon.common.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

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

	public static NonNullList<ItemStack> take(IItemHandlerModifiable handler, int amount, boolean simulate)
	{
		return take(handler, amount, 0, handler.getSlots(), simulate);
	}

	public static NonNullList<ItemStack> take(IItemHandlerModifiable handler, int amount, int startIndex, int endIndex, boolean simulate)
	{
		int length = endIndex - startIndex;
		NonNullList<ItemStack> list = NonNullList.withSize(length, ItemStack.EMPTY);

		for (int i = 0; i < length; i++)
		{
			ItemStack item = handler.extractItem(startIndex + i, amount, simulate);
			list.set(i, item);
		}

		return list;
	}

	private ItemHandlerHelper2()
	{

	}

}
