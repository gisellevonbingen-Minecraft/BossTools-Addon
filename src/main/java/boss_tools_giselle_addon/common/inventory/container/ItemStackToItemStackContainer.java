package boss_tools_giselle_addon.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.ContainerHelper;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public class ItemStackToItemStackContainer<T extends ItemStackToItemStackTileEntity> extends Container
{
	@FunctionalInterface
	public interface IItemStackToItemStackContainerConstructor<T extends ItemStackToItemStackTileEntity>
	{
		public ItemStackToItemStackContainer<T> invoke(ContainerType<? extends ItemStackToItemStackContainer<T>> type, int windowId, PlayerInventory inv, T tileEntity);
	}

	private T tileEntity;
	private int handlerEndIndex;
	private Slot inputSlot;

	public ItemStackToItemStackContainer(ContainerType<? extends ItemStackToItemStackContainer<T>> type, int windowId, PlayerInventory inv, T tileEntity)
	{
		super(type, windowId);
		this.tileEntity = tileEntity;

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
		this.inputSlot = this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotIngredient(), 40, 36));
		this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotOutput(), 92, 36)
		{
			@Override
			public boolean mayPlace(ItemStack stack)
			{
				return false;
			}
		});

		this.handlerEndIndex = this.slots.size();
		ContainerHelper.addInventorySlots(this, inv, 8, 86, 144, this::addSlot);
	}

	public Slot getInputSlot()
	{
		return this.inputSlot;
	}

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return !this.getTileEntity().isRemoved();
	}

	public T getTileEntity()
	{
		return this.tileEntity;
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		return ContainerHelper.transferStackInSlot(this, player, slotNumber, 0, this.getHandlerEndIndex(), this::moveItemStackTo);
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
