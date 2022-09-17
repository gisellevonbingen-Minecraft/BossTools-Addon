package boss_tools_giselle_addon.common.inventory.container;

import boss_tools_giselle_addon.common.registries.AddonContainerTypes;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ElectricBlastFurnaceContainer extends ItemStackToItemStackContainer<ElectricBlastFurnaceContainer, ElectricBlastFurnaceTileEntity>
{
	public ElectricBlastFurnaceContainer(int windowId, PlayerInventory inv, ElectricBlastFurnaceTileEntity tileEntity)
	{
		super(AddonContainerTypes.ELECTRIC_BLAST_FURNACE.get(), windowId, inv, tileEntity);
	}

	@Override
	protected void onOutputSlotTake(PlayerEntity player, ItemStack stack)
	{
		super.onOutputSlotTake(player, stack);

		this.getTileEntity().awardExp(player.level, player.position());
	}

}
