package boss_tools_giselle_addon.common.adapter;

import boss_tools_giselle_addon.common.entity.BossToolsRoverHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class FuelAdapterBossToolsRover extends FuelAdapter<Entity>
{
	public FuelAdapterBossToolsRover(Entity target)
	{
		super(target);
	}

	@Override
	public int fill(int amount, FluidAction action)
	{
		Entity target = this.getTarget();
		int fuelAmount = BossToolsRoverHelper.getFuelAmount(target);
		int fuelCapacity = BossToolsRoverHelper.getFuelCapacity(target);
		int fuelRemain = fuelCapacity - fuelAmount;

		int fuelFilling = MathHelper.clamp(amount, 0, fuelRemain);

		if (action.execute() == true)
		{
			BossToolsRoverHelper.setCurrentFuel(target, fuelAmount + fuelFilling);
		}

		return fuelFilling;
	}

}
