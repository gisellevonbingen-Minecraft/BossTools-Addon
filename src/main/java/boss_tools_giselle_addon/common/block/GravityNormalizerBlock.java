package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.tile.GravityNormalizerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class GravityNormalizerBlock extends AbstractMachineBlock
{
	public GravityNormalizerBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
	}

	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new GravityNormalizerTileEntity();
	}

}
