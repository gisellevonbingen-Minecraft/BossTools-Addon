package beyond_earth_giselle_addon.common.block;

import beyond_earth_giselle_addon.common.block.entity.ElectricBlastFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricBlastFurnaceBlock extends ItemStackToItemStackBlock<ElectricBlastFurnaceBlockEntity>
{
	public ElectricBlastFurnaceBlock(Properties properties)
	{
		super(properties.strength(3.0F));
	}

	@Override
	protected boolean useFacing()
	{
		return true;
	}

	@Override
	protected boolean useLit()
	{
		return true;
	}

	@Override
	public ElectricBlastFurnaceBlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new ElectricBlastFurnaceBlockEntity(pos, state);
	}

}
