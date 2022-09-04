package beyond_earth_giselle_addon.common.inventory;

import java.util.function.Supplier;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.guis.helper.ContainerHelper;
import net.mrscauthd.beyond_earth.guis.helper.IMergeItemStack;

public class ContainerHelper2
{
	public static ItemStack quickMoveStack(AbstractContainerMenu menu, Player player, int slotNumber, int containerIndex, Container inventory, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, containerIndex, inventory, mergeItemStack));
	}

	public static ItemStack quickMoveStack(AbstractContainerMenu menu, Player player, int slotNumber, int containerIndex, int containerSize, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, containerIndex, containerSize, mergeItemStack));
	}

	public static ItemStack quickMoveStack(AbstractContainerMenu menu, Player player, int slotNumber, Container inventory, IMergeItemStack mergeItemStack)
	{
		return quickMoveStack(menu, player, slotNumber, () -> ContainerHelper.transferStackInSlot(menu, player, slotNumber, inventory, mergeItemStack));
	}

	public static ItemStack quickMoveStack(AbstractContainerMenu menu, Player player, int slotNumber, Supplier<ItemStack> supplier)
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
