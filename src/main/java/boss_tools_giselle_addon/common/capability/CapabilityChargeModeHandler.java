package boss_tools_giselle_addon.common.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityChargeModeHandler
{
	@CapabilityInject(IChargeModeHandler.class)
	public static Capability<IChargeModeHandler> CHARGE_MODE_HANDLER = null;

	@Nonnull
	public static IChargeMode readNBT(@Nullable IChargeModeHandler instance, @Nullable INBT nbt)
	{
		if (instance == null || nbt == null)
		{
			return ChargeMode.NONE;
		}

		return IChargeMode.readNBT(instance.getAvailableChargeModes(), nbt);
	}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IChargeModeHandler.class, new IStorage<IChargeModeHandler>()
		{
			@Override
			public INBT writeNBT(Capability<IChargeModeHandler> capability, IChargeModeHandler instance, Direction side)
			{
				return IChargeMode.writeNBT(instance.getChargeMode());
			}

			@Override
			public void readNBT(Capability<IChargeModeHandler> capability, IChargeModeHandler instance, Direction side, INBT nbt)
			{
				instance.setChargeMode(CapabilityChargeModeHandler.readNBT(instance, nbt));
			}

		}, ChargeModeHandlerEmpty::new);
	}

	private CapabilityChargeModeHandler()
	{

	}

}
