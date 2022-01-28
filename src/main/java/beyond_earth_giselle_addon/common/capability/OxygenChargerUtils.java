package beyond_earth_giselle_addon.common.capability;

import java.util.stream.Stream;

import com.google.common.collect.Iterables;

import beyond_earth_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;

public class OxygenChargerUtils
{
	public static IOxygenCharger firstExtractableOxygenCharger(LivingEntity entity, int extracting, ItemStack beContains)
	{
		return streamExtractableOxygenCharger(entity, extracting, beContains).findFirst().orElse(null);
	}

	public static Stream<IOxygenCharger> streamExtractableOxygenCharger(LivingEntity entity, int extracting, ItemStack beContains)
	{
		return streamExtractableItems(entity, extracting, beContains).map(OxygenChargerUtils::getOxygenCharger);
	}

	public static ItemStack firstExtractableItem(LivingEntity entity, int extracting, ItemStack beContains)
	{
		return streamExtractableItems(entity, extracting, beContains).findFirst().orElse(ItemStack.EMPTY);
	}

	public static Stream<ItemStack> streamExtractableItems(LivingEntity entity, int extracting, ItemStack beContains)
	{
		return LivingEntityHelper.getInventoryStacks(entity).stream().filter(is ->
		{
			IOxygenCharger oxygenCharger = getOxygenCharger(is);

			if (oxygenCharger != null && Iterables.contains(oxygenCharger.getChargeMode().getItemStacks(entity), beContains) == true)
			{
				IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
				return oxygenStorage.extractOxygen(extracting, true) == extracting;
			}
			else
			{
				return false;
			}

		});
	}

	public static IOxygenCharger getOxygenCharger(ItemStack is)
	{
		return is.getCapability(CapabilityOxygenCharger.OXYGEN_CHARGER).orElse(null);
	}

	private OxygenChargerUtils()
	{

	}

}
