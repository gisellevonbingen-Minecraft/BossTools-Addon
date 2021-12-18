package boss_tools_giselle_addon.common.capability;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityChargeModeHandler
{
	@CapabilityInject(IChargeModeHandler.class)
	public static Capability<IChargeModeHandler> CHARGE_MODE_HANDLER = null;

	@Nonnull
	public static IChargeMode find(@Nullable List<IChargeMode> availableModes, @Nullable ResourceLocation name)
	{
		if (availableModes == null || name == null)
		{
			return ChargeMode.NONE;
		}

		for (IChargeMode mode : availableModes)
		{
			if (mode.getName().equals(name) == true)
			{
				return mode;
			}

		}

		return ChargeMode.NONE;
	}

	public static INBT writeNBT(@Nullable IChargeMode mode)
	{
		ResourceLocation name = (mode != null ? mode : ChargeMode.NONE).getName();
		return StringNBT.valueOf(name.toString());
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable IChargeModeHandler instance, @Nullable INBT nbt)
	{
		if (instance == null)
		{
			return ChargeMode.NONE;
		}

		return CapabilityChargeModeHandler.readNBT(instance.getAvailableChargeModes(), nbt);
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable List<IChargeMode> availableModes, @Nullable INBT nbt)
	{
		if (nbt == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation name = ResourceLocation.tryParse(nbt.getAsString());
		return find(availableModes, name);
	}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(IChargeModeHandler.class, new IStorage<IChargeModeHandler>()
		{
			@Override
			public INBT writeNBT(Capability<IChargeModeHandler> capability, IChargeModeHandler instance, Direction side)
			{
				return CapabilityChargeModeHandler.writeNBT(instance.getChargeMode());
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
