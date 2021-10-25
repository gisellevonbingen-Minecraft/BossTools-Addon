package giselle.boss_tools_addon.common.tile;

import java.util.List;
import java.util.function.Function;

import giselle.boss_tools_addon.common.inventory.container.IMoveItemStackTo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHelper
{
	public static void addInventorySlots(Container container, PlayerInventory inv, int left, int top, Function<Slot, Slot> addSlot)
	{
		addInventorySlots(container, inv, left, top, top + 58, addSlot);
	}

	public static void addInventorySlots(Container container, PlayerInventory inv, int left, int top, int hotbarY, Function<Slot, Slot> addSlot)
	{
		int rows = 3;
		int cols = 9;
		int offsetX = 18;
		int offsetY = 18;

		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				addSlot.apply(new Slot(inv, col + (row + 1) * cols, left + col * offsetX, top + row * offsetY));
			}
		}

		for (int col = 0; col < cols; col++)
		{
			addSlot.apply(new Slot(inv, col, left + col * offsetX, hotbarY));
		}

	}

	public static ItemStack quickMoveStack(Container container, PlayerEntity player, int slotNumber, int inventoryIndex, IInventory inventory, IMoveItemStackTo moveItemStackTo)
	{
		int containerSize = inventory.getContainerSize();
		return quickMoveStack(container, player, slotNumber, inventoryIndex, containerSize, moveItemStackTo);
	}

	public static ItemStack quickMoveStack(Container container, PlayerEntity player, int slotNumber, int inventoryIndex, int containerSize, IMoveItemStackTo moveItemStackTo)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = container.slots;
		Slot slot = inventorySlots.get(slotNumber);

		if (slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			itemStack = slotStack.copy();

			if (slotNumber < inventoryIndex + containerSize)
			{
				if (!moveItemStackTo.moveItemStackTo(slotStack, containerSize, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}

			}
			else if (!moveItemStackTo.moveItemStackTo(slotStack, 0, containerSize, false))
			{
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();
			}

		}

		return itemStack;
	}

	public static ItemStack quickMoveStack(Container container, PlayerEntity player, int slotNumber, IInventory inventory, IMoveItemStackTo moveItemStackTo)
	{
		return quickMoveStack(container, player, slotNumber, slotNumber, inventory, moveItemStackTo);
	}

}
