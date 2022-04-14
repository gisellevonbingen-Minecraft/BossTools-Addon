package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.beyond_earth.guis.helper.ContainerHelper;

public class ItemStackToItemStackContainerMenu<O extends ItemStackToItemStackContainerMenu<O, T>, T extends ItemStackToItemStackBlockEntityMultiRecipe> extends AbstractMachineContainerMenu<ItemStackToItemStackContainerMenu<O, T>, T>
{
	private int handlerEndIndex;
	private net.minecraft.world.inventory.Slot inputSlot;

	public ItemStackToItemStackContainerMenu(MenuType<? extends O> type, int windowId, Inventory inv, T tileEntity)
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
	public ItemStack quickMoveStack(Player player, int slotNumber)
	{
		return ContainerHelper.transferStackInSlot(this, player, slotNumber, 0, this.getHandlerEndIndex(), this::moveItemStackTo);
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
