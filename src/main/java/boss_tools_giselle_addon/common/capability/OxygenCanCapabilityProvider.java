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
import net.mrscauthd.boss_tools.capability.OxygenStorage;

public class OxygenCanCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder
{
	public static final String KEY_NBT = BossToolsAddon.rl("oxygen_capacitor_capability").toString();
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

		CompoundNBT tag = this.getTag();
		CapabilityOxygenUtils.readNBT(this.oxygenStorage, tag.getCompound(KEY_OXYGEN_STORAGE));
		CapabilityOxygenCharger.readNBT(this.oxygenCharger, tag.getCompound(KEY_OXYGEN_CHARGER));
	}

	private void writeOxygenStorage()
	{
		if (this.oxygenStorage != null)
		{
			this.getOrCreateTag().put(KEY_OXYGEN_STORAGE, CapabilityOxygenUtils.writeNBT(this.oxygenStorage));
		}
	}

	private void writeOxygenCharger()
	{
		if (this.oxygenCharger != null)
		{
			this.getOrCreateTag().put(KEY_OXYGEN_CHARGER, CapabilityOxygenCharger.writeNBT(this.oxygenCharger));
		}
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

	@Override
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
