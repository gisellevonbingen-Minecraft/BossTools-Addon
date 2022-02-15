package beyond_earth_giselle_addon.common.capability;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.mrscauthd.beyond_earth.capability.oxygen.CapabilityOxygen;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;

public class OxygenUtil2
{
	public static <T> LazyOptional<T> getOxygenStorageOrEmpty(Capability<T> capability, Direction direction, @Nullable NonNullSupplier<IOxygenStorage> oxygenStorage)
	{
		if (capability == null)
		{
			return LazyOptional.empty();
		}
		else if (capability == CapabilityOxygen.OXYGEN)
		{
			return LazyOptional.of(oxygenStorage).cast();
		}
//		else if (CompatibleManager.MEKANISM.isLoaded() && capability == MekanismHelper.getGasHandlerCapability())
//		{
//			return LazyOptional.of(oxygenStorage).lazyMap(OxygenUtil2::getOxygenGasAdapter).cast();
//		}

		return LazyOptional.empty();
	}

//	public static OxygenStorageGasAdapter getOxygenGasAdapter(IOxygenStorage oxygenStorage)
//	{
//		return new OxygenStorageGasAdapter(oxygenStorage, true, true);
//	}

	private OxygenUtil2()
	{

	}

}
