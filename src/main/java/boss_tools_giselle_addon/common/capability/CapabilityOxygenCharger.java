package boss_tools_giselle_addon.common.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityOxygenCharger
{
	public static final String KEY_CHARGE_MODE = "chargemode";

	@CapabilityInject(IOxygenCharger.class)
	public static Capability<IOxygenCharger> OXYGEN_CHARGER;

	@Nonnull
	public static INBT writeNBT(@Nullable IOxygenCharger instance)
	{
		CompoundNBT compound = new CompoundNBT();

		if (instance != null)
		{
			compound.put(KEY_CHARGE_MODE, IChargeMode.writeNBT(instance.getChargeMode()));
		}

		return compound;
	}

	@Nonnull
	public static <T extends IOxygenCharger> T readNBT(@Nullable T instance, @Nullable INBT nbt)
	{
		if (instance != null && nbt instanceof CompoundNBT)
		{
			CompoundNBT compound = (CompoundNBT) nbt;
			instance.setChargeMode(CapabilityChargeModeHandler.readNBT(instance, compound.get(KEY_CHARGE_MODE)));
		}

		return instance;
	}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IOxygenCharger.class, new IStorage<IOxygenCharger>()
		{
			@Override
			public INBT writeNBT(Capability<IOxygenCharger> capability, IOxygenCharger instance, Direction side)
			{
				return CapabilityOxygenCharger.writeNBT(instance);
			}

			@Override
			public void readNBT(Capability<IOxygenCharger> capability, IOxygenCharger instance, Direction side, INBT nbt)
			{
				CapabilityOxygenCharger.readNBT(instance, nbt);
			}

		}, OxygenChargerEmpty::new);
	}

	private CapabilityOxygenCharger()
	{

	}

}
