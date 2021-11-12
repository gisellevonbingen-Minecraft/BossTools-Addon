package boss_tools_giselle_addon.common.block;

import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public abstract class AbstractMachineBlock extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public AbstractMachineBlock(Properties properties)
	{
		super(properties);

		BlockState any = this.stateDefinition.any();

		if (this.useFacing() == true)
		{
			any = any.setValue(FACING, Direction.NORTH);
		}

		if (this.useLit() == true)
		{
			any = any.setValue(LIT, false);
		}

		this.registerDefaultState(any);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);

		if (this.useFacing() == true)
		{
			builder.add(FACING);
		}

		if (this.useLit() == true)
		{
			builder.add(LIT);
		}

	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		if (this.useFacing() == true)
		{
			return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
		}
		else
		{
			return state;
		}

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState state = this.defaultBlockState();

		if (this.useFacing() == true)
		{
			return state.setValue(FACING, context.getHorizontalDirection().getOpposite());
		}
		else
		{
			return state;
		}

	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		if (this.useLit() == true)
		{
			return state.getValue(LIT) ? 12 : 0;
		}
		else
		{
			return super.getLightValue(state, world, pos);
		}

	}

	protected boolean useFacing()
	{
		return false;
	}

	protected boolean useLit()
	{
		return false;
	}

	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileEntity = level.getBlockEntity(pos);

			if (tileEntity instanceof AbstractMachineTileEntity)
			{
				NonNullList<ItemStack> stacks = ItemHandlerHelper2.getStacks(((AbstractMachineTileEntity) tileEntity).getItemHandler());
				InventoryHelper.dropContents(level, pos, stacks);
			}

		}

		super.onRemove(state, level, pos, newState, isMoving);
	}

	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult raytrace)
	{
		if (entity instanceof ServerPlayerEntity)
		{
			TileEntity tileEntity = level.getBlockEntity(pos);

			if (tileEntity instanceof AbstractMachineTileEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity) entity, (AbstractMachineTileEntity) level.getBlockEntity(pos), pos);
			}

			return ActionResultType.CONSUME;
		}
		else
		{
			return ActionResultType.SUCCESS;
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

	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

}
