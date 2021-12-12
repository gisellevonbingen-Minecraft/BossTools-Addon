package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FuelLoaderBlock extends AbstractMachineBlock<FuelLoaderTileEntity>
{
	public FuelLoaderBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public FuelLoaderTileEntity createTileEntity(BlockState state, IBlockReader level)
	{
		return new FuelLoaderTileEntity();
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos)
	{
		return this.getTileEntity(level, pos).isActivated() ? 15 : 0;
	}

}
