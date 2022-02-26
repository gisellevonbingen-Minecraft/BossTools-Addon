package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import net.minecraft.entity.player.PlayerInventory;

public class AdvancedCompressorContainer extends ItemStackToItemStackContainer<AdvancedCompressorContainer, AdvancedCompressorTileEntity>
{
	public AdvancedCompressorContainer(int windowId, PlayerInventory inv, AdvancedCompressorTileEntity tileEntity)
	{
		super(AddonContainerTypes.ADVANCED_COMPRESSOR.get(), windowId, inv, tileEntity);
	}
	
	@Override
	public int getInventoryTop()
	{
		return 105;
	}

}
