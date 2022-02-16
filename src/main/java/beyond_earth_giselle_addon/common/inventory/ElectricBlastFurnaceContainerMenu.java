package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.ElectricBlastFurnaceBlockEntity;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import net.minecraft.world.entity.player.Inventory;

public class ElectricBlastFurnaceContainerMenu extends ItemStackToItemStackContainerMenu<ElectricBlastFurnaceContainerMenu, ElectricBlastFurnaceBlockEntity>
{
	public ElectricBlastFurnaceContainerMenu(int windowId, Inventory inv, ElectricBlastFurnaceBlockEntity blockEntity)
	{
		super(AddonMenuTypes.ELECTRIC_BLAST_FURNACE.get(), windowId, inv, blockEntity);
	}

}
