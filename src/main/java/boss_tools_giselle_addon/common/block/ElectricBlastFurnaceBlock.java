package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ElectricBlastFurnaceBlock extends ItemStackToItemStackBlock<ElectricBlastFurnaceTileEntity>
{
	public ElectricBlastFurnaceBlock(Properties properties)
	{
		super(properties);
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
	public ElectricBlastFurnaceTileEntity createTileEntity(BlockState state, IBlockReader level)
	{
		return new ElectricBlastFurnaceTileEntity();
	}

	@Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			ElectricBlastFurnaceTileEntity tileEntity = this.getTileEntity(level, pos);

			if (tileEntity != null)
			{
				tileEntity.awardExp(level, Vector3d.atCenterOf(pos));
			}

		}

		super.onRemove(state, level, pos, newState, isMoving);
	}

}
