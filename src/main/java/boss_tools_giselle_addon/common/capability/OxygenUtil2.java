package boss_tools_giselle_addon.common.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismHelper;
import net.mrscauthd.boss_tools.compat.mekanism.OxygenStorageGasAdapter;

public class OxygenUtil2
{
	public static <T> LazyOptional<T> getOxygenCapability(Capability<T> capability, Direction direction, IOxygenStorage oxygenStorage)
	{
		if (capability == CapabilityOxygen.OXYGEN)
		{
			return LazyOptional.of(() -> oxygenStorage).cast();
		}
		else if (CompatibleManager.MEKANISM.isLoaded() && capability == MekanismHelper.getGasHandlerCapability())
		{
			return LazyOptional.of(() -> oxygenStorage).lazyMap(OxygenUtil2::getOxygenGasAdapter).cast();
		}

		return LazyOptional.empty();
	}

	public static OxygenStorageGasAdapter getOxygenGasAdapter(IOxygenStorage oxygenStorage)
	{
		return new OxygenStorageGasAdapter(oxygenStorage, true, true);
	}

	private OxygenUtil2()
	{

	}

}
