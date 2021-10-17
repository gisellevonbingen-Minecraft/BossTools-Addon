package giselle.bosstools_addon.common.tile;

import giselle.bosstools_addon.common.block.OxygenAccepterBlock;
import giselle.bosstools_addon.compat.CompatibleManager;
import giselle.bosstools_addon.compat.mekanism.GasHelper;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.block.OxygenGeneratorBlock;
import net.mrscauthd.boss_tools.block.OxygenMachineBlock;

public class OxygenAccepterTileEntity extends TileEntity implements ITickableTileEntity
{
	public OxygenAccepterTileEntity()
	{
		super(AddonTiles.OXYGEN_ACCEPTER.get());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (CompatibleManager.MEKANISM.isLoaded() == true)
		{
			if (cap == GasHelper.getGasHandlerCapability())
			{
				return LazyOptional.of(GasHelper::createEmptyHandler).cast();
			}

		}

		return super.getCapability(cap, side);
	}

	public long extractFuelAround(long amount, boolean execute)
	{
		long maxExtract = amount;
		World level = this.getLevel();
		BlockPos blockPos = this.getBlockPos();

		if (amount > 0 && CompatibleManager.MEKANISM.isLoaded() == true)
		{
			long gasPerFuel = 8;
			long testExtract = amount * gasPerFuel;
			long testExtracted = GasHelper.extractGasAround(level, blockPos, GasHelper.getOxygenStack(testExtract), false);
			long realExtrating = (testExtracted / gasPerFuel) * gasPerFuel;

			if (realExtrating > 0)
			{
				long gasExtracted = GasHelper.extractGasAround(level, blockPos, GasHelper.getOxygenStack(realExtrating), execute);
				amount -= gasExtracted / gasPerFuel;
			}

		}

		return maxExtract - amount;
	}

	@Override
	public void tick()
	{
		World level = this.getLevel();
		BlockPos blockPos = this.getBlockPos();
		boolean worked = false;

		for (Direction direction : Direction.values())
		{
			TileEntity blockEntity = level.getBlockEntity(blockPos.offset(direction.getNormal()));
			OxygenMachineAdapter oxygenAdapter = this.createAdapter(blockEntity);

			if (this.giveFuel(oxygenAdapter) == true)
			{
				worked = true;
			}

		}

		BlockState blockState = this.getBlockState();

		if (blockState.hasProperty(OxygenAccepterBlock.POWERED))
		{
			level.setBlockAndUpdate(blockPos, blockState.setValue(OxygenAccepterBlock.POWERED, worked));
		}
	}

	public OxygenMachineAdapter createAdapter(TileEntity blockEntity)
	{
		OxygenMachineAdapter adapter = null;

		if (blockEntity instanceof OxygenMachineBlock.CustomTileEntity)
		{
			adapter = new OxygenLoaderAdapter((OxygenMachineBlock.CustomTileEntity) blockEntity);

		}
		else if (blockEntity instanceof OxygenGeneratorBlock.CustomTileEntity)
		{
			adapter = new OxygenGeneratorAdapter((OxygenGeneratorBlock.CustomTileEntity) blockEntity);
		}

		return adapter;
	}

	public boolean giveFuel(OxygenMachineAdapter adapter)
	{
		if (adapter == null)
		{
			return false;
		}

		double maxFuel = adapter.getMaxFuel();
		double fuel = adapter.getFuel();
		double remain = maxFuel - fuel;

		if (remain > 0)
		{
			double extract = Math.min(remain, 1.0D);
			long extractedFuel = this.extractFuelAround((long) extract, false);

			if (extractedFuel > 0)
			{
				this.extractFuelAround((long) extract, true);
				adapter.setFuel(fuel + extractedFuel);
				adapter.setMaxFuel(maxFuel);
				return true;
			}

		}

		return false;
	}

}
