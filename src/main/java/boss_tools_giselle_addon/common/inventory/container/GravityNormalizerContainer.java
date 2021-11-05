package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.mrscauthd.boss_tools.gui.ContainerHelper;

public class GravityNormalizerContainer extends Container
{
	private GravityNormalizerTileEntity tileEntity;

	public GravityNormalizerContainer(int windowId, PlayerInventory inv, GravityNormalizerTileEntity tileEntity)
	{
		super(AddonContainers.GRAVITY_NORMALIZER.get(), windowId);
		this.tileEntity = tileEntity;

		ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
	}

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return !this.getTileEntity().isRemoved();
	}

	public GravityNormalizerTileEntity getTileEntity()
	{
		return this.tileEntity;
	}

}
