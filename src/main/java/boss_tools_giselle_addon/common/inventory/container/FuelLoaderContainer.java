package boss_tools_giselle_addon.common.inventory.container;

import java.util.List;

import boss_tools_giselle_addon.common.tile.ContainerHelper;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class FuelLoaderContainer extends Container
{
	private FuelLoaderTileEntity tileEntity;
	private int internalSlotFluidSink;
	private int inventorySize;

	public FuelLoaderContainer(int windowId, PlayerInventory inv, FuelLoaderTileEntity tileEntity)
	{
		super(AddonContainers.FUEL_LOADER.get(), windowId);
		this.tileEntity = tileEntity;

		IItemHandlerModifiable fluidInventory = tileEntity.getFluidInventory();
		IItemHandlerModifiable inputInventory = tileEntity.getInputInventory();
		IItemHandlerModifiable outputInventory = tileEntity.getOutputInventory();

		for (int i = 0; i < fluidInventory.getSlots(); i++)
		{
			this.addSlot(new SlotItemHandler(fluidInventory, i, 152, 20 + 46 * i)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return !ItemStack.matches(fluidInventory.insertItem(this.getSlotIndex(), itemStack, true), itemStack);
				}
			});

			if (i == tileEntity.getInternalSlotFluidSink())
			{
				this.internalSlotFluidSink = this.slots.size() - 1;
			}

		}

		for (int i = 0; i < inputInventory.getSlots(); i++)
		{
			this.addSlot(new SlotItemHandler(inputInventory, i, 8 + 18 * i, 20)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return !ItemStack.matches(inputInventory.insertItem(this.getSlotIndex(), itemStack, true), itemStack);
				}
			});
		}

		for (int i = 0; i < outputInventory.getSlots(); i++)
		{
			this.addSlot(new SlotItemHandler(outputInventory, i, 8 + 18 * i, 66)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return false;
				}
			});
		}

		this.inventorySize = this.slots.size();
		ContainerHelper.addInventorySlots(this, inv, 8, 100, this::addSlot);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = this.slots;
		Slot slot = inventorySlots.get(slotNumber);

		if (slot != null && slot.hasItem() == true)
		{
			ItemStack slotStack = slot.getItem();
			itemStack = slotStack.copy();

			if (0 <= slotNumber && slotNumber < this.inventorySize)
			{
				if (!this.moveItemStackTo(slotStack, this.inventorySize, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}

			}
			else
			{
				boolean moved = false;
				int internalSlotFluidSink = this.internalSlotFluidSink;

				for (int i = 0; i < this.inventorySize; i++)
				{
					if (slotStack.isEmpty())
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
