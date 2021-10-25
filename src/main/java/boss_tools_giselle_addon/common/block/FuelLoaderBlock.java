package boss_tools_giselle_addon.common.block;

import java.util.Collections;
import java.util.List;

import boss_tools_giselle_addon.common.inventory.ItemHandlerHelper2;
import boss_tools_giselle_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class FuelLoaderBlock extends Block
{
	public FuelLoaderBlock(Properties properties)
	{
		super(properties.strength(3.0F).harvestTool(ToolType.PICKAXE));
	}

	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileEntity = level.getBlockEntity(pos);

			if (tileEntity instanceof FuelLoaderTileEntity)
			{
				NonNullList<ItemStack> stacks = ItemHandlerHelper2.getStacks(((FuelLoaderTileEntity) tileEntity).getItemHandler());
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

			if (tileEntity instanceof FuelLoaderTileEntity)
			{
				FuelLoaderTileEntity fuelLoader = (FuelLoaderTileEntity) level.getBlockEntity(pos);
				fuelLoader.openGui((ServerPlayerEntity) entity);
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
		return new FuelLoaderTileEntity();
	}

}
