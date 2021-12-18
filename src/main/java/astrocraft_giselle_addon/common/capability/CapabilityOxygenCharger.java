package astrocraft_giselle_addon.common.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityOxygenCharger
{
	public static final String KEY_CHARGE_MODE = "chargemode";

	public static Capability<IOxygenCharger> OXYGEN_CHARGER = CapabilityManager.get(new CapabilityToken<>()
	{
	});

	@Nonnull
	public static CompoundTag writeNBT(@Nullable IOxygenCharger instance)
	{
		CompoundTag compound = new CompoundTag();

		if (instance != null)
		{
			compound.put(KEY_CHARGE_MODE, CapabilityChargeModeHandler.writeNBT(instance.getChargeMode()));
		}

		return compound;
	}

	@Nonnull
	public static <T extends IOxygenCharger> T readNBT(@Nullable T instance, @Nullable CompoundTag compound)
	{
		if (instance != null && compound != null)
		{
			instance.setChargeMode(CapabilityChargeModeHandler.readNBT(instance, compound.get(KEY_CHARGE_MODE)));
		}

		return instance;
	}

	public static void register(RegisterCapabilitiesEvent event)
	{
		event.register(IOxygenCharger.class);
	}

}
