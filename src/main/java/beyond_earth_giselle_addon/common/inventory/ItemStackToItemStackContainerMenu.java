package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.ItemStackToItemStackBlockEntityMultiRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.beyond_earth.guis.helper.ContainerHelper;

public class ItemStackToItemStackContainerMenu<O extends ItemStackToItemStackContainerMenu<O, T>, T extends ItemStackToItemStackBlockEntityMultiRecipe> extends AbstractMachineContainerMenu<ItemStackToItemStackContainerMenu<O, T>, T>
{
	private int handlerEndIndex;
	private Slot inputSlot;
	private Slot outputSlot;

	public ItemStackToItemStackContainerMenu(MenuType<? extends O> type, int windowId, Inventory inv, T blockEntity)
	{
		super(type, windowId, inv, blockEntity);

		this.inputSlot = this.addSlot(this.createInputSlot(blockEntity));
		this.outputSlot = this.addSlot(this.createOutputSlot(blockEntity));

		this.handlerEndIndex = this.slots.size();
		int inventoryTop = this.getInventoryTop();
		ContainerHelper.addInventorySlots(this, inv, 8, inventoryTop, inventoryTop + 58, this::addSlot);
	}

	protected Slot createInputSlot(T tileEntity)
	{
		return new SlotItemHandler(tileEntity.getItemHandler(), tileEntity.getSlotIngredient(), 40, 22);
	}

	protected Slot createOutputSlot(T tileEntity)
	{
		return new SlotItemHandler(tileEntity.getItemHandler(), tileEntity.getSlotOutput(), 92, 22)
		{
			@Override
			public boolean mayPlace(ItemStack stack)
			{
				return false;
			}

			@Override
			public void onTake(Player player, ItemStack stack)
			{
				onOutputSlotTake(player, stack);
				super.onTake(player, stack);
			}

		};
	}

	protected void onOutputSlotTake(Player player, ItemStack stack)
	{

	}

	public int getInventoryTop()
	{
		return 86;
	}

	public Slot getInputSlot()
	{
		return this.inputSlot;
	}

	public Slot getOutputSlot()
	{
		return this.outputSlot;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotNumber)
	{
		return ContainerHelper2.quickMoveStack(this, player, slotNumber, 0, this.getHandlerEndIndex(), this::moveItemStackTo);
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
