package astrocraft_giselle_addon.common.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.mrscauthd.astrocraft.machines.tile.AbstractMachineBlockEntity;

public class AbstractMachineContainerMenu<O extends AbstractMachineContainerMenu<O, T>, T extends AbstractMachineBlockEntity> extends AbstractContainerMenu
{
	private T blockEntity;

	public AbstractMachineContainerMenu(MenuType<? extends O> type, int windowId, Inventory inv, T blockEntity)
	{
		super(type, windowId);
		this.blockEntity = blockEntity;
	}

	@Override
	public boolean stillValid(Player player)
	{
		return !this.getBlockEntity().isRemoved();
	}

	public T getBlockEntity()
	{
		return this.blockEntity;
	}

}
