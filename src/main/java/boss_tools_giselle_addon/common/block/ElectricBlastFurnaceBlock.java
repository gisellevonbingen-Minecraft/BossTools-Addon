package boss_tools_giselle_addon.common.block;

import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import boss_tools_giselle_addon.common.tile.ElectricBlastFurnaceTileEntity;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class ElectricBlastFurnaceBlock extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public ElectricBlastFurnaceBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
		builder.add(LIT);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		return state.getValue(LIT) ? 12 : 0;
	}

	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileEntity = level.getBlockEntity(pos);

			if (tileEntity instanceof ElectricBlastFurnaceTileEntity)
			{
				 NonNullList<ItemStack> stacks = ItemHandlerHelper2.getStacks(((ElectricBlastFurnaceTileEntity) tileEntity).getItemHandler());
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

			if (tileEntity instanceof ElectricBlastFurnaceTileEntity)
			{
				ElectricBlastFurnaceTileEntity blastFurnace = (ElectricBlastFurnaceTileEntity) level.getBlockEntity(pos);
				NetworkHooks.openGui((ServerPlayerEntity) entity, blastFurnace, pos);
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

	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new ElectricBlastFurnaceTileEntity();
	}

}