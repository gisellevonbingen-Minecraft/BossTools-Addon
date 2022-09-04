package boss_tools_giselle_addon.common.inventory.container;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.gui.helper.IMergeItemStack;

public class ContainerHelper2
{
	public static ItemStack quickMoveStack(Container menu, PlayerEntity player, int slotNumber, int containerIndex, IInventory inventory, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, containerIndex, inventory, mergeItemStack));
	}

	public static ItemStack quickMoveStack(Container menu, PlayerEntity player, int slotNumber, int containerIndex, int containerSize, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, containerIndex, containerSize, mergeItemStack));
	}

	public static ItemStack quickMoveStack(Container menu, PlayerEntity player, int slotNumber, IInventory inventory, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, inventory, mergeItemStack));
	}

	public static ItemStack quickMoveStack(Container menu, PlayerEntity player, int slotNumber, Supplier<ItemStack> supplier)
	{
		Slot slot = menu.slots.get(slotNumber);

		if (slot != null && slot.hasItem() == true)
		{
			ItemStack item = slot.getItem();
			ItemStack retVaue = supplier.get();

			if (retVaue.isEmpty() == false)
			{
				slot.onTake(player, item);
			}

			return retVaue;
		}
		else
		{
			return ItemStack.EMPTY;
		}

	}

}
