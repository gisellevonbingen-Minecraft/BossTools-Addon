package beyond_earth_giselle_addon.common.block;

import beyond_earth_giselle_addon.common.block.entity.ElectricBlastFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

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

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock() && level instanceof ServerLevel serverLevel)
		{
			ElectricBlastFurnaceBlockEntity blockEntity = this.getBlockEntity(level, pos);

			if (blockEntity != null)
			{
				blockEntity.awardExp(serverLevel, Vec3.atCenterOf(pos));
			}

		}

		super.onRemove(state, level, pos, newState, isMoving);
	}

}
