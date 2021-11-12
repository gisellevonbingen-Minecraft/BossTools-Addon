package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;

public class ElectricBlastFurnaceContainer extends ItemStackToItemStackContainer<ElectricBlastFurnaceTileEntity>
{
	public ElectricBlastFurnaceContainer(int windowId, PlayerInventory inv, ElectricBlastFurnaceTileEntity tileEntity)
	{
		super(AddonContainers.ELECTRIC_BLAST_FURNACE.get(), windowId, inv, tileEntity);
	}

}
