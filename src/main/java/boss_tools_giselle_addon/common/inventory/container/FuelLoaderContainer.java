package boss_tools_giselle_addon.common.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class FuelLoaderContainer extends AbstractMachineContainer<FuelLoaderContainer, FuelLoaderTileEntity>
{
	private final List<Slot> inputSlots;

	public FuelLoaderContainer(int windowId, PlayerInventory inv, FuelLoaderTileEntity tileEntity)
	{
		super(AddonContainers.FUEL_LOADER.get(), windowId, inv, tileEntity);

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();

		for (int i = tileEntity.getSlotFluidStart(); i < tileEntity.getSlotFluidEnd(); i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, i, 144, 28 + 30 * i));
		}

		int slotInputStart = tileEntity.getSlotInputStart();
		int slotsInput = tileEntity.getSlotsInput();
		List<Slot> inputSlots = new ArrayList<>();

		for (int i = 0; i < slotsInput; i++)
		{
			Slot slot = this.addSlot(new SlotItemHandler(itemHandler, slotInputStart + i, 8 + 18 * i, 20));
			inputSlots.add(slot);
		}

		this.inputSlots = Collections.unmodifiableList(inputSlots);

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

	public List<Slot> getInputSlots()
	{
		return this.inputSlots;
	}

}
