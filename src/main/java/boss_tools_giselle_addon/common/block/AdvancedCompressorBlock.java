package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;

public class AdvancedCompressorBlock extends ItemStackToItemStackBlock<AdvancedCompressorTileEntity>
{
	public AdvancedCompressorBlock(Properties properties)
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
	public AdvancedCompressorTileEntity createTileEntity(BlockState state, IBlockReader level)
	{
		return new AdvancedCompressorTileEntity();
	}

}
