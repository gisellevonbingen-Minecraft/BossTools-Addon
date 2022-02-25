package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class ItemStackToItemStackContainer<O extends ItemStackToItemStackContainer<O, T>, T extends ItemStackToItemStackTileEntityMultiRecipe> extends AbstractMachineContainer<ItemStackToItemStackContainer<O, T>, T>
{
	private int handlerEndIndex;
	private Slot inputSlot;

	public ItemStackToItemStackContainer(ContainerType<? extends O> type, int windowId, PlayerInventory inv, T tileEntity)
	{
		super(type, windowId, inv, tileEntity);

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
		this.inputSlot = this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotIngredient(), 40, 22));
		this.addSlot(new SlotItemHandler(itemHandler, tileEntity.getSlotOutput(), 92, 22)
		{
			@Override
			public boolean mayPlace(ItemStack stack)
			{
				return false;
			}
		});

		this.handlerEndIndex = this.slots.size();
		int inventoryTop = this.getInventoryTop();
		ContainerHelper.addInventorySlots(this, inv, 8, inventoryTop, inventoryTop + 58, this::addSlot);
	}

	public int getInventoryTop()
	{
		return 86;
	}

	public Slot getInputSlot()
	{
		return this.inputSlot;
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
