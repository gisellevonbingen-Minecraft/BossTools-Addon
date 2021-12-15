package boss_tools_giselle_addon.common.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.util.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.IOxygenStorageHolder;

public class OxygenCanCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder, IChargeModeHandler
{
	public static final String KEY_NBT = BossToolsAddon.rl("oxygen_capacitor_capability").toString();
	public static final String KEY_OXYGEN_STORAGE = "oxygenStorage";
	public static final String KEY_CHARGE_MODE = "chargemode";

	private final ItemStack itemStack;
	private final IOxygenStorage oxygenStorage;

	public OxygenCanCapabilityProvider(ItemStack itemStack, int capacity, int transfer)
	{
		this.itemStack = itemStack;
		this.oxygenStorage = new RatedOxygenStorage(this, capacity, 0, transfer);
	}

	public IOxygenStorage readOxygen(IOxygenStorage oxygenStorage)
	{
		Capability<IOxygenStorage> oxygen = CapabilityOxygen.OXYGEN;

		if (oxygen != null)
		{
			oxygen.readNBT(oxygenStorage, null, this.getTag().get(KEY_OXYGEN_STORAGE));
		}

		return oxygenStorage;
	}

	public void writeOxygen(IOxygenStorage storage)
	{
		this.getOrCreateTag().put(KEY_OXYGEN_STORAGE, CapabilityOxygen.OXYGEN.writeNBT(storage, null));
	}

	public CompoundNBT getTag()
	{
		return NBTUtils.getTag(this.getItemStack(), KEY_NBT);
	}

	public CompoundNBT getOrCreateTag()
	{
		CompoundNBT compound = NBTUtils.getOrCreateTag(this.getItemStack(), KEY_NBT);
		return compound;
	}

	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
	{
		if (capability == null)
		{
			return LazyOptional.empty();
		}

		LazyOptional<T> oxygenCapability = OxygenUtil2.getOxygenCapability(capability, direction, this.getOxygenStorage());

		if (oxygenCapability.isPresent() == true)
		{
			return oxygenCapability;
		}

		if (capability == CapabilityChargeModeHandler.CHARGE_MODE_HANDLER)
		{
			return LazyOptional.of(() -> this).cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public void onOxygenChanged(IOxygenStorage storage, int delta)
	{
		this.writeOxygen(storage);
	}

	@Override
	public void setChargeMode(@Nullable IChargeMode mode)
	{
		this.getOrCreateTag().put(KEY_CHARGE_MODE, CapabilityChargeModeHandler.writeNBT(mode));
	}

	@Override
	@Nonnull
	public IChargeMode getChargeMode()
	{
		return CapabilityChargeModeHandler.readNBT(this.getAvailableChargeModes(), this.getTag().get(KEY_CHARGE_MODE));
	}

	public final ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public final IOxygenStorage getOxygenStorage()
	{
		return this.readOxygen(this.oxygenStorage);
	}
}
