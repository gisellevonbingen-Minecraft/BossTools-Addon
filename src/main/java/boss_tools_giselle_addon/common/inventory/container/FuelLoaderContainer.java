package boss_tools_giselle_addon.common.inventory.container;

import java.util.List;

import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.ContainerHelper;

public class FuelLoaderContainer extends Container
{
	private FuelLoaderTileEntity tileEntity;

	public FuelLoaderContainer(int windowId, PlayerInventory inv, FuelLoaderTileEntity tileEntity)
	{
		super(AddonContainers.FUEL_LOADER.get(), windowId);
		this.tileEntity = tileEntity;

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();

		for (int i = tileEntity.getSlotFluidStart(); i < tileEntity.getSlotFluidEnd(); i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, i, 144, 28 + 30 * i));
		}

		int slotInputStart = tileEntity.getSlotInputStart();
		int slotsInput = tileEntity.getSlotsInput();

		for (int i = 0; i < slotsInput; i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, slotInputStart + i, 8 + 18 * i, 20));
		}

		int slotOutputStart = tileEntity.getSlotOutputStart();
		int slotsOutput = tileEntity.getSlotsOutput();

		for (int i = 0; i < slotsOutput; i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, slotOutputStart + i, 8 + 18 * i, 66)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return false;
				}
			});
		}

		ContainerHelper.addInventorySlots(this, inv, 8, 100, this::addSlot);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = this.slots;
		Slot slot = inventorySlots.get(slotNumber);
		FuelLoaderTileEntity tileEntity = this.getTileEntity();

		if (slot != null && slot.hasItem() == true)
		{
			ItemStack slotStack = slot.getItem();
			itemStack = slotStack.copy();

			if (0 <= slotNumber && slotNumber < tileEntity.getContainerSize())
			{
				if (!this.moveItemStackTo(slotStack, tileEntity.getContainerSize(), inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}

			}
			else
			{
				boolean moved = false;
				int internalSlotFluidSink = tileEntity.getSlotFluidSink();

				for (int i = 0; i < tileEntity.getContainerSize(); i++)
				{
					if (slotStack.isEmpty() == true)
					{
						break;
					}

					if (i != internalSlotFluidSink)
					{
						if (this.moveItemStackTo(slotStack, i, i + 1, false) == true)
						{
							moved = true;
						}

					}

				}

				if (this.moveItemStackTo(slotStack, internalSlotFluidSink, internalSlotFluidSink + 1, false) == true)
				{
					moved = true;
				}

				if (moved == false)
				{
					return ItemStack.EMPTY;
				}

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

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return !this.getTileEntity().isRemoved();
	}

	public FuelLoaderTileEntity getTileEntity()
	{
		return this.tileEntity;
	}

}
