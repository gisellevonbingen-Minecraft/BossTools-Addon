package astrocraft_giselle_addon.common.capability;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityChargeModeHandler
{
	public static Capability<IChargeModeHandler> CHARGE_MODE_HANDLER = CapabilityManager.get(new CapabilityToken<>()
	{
	});

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

	public static Tag writeNBT(@Nullable IChargeMode mode)
	{
		ResourceLocation name = (mode != null ? mode : ChargeMode.NONE).getName();
		return StringTag.valueOf(name.toString());
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable IChargeModeHandler instance, @Nullable Tag tag)
	{
		if (instance == null)
		{
			return ChargeMode.NONE;
		}

		return CapabilityChargeModeHandler.readNBT(instance.getAvailableChargeModes(), tag);
	}

	@Nonnull
	public static IChargeMode readNBT(@Nullable List<IChargeMode> availableModes, @Nullable Tag tag)
	{
		if (tag == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation name = ResourceLocation.tryParse(tag.getAsString());
		return find(availableModes, name);
	}

	public static void register(RegisterCapabilitiesEvent event)
	{
		event.register(IOxygenCharger.class);
	}

	private CapabilityChargeModeHandler()
	{

	}

}
