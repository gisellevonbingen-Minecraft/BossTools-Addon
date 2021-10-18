package giselle.bosstools_addon.common.inventory.container;

import giselle.bosstools_addon.common.tile.ContainerHelper;
import giselle.bosstools_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class FuelLoaderContainer extends Container
{
	private FuelLoaderTileEntity tileEntity;

	public FuelLoaderContainer(int windowId, PlayerInventory inv, FuelLoaderTileEntity tileEntity)
	{
		super(AddonContainers.FUEL_LOADER.get(), windowId);
		this.tileEntity = tileEntity;

		IInventory inputInventory = tileEntity.getInputInventory();
		IInventory outputInventory = tileEntity.getOutputInventory();
		IInventory fluidInventory = tileEntity.getFluidInventory();

		for (int i = 0; i < fluidInventory.getContainerSize(); i++)
		{
			this.addSlot(fluidInventory, i, 152, 20 + 46 * i);
		}

		for (int i = 0; i < inputInventory.getContainerSize(); i++)
		{
			this.addSlot(inputInventory, i, 8 + 18 * i, 20);
		}

		for (int i = 0; i < outputInventory.getContainerSize(); i++)
		{
			this.addSlot(outputInventory, i, 8 + 18 * i, 66);
		}

		ContainerHelper.addInventorySlots(this, inv, 8, 100, this::addSlot);
	}

	private void addSlot(IInventory inventory, int slot, int x, int y)
	{
		this.addSlot(new Slot(inventory, slot, x, y)
		{
			@Override
			public boolean mayPlace(ItemStack itemStack)
			{
				return getTileEntity().getItemHandler().insertItem(this.index, itemStack, true).isEmpty();
			}
		});
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slot)
	{
		FuelLoaderTileEntity tileEntity = this.getTileEntity();
		return ContainerHelper.quickMoveStack(this, player, slot, 0, tileEntity.getItemHandler().getSlots(), this::moveItemStackTo);
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
