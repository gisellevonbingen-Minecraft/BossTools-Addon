package boss_tools_giselle_addon.common.block;

import boss_tools_giselle_addon.util.AnalogOutputSignalUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;

public abstract class ItemStackToItemStackBlock<T extends ItemStackToItemStackTileEntity> extends AbstractMachineBlock<T>
{
	public ItemStackToItemStackBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos)
	{
		T tileEntity = this.getTileEntity(level, pos);
		ItemStack output = tileEntity.getItem(tileEntity.getSlotOutput());
		return AnalogOutputSignalUtil.getAnalogOutputSignal(output);
	}

}
