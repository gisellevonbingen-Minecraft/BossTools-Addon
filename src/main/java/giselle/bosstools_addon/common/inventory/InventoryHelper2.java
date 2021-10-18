package giselle.bosstools_addon.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class InventoryHelper2
{
	public static void load(IInventory inventory, CompoundNBT compound)
	{
		inventory.clearContent();
		int size = inventory.getContainerSize();

		for (int i = 0; i < size; i++)
		{
			CompoundNBT slotCompound = compound.getCompound("Slot." + i);
			inventory.setItem(i, ItemStack.of(slotCompound));
		}

	}

	public static CompoundNBT save(IInventory inventory)
	{
		CompoundNBT compound = new CompoundNBT();
		int size = inventory.getContainerSize();

		for (int i = 0; i < size; i++)
		{
			CompoundNBT slotCompound = inventory.getItem(i).serializeNBT();
			compound.put("Slot." + i, slotCompound);
		}

		return compound;
	}

}
