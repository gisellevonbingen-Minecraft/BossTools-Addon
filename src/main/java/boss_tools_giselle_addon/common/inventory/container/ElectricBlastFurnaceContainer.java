package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.ContainerHelper;

public class ElectricBlastFurnaceContainer extends Container
{
	private ElectricBlastFurnaceTileEntity tileEntity;
	private int handlerEndIndex;
	private Slot inputSlot;

	public ElectricBlastFurnaceContainer(int windowId, PlayerInventory inv, ElectricBlastFurnaceTileEntity tileEntity)
	{
		super(AddonContainers.ELECTRIC_BLAST_FURNACE.get(), windowId);
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

	public ElectricBlastFurnaceTileEntity getTileEntity()
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
