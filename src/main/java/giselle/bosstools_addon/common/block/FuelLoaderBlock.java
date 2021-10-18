package giselle.bosstools_addon.common.block;

import java.util.Collections;
import java.util.List;

import giselle.bosstools_addon.common.tile.FuelLoaderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FuelLoaderBlock extends Block
{
	public FuelLoaderBlock(Properties properties)
	{
		super(properties);
	}

	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult raytrace)
	{
		if (entity instanceof ServerPlayerEntity)
		{
			FuelLoaderTileEntity blockEntity = (FuelLoaderTileEntity) level.getBlockEntity(pos);
			blockEntity.openGui((ServerPlayerEntity) entity);
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
