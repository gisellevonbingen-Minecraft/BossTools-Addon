package boss_tools_giselle_addon.common.capability;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.util.NBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.IOxygenStorageHolder;

public class OxygenCanCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder
{
	public static final String KEY_NBT = BossToolsAddon.rl("oxygen_capacitor_capability").toString();
	public static final String KEY_OXYGEN_STORAGE = "oxygenstorage";
	public static final String KEY_OXYGEN_CHARGER = "oxygencharger";

	private final ItemStack itemStack;
	private final IOxygenStorage oxygenStorage;
	private final IOxygenCharger oxygenCharger;

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
				OxygenCanCapabilityProvider.this.writeOxygenCharger(this);
			}

			@Override
			public IOxygenStorage getOxygenStorage()
			{
				return OxygenCanCapabilityProvider.this.getOxygenStorage();
			}
		};
	}

	public <T extends IOxygenStorage> T readOxygenStorage(T oxygenStorage)
	{
		return CapabilityOxygenUtils.readNBT(oxygenStorage, this.getTag().get(KEY_OXYGEN_STORAGE));
	}

	public void writeOxygenStorage(IOxygenStorage storage)
	{
		this.getOrCreateTag().put(KEY_OXYGEN_STORAGE, CapabilityOxygenUtils.writeNBT(storage));
	}

	public <T extends IOxygenCharger> T readOxygenCharger(T oxygenCharger)
	{
		return CapabilityOxygenCharger.readNBT(oxygenCharger, this.getTag().get(KEY_OXYGEN_CHARGER));
	}

	public void writeOxygenCharger(IOxygenCharger charger)
	{
		this.getOrCreateTag().put(KEY_OXYGEN_CHARGER, CapabilityOxygenCharger.writeNBT(charger));
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

		LazyOptional<T> oxygenCapability = OxygenUtil2.getOxygenStorageOrEmpty(capability, direction, this::getOxygenStorage);

		if (oxygenCapability.isPresent() == true)
		{
			return oxygenCapability;
		}

		if (capability == CapabilityOxygenCharger.OXYGEN_CHARGER)
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
		this.writeOxygenStorage(storage);
	}

	public final ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public final IOxygenStorage getOxygenStorage()
	{
		return this.readOxygenStorage(this.oxygenStorage);
	}

	public final IOxygenCharger getOxygenCharger()
	{
		return this.readOxygenCharger(this.oxygenCharger);
	}

}