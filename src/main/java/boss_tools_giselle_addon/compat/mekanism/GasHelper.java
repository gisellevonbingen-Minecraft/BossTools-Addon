package boss_tools_giselle_addon.compat.mekanism;

import boss_tools_giselle_addon.compat.mekanism.capability.EmtpyGasHandler;
import mekanism.api.Action;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismGases;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class GasHelper
{
	public static Gas getOxygen()
	{
		return MekanismGases.OXYGEN.get();
	}

	public static GasStack getOxygenStack(long amount)
	{
		return getOxygen().getStack(amount);
	}

	public static Capability<IGasHandler> getGasHandlerCapability()
	{
		return Capabilities.GAS_HANDLER_CAPABILITY;
	}

	private GasHelper()
	{

	}

	public static IGasHandler createEmptyHandler()
	{
		return new EmtpyGasHandler();
	}

	public static long extractGasAround(World level, BlockPos pos, GasStack stack, boolean execute)
	{
		long maxExtract = stack.getAmount();
		stack = stack.copy();
		Action action = Action.get(execute);

		for (Direction direction : Direction.values())
		{
			if (stack.isEmpty() == true)
			{
				break;
			}

			TileEntity blockEntity = level.getBlockEntity(pos.offset(direction.getNormal()));
			IGasHandler gasHandler = blockEntity != null ? blockEntity.getCapability(getGasHandlerCapability(), direction.getOpposite()).orElse(null) : null;

			if (gasHandler != null)
			{
				GasStack extracted = gasHandler.extractChemical(stack, action);

				if (extracted.isEmpty() == false)
				{
					stack.setAmount(stack.getAmount() - extracted.getAmount());
				}

			}

		}

		return maxExtract - stack.getAmount();
	}

}
