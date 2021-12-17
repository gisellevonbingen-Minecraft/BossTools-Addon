package boss_tools_giselle_addon.common.block;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

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

public abstract class AbstractMachineBlock<T extends AbstractMachineTileEntity> extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public AbstractMachineBlock(Properties properties)
	{
		super(properties.lightLevel(new ToIntFunction<BlockState>()
		{
			@Override
			public int applyAsInt(BlockState state)
			{
				Block block = state.getBlock();

				if (block instanceof AbstractMachineBlock<?>)
				{
					return ((AbstractMachineBlock<?>) block).getLightLevel(state);
				}

				return 0;
			}
		}));

		this.registerDefaultState(this.buildDefaultState());
	}

	protected BlockState buildDefaultState()
	{
		BlockState any = this.stateDefinition.any();

		if (this.useFacing() == true)
		{
			any = any.setValue(FACING, Direction.NORTH);
		}

		if (this.useLit() == true)
		{
			any = any.setValue(LIT, false);
		}

		return any;
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
	public BlockState rotate(BlockState state, Rotation rotation)
	{
		if (this.useFacing() == true)
		{
			return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
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

	protected int getLightLevel(BlockState state)
	{
		if (this.useLit() == true)
		{
			return state.getValue(LIT) ? 12 : 0;
		}
		else
		{
			return 0;
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
			T tileEntity = this.getTileEntity(level, pos);

			if (tileEntity != null)
			{
				NonNullList<ItemStack> stacks = ItemHandlerHelper2.getStacks(tileEntity.getItemHandler());
				InventoryHelper.dropContents(level, pos, stacks);
			}

		}

		super.onRemove(state, level, pos, newState, isMoving);
	}

	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult raytrace)
	{
		if (entity instanceof ServerPlayerEntity)
		{
			T tileEntity = this.getTileEntity(level, pos);

			if (tileEntity != null)
			{
				NetworkHooks.openGui((ServerPlayerEntity) entity, tileEntity, pos);
			}

			return ActionResultType.CONSUME;
		}
		else
		{
			return ActionResultType.SUCCESS;
		}

	}

	@SuppressWarnings("unchecked")
	public T getTileEntity(World level, BlockPos pos)
	{
		TileEntity tileEntity = level.getBlockEntity(pos);

		if (tileEntity instanceof AbstractMachineTileEntity)
		{
			return (T) tileEntity;
		}

		return null;
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
	public abstract T createTileEntity(BlockState state, IBlockReader level);

}
