package astrocraft_giselle_addon.common.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.astrocraft.gui.helper.ContainerHelper;
import net.mrscauthd.astrocraft.machines.tile.ItemStackToItemStackBlockEntity;

public class ItemStackToItemStackContainerMenu<O extends ItemStackToItemStackContainerMenu<O, T>, T extends ItemStackToItemStackBlockEntity> extends AbstractMachineContainerMenu<ItemStackToItemStackContainerMenu<O, T>, T>
{
	@FunctionalInterface
	public interface IItemStackToItemStackContainerConstructor<O extends ItemStackToItemStackContainerMenu<O, T>, T extends ItemStackToItemStackBlockEntity>
	{
		public ItemStackToItemStackContainerMenu<O, T> invoke(MenuType<? extends ItemStackToItemStackContainerMenu<O, T>> type, int windowId, Inventory inv, T tileEntity);
	}

	private int handlerEndIndex;
	private net.minecraft.world.inventory.Slot inputSlot;

	public ItemStackToItemStackContainerMenu(MenuType<? extends O> type, int windowId, Inventory inv, T tileEntity)
	{
		super(type, windowId, inv, tileEntity);

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
		this.inputSlot = this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotIngredient(), 40, 30));
		this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotOutput(), 92, 30)
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
	public ItemStack quickMoveStack(Player player, int slotNumber)
	{
		return ContainerHelper.transferStackInSlot(this, player, slotNumber, 0, this.getHandlerEndIndex(), this::moveItemStackTo);
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
