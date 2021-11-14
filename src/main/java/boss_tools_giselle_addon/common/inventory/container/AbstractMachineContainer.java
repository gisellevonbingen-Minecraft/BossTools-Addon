package boss_tools_giselle_addon.common.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class AbstractMachineContainer<O extends AbstractMachineContainer<O, T>, T extends AbstractMachineTileEntity> extends Container
{
	private T tileEntity;

	public AbstractMachineContainer(ContainerType<? extends O> type, int windowId, PlayerInventory inv, T tileEntity)
	{
		super(type, windowId);
		this.tileEntity = tileEntity;
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

}
