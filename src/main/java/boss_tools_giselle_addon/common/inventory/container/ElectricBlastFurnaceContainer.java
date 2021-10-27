package boss_tools_giselle_addon.common.inventory.container;

import java.util.ArrayList;
import java.util.List;

import boss_tools_giselle_addon.common.tile.ContainerHelper;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class ElectricBlastFurnaceContainer extends Container
{
	private ElectricBlastFurnaceTileEntity tileEntity;
	private List<Slot> inputSlots;
	private List<Slot> outputSlots;
	private int inputEndIndex;
	private int handlerEndIndex;

	public ElectricBlastFurnaceContainer(int windowId, PlayerInventory inv, ElectricBlastFurnaceTileEntity tileEntity)
	{
		super(AddonContainers.ELECTRIC_BLAST_FURNACE.get(), windowId);
		this.tileEntity = tileEntity;

		IItemHandlerModifiable inputInventory = tileEntity.getInputInventory();
		IItemHandlerModifiable outputInventory = tileEntity.getOutputInventory();

		this.inputSlots = new ArrayList<>();
		for (int i = 0; i < inputInventory.getSlots(); i++)
		{
			this.inputSlots.add(this.addSlot(new SlotItemHandler(inputInventory, i, 40, 36 + 18 * i)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return !ItemStack.matches(inputInventory.insertItem(this.getSlotIndex(), itemStack, true), itemStack);
				}
			}));
		}

		this.inputEndIndex = this.slots.size();
		this.outputSlots = new ArrayList<>();
		for (int i = 0; i < outputInventory.getSlots(); i++)
		{
			this.outputSlots.add(this.addSlot(new SlotItemHandler(outputInventory, i, 92, 36 + 18 * i)
			{
				@Override
				public boolean mayPlace(ItemStack itemStack)
				{
					return false;
				}
			}));
		}

		this.handlerEndIndex = this.slots.size();
		ContainerHelper.addInventorySlots(this, inv, 8, 86, 144, this::addSlot);
	}

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return !this.getTileEntity().isRemoved();
	}

	public ElectricBlastFurnaceTileEntity getTileEntity()
	{
		return this.tileEntity;
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = this.slots;
		Slot slot = inventorySlots.get(slotNumber);

		if (slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			itemStack = slotStack.copy();

			if (slotNumber < this.handlerEndIndex)
			{
				if (!this.moveItemStackTo(slotStack, this.handlerEndIndex, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}

			}
			else if (!this.moveItemStackTo(slotStack, 0, this.inputEndIndex, false))
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

	public List<Slot> getInputSlots()
	{
		return new ArrayList<>(this.inputSlots);
	}

	public List<Slot> getOutputSlots()
	{
		return new ArrayList<>(this.outputSlots);
	}

	public int getInputEndIndex()
	{
		return this.inputEndIndex;
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
