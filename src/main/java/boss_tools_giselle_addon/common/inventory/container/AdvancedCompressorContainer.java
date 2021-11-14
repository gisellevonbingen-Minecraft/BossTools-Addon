package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import net.minecraft.entity.player.PlayerInventory;

public class AdvancedCompressorContainer extends ItemStackToItemStackContainer<AdvancedCompressorContainer, AdvancedCompressorTileEntity>
{
	public AdvancedCompressorContainer(int windowId, PlayerInventory inv, AdvancedCompressorTileEntity tileEntity)
	{
		super(AddonContainers.ADVANCED_COMPRESSOR.get(), windowId, inv, tileEntity);
	}

}
