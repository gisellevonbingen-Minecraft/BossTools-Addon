package beyond_earth_giselle_addon.common.capability;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.util.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.IOxygenStorageHolder;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.OxygenStorage;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.OxygenUtil;

public class OxygenCanCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder
{
	public static final String KEY_NBT = BeyondEarthAddon.rl("oxygen_capacitor_capability").toString();
	public static final String KEY_OXYGEN_STORAGE = "oxygenstorage";
	public static final String KEY_OXYGEN_CHARGER = "oxygencharger";

	private final ItemStack itemStack;
	private final OxygenStorage oxygenStorage;
	private final OxygenChargerWrapper oxygenCharger;

	public OxygenCanCapabilityProvider(ItemStack itemStack, int capacity, int transfer)
	{
		this.itemStack = itemStack;
		this.oxygenStorage = new RatedOxygenStorage(this, capacity, 0, transfer);
		this.oxygenCharger = new OxygenChargerWrapper()
		{
			@Override
			public void setChanged()
			{
				super.setChanged();
				OxygenCanCapabilityProvider.this.writeOxygenCharger();
			}

			@Override
			public IOxygenStorage getOxygenStorage()
			{
				return OxygenCanCapabilityProvider.this.getOxygenStorage();
			}
		};

		CompoundTag tag = this.getTag();
		this.oxygenStorage.deserializeNBT(tag.getCompound(KEY_OXYGEN_STORAGE));
		this.oxygenCharger.deserializeNBT(tag.getCompound(KEY_OXYGEN_CHARGER));
	}

	private void writeOxygenStorage()
	{
		if (this.oxygenStorage != null)
		{
			this.getOrCreateTag().put(KEY_OXYGEN_STORAGE, this.oxygenStorage.serializeNBT());
		}
	}

	private void writeOxygenCharger()
	{
		if (this.oxygenCharger != null)
		{
			this.getOrCreateTag().put(KEY_OXYGEN_CHARGER, this.oxygenCharger.serializeNBT());
		}
	}

	public CompoundTag getTag()
	{
		return NBTUtils.getTag(this.getItemStack(), KEY_NBT);
	}

	public CompoundTag getOrCreateTag()
	{
		CompoundTag compound = NBTUtils.getOrCreateTag(this.getItemStack(), KEY_NBT);
		return compound;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
	{
		if (capability == null)
		{
			return LazyOptional.empty();
		}

		LazyOptional<T> oxygenCapability = OxygenUtil.getOxygenCapability(capability, this::getOxygenStorage);

		if (oxygenCapability.isPresent() == true)
		{
			return oxygenCapability;
		}
		else if (capability == CapabilityOxygenCharger.OXYGEN_CHARGER)
		{
			return LazyOptional.of(this::getOxygenCharger).cast();
		}
		else if (capability == CapabilityChargeModeHandler.CHARGE_MODE_HANDLER)
		{
			return LazyOptional.of(this::getOxygenCharger).cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public void onOxygenChanged(IOxygenStorage storage, int delta)
	{
		this.writeOxygenStorage();
	}

	public final ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public final IOxygenStorage getOxygenStorage()
	{
		return this.oxygenStorage;
	}

	public final IOxygenCharger getOxygenCharger()
	{
		return this.oxygenCharger;
	}

}
