package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.ItemStackToItemStackTileEntityMultiRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class ItemStackToItemStackContainer<O extends ItemStackToItemStackContainer<O, T>, T extends ItemStackToItemStackTileEntityMultiRecipe> extends AbstractMachineContainer<ItemStackToItemStackContainer<O, T>, T>
{
	private int handlerEndIndex;
	private Slot inputSlot;
	private Slot outputSlot;

	public ItemStackToItemStackContainer(ContainerType<? extends O> type, int windowId, PlayerInventory inv, T tileEntity)
	{
		super(type, windowId, inv, tileEntity);

		this.inputSlot = this.addSlot(this.createInputSlot(tileEntity));
		this.outputSlot = this.addSlot(this.createOutputSlot(tileEntity));

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
			public ItemStack onTake(PlayerEntity player, ItemStack stack)
			{
				onOutputSlotTake(player, stack);
				return super.onTake(player, stack);
			}

		};
	}

	protected void onOutputSlotTake(PlayerEntity player, ItemStack stack)
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
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		return ContainerHelper2.quickMoveStack(this, player, slotNumber, 0, this.getHandlerEndIndex(), this::moveItemStackTo);
	}

	public int getHandlerEndIndex()
	{
		return this.handlerEndIndex;
	}

}
