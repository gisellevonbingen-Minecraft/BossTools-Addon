package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class GravityNormalizerContainer extends AbstractMachineContainer<GravityNormalizerContainer, GravityNormalizerTileEntity>
{
	public GravityNormalizerContainer(int windowId, PlayerInventory inv, GravityNormalizerTileEntity tileEntity)
	{
		super(AddonContainerTypes.GRAVITY_NORMALIZER.get(), windowId, inv, tileEntity);

		ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
	}

}
