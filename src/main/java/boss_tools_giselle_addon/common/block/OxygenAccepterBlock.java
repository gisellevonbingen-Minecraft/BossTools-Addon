package boss_tools_giselle_addon.common.block;

import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.tile.OxygenAccepterTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class OxygenAccepterBlock extends Block
{
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public OxygenAccepterBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(POWERED);
	}

	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_)
	{
		if (p_225533_2_.isClientSide)
		{
			return ActionResultType.SUCCESS;
		}
		else
		{
			return ActionResultType.CONSUME;
		}

	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
	{
		@SuppressWarnings("deprecation")
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
		{
			return dropsOriginal;
		}
		else
		{
			return Collections.singletonList(new ItemStack(this));
		}

	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new OxygenAccepterTileEntity();
	}

	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	public int getAnalogOutputSignal(BlockState state, World level, BlockPos pos)
	{
		return state.hasProperty(POWERED) && state.getValue(POWERED) ? 15 : 0;
	}

}
