package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import net.minecraft.world.entity.player.Inventory;

public class AdvancedCompressorContainerMenu extends ItemStackToItemStackContainerMenu<AdvancedCompressorContainerMenu, AdvancedCompressorBlockEntity>
{
	public AdvancedCompressorContainerMenu(int windowId, Inventory inv, AdvancedCompressorBlockEntity blockEntity)
	{
		super(AddonMenuTypes.ADVANCED_COMPRESSOR.get(), windowId, inv, blockEntity);
	}

	@Override
	public int getInventoryTop()
	{
		return 105;
	}

}
