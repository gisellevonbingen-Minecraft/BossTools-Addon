package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.common.block.entity.AdvancedCompressorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedCompressorBlock extends ItemStackToItemStackBlock<AdvancedCompressorBlockEntity>
{
	public AdvancedCompressorBlock(BlockBehaviour.Properties properties)
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
	public AdvancedCompressorBlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new AdvancedCompressorBlockEntity(pos, state);
	}

}
