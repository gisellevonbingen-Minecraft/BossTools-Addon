package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;

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

}
