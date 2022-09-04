package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.ElectricBlastFurnaceBlockEntity;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ElectricBlastFurnaceContainerMenu extends ItemStackToItemStackContainerMenu<ElectricBlastFurnaceContainerMenu, ElectricBlastFurnaceBlockEntity>
{
	public ElectricBlastFurnaceContainerMenu(int windowId, Inventory inv, ElectricBlastFurnaceBlockEntity blockEntity)
	{
		super(AddonMenuTypes.ELECTRIC_BLAST_FURNACE.get(), windowId, inv, blockEntity);
	}

	@Override
	protected void onOutputSlotTake(Player player, ItemStack stack)
	{
		super.onOutputSlotTake(player, stack);

		ElectricBlastFurnaceBlockEntity blockEntity = this.getBlockEntity();
		float stored = blockEntity.getExperience();
		int awarding = (int) stored;

		if (awarding > 0)
		{
			blockEntity.setExperience(stored - awarding);

			if (player.getLevel()instanceof ServerLevel level)
			{
				ExperienceOrb.award(level, player.position(), awarding);
			}

		}

	}

}
