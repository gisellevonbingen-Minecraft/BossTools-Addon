package boss_tools_giselle_addon.common.capability;

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

public class OxygenCapacitorCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder
{
	public static final String KEY_NBT = BossToolsAddon.rl("oxygen_capacitor_capability").toString();
	public static final String KEY_OXYGEN_STORAGE = "oxygenStorage";

	private final ItemStack itemStack;
	private final IOxygenStorage oxygenStorage;

	public OxygenCapacitorCapabilityProvider(ItemStack itemStack, int capacity, int transfer)
	{
		this.itemStack = itemStack;
		this.oxygenStorage = new OxygenCapacitorOxygenStorage(this, capacity, 0, transfer);
	}

	public IOxygenStorage readOxygen(IOxygenStorage oxygenStorage)
	{
		CompoundNBT compound = NBTUtils.getTag(this.getItemStack(), KEY_NBT);
		Capability<IOxygenStorage> oxygen = CapabilityOxygen.OXYGEN;

		if (oxygen != null)
		{
			oxygen.readNBT(oxygenStorage, null, compound.get(KEY_OXYGEN_STORAGE));
		}

		return oxygenStorage;
	}

	public void writeOxygen(IOxygenStorage storage)
	{
		CompoundNBT compound = NBTUtils.getOrCreateTag(this.getItemStack(), KEY_NBT);
		compound.put(KEY_OXYGEN_STORAGE, CapabilityOxygen.OXYGEN.writeNBT(storage, null));
	}

	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction)
	{
		LazyOptional<T> oxygenCapability = OxygenUtil2.getOxygenCapability(capability, direction, this.getOxygenStorage());

		if (oxygenCapability.isPresent() == true)
		{
			return oxygenCapability;
		}

		return LazyOptional.empty();
	}

	public final ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public final IOxygenStorage getOxygenStorage()
	{
		return this.readOxygen(this.oxygenStorage);
	}

	@Override
	public void onOxygenChanged(IOxygenStorage storage, int delta)
	{
		this.writeOxygen(storage);
	}

}
