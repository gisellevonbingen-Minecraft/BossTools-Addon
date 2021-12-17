package boss_tools_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;

public class CapabilityOxygenUtils
{
	private static final String KEY_OXYGEN = "oxygen";

	public static <T extends IOxygenStorage> T readNBT(T instance, INBT nbt)
	{
		if (instance != null && nbt instanceof CompoundNBT)
		{
			CompoundNBT compound = (CompoundNBT) nbt;
			instance.setOxygenStored(compound.getInt(KEY_OXYGEN));
		}

		return instance;
	}

	public static INBT writeNBT(IOxygenStorage instance)
	{
		CompoundNBT compound = new CompoundNBT();
		compound.putInt(KEY_OXYGEN, instance.getOxygenStored());
		return compound;
	}

}
