package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import boss_tools_giselle_addon.common.util.AnalogOutputSignalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.IItemHandlerModifiable;

public class FuelLoaderBlock extends AbstractMachineBlock<FuelLoaderTileEntity>
{
	public FuelLoaderBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
	}

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
		IItemHandlerModifiable handler = this.getTileEntity(level, pos).getOutputItemHandler();
		return AnalogOutputSignalUtils.getAnalogOutputSignal(handler);
	}

}
