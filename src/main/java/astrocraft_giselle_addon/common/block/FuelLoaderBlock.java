package astrocraft_giselle_addon.common.block;

import astrocraft_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.astrocraft.machines.AbstractMachineBlock;

public class FuelLoaderBlock extends AbstractMachineBlock<FuelLoaderBlockEntity>
{
	public FuelLoaderBlock(BlockBehaviour.Properties properties)
	{
		super(properties.strength(3.0F));
	}

	@Override
	public FuelLoaderBlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new FuelLoaderBlockEntity(pos, state);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		return this.getBlockEntity(level, pos).isActivated() ? 15 : 0;
	}

}
