package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.AdvancedCompressorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class AdvancedCompressorBlock extends ItemStackToItemStackBlock<AdvancedCompressorTileEntity>
{
	public AdvancedCompressorBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
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

	public AdvancedCompressorTileEntity createTileEntity(BlockState state, IBlockReader level)
	{
		return new AdvancedCompressorTileEntity();
	}
	
}
