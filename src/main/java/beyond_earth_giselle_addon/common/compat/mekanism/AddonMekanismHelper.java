package beyond_earth_giselle_addon.common.compat.mekanism;

import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;

public class AddonMekanismHelper
{
	public static Capability<IGasHandler> getGasHandlerCapability()
	{
		return Capabilities.GAS_HANDLER_CAPABILITY;
	}

	public static IGasHandler getItemStackGasHandler(ItemStack itemStack)
	{
		if (itemStack.isEmpty())
		{
			return null;
		}

		return itemStack.getCapability(getGasHandlerCapability()).orElse(null);
	}

	public static IOxygenStorage getItemStackOxygenAdapter(ItemStack itemStack)
	{
		IGasHandler gasHandler = getItemStackGasHandler(itemStack);

		if (gasHandler != null)
		{
			return new GasHandlerOxygenAdapter(gasHandler, true, true);
		}
		else
		{
			return null;
		}

	}

}
