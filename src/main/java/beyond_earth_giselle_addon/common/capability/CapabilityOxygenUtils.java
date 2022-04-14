package beyond_earth_giselle_addon.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;

public class CapabilityOxygenUtils
{
	private static final String KEY_OXYGEN = "oxygen";

	public static <T extends IOxygenStorage> T readNBT(T instance, Tag tag)
	{
		if (instance != null && tag instanceof CompoundTag)
		{
			CompoundTag compound = (CompoundTag) tag;
			instance.setOxygenStored(compound.getInt(KEY_OXYGEN));
		}

		return instance;
	}

	public static Tag writeNBT(IOxygenStorage instance)
	{
		CompoundTag compound = new CompoundTag();
		compound.putInt(KEY_OXYGEN, instance.getOxygenStored());
		return compound;
	}

}
