package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.beyond_earth.gui.helper.ContainerHelper;

public class FuelLoaderContainerMenu extends AbstractMachineContainerMenu<FuelLoaderContainerMenu, FuelLoaderBlockEntity>
{
	public FuelLoaderContainerMenu(int windowId, Inventory inv, FuelLoaderBlockEntity blockEntity)
	{
		super(AddonMenuTypes.FUEL_LOADER.get(), windowId, inv, blockEntity);

		IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();

		for (int i = blockEntity.getSlotFluidStart(); i < blockEntity.getSlotFluidEnd(); i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, i, 88, 28 + 30 * i));
		}

		ContainerHelper.addInventorySlots(this, inv, 8, 100, this::addSlot);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotNumber)
	{
		return ContainerHelper.transferStackInSlot(this, player, slotNumber, 0, this.getBlockEntity(), this::moveItemStackTo);
	}

}
