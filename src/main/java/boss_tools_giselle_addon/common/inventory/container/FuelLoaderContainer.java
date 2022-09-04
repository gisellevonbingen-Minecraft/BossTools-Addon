package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class FuelLoaderContainer extends AbstractMachineContainer<FuelLoaderContainer, FuelLoaderTileEntity>
{
	public FuelLoaderContainer(int windowId, PlayerInventory inv, FuelLoaderTileEntity tileEntity)
	{
		super(AddonContainerTypes.FUEL_LOADER.get(), windowId, inv, tileEntity);

		IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();

		for (int i = tileEntity.getSlotFluidStart(); i < tileEntity.getSlotFluidEnd(); i++)
		{
			this.addSlot(new SlotItemHandler(itemHandler, i, 88, 28 + 30 * i));
		}

		ContainerHelper.addInventorySlots(this, inv, 8, 100, this::addSlot);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotNumber)
	{
		return ContainerHelper2.quickMoveStack(this, player, slotNumber, 0, this.getTileEntity(), this::moveItemStackTo);
	}

}
