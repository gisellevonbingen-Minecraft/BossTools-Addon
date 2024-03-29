package beyond_earth_giselle_addon.common.block;

import beyond_earth_giselle_addon.common.util.AnalogOutputSignalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.ItemStackToItemStackBlockEntity;
import net.mrscauthd.beyond_earth.common.blocks.machines.AbstractMachineBlock;

public abstract class ItemStackToItemStackBlock<T extends ItemStackToItemStackBlockEntity> extends AbstractMachineBlock<T>
{
	public ItemStackToItemStackBlock(BlockBehaviour.Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		T blockEntity = this.getBlockEntity(level, pos);
		ItemStack output = blockEntity.getItem(blockEntity.getSlotOutput());
		return AnalogOutputSignalUtils.getAnalogOutputSignal(output);
	}

}
