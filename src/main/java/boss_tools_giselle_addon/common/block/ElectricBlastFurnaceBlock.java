package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class ElectricBlastFurnaceBlock extends ItemStackToItemStackBlock<ElectricBlastFurnaceTileEntity>
{
	public ElectricBlastFurnaceBlock(Properties properties)
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

	public ElectricBlastFurnaceTileEntity createTileEntity(BlockState state, IBlockReader level)
	{
		return new ElectricBlastFurnaceTileEntity();
	}

}
