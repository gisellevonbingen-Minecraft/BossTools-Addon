package boss_tools_giselle_addon.common.capability;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Iterables;

import boss_tools_giselle_addon.common.item.OxygenCanItem;
import boss_tools_giselle_addon.common.registries.AddonItems;
import boss_tools_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gauge.GaugeValueSimple;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class OxygenChargerUtils
{
	public static IGaugeValue getInventoryOxygenChargerStorage(LivingEntity entity)
	{
		List<ItemStack> itemStacks = LivingEntityHelper.getInventoryStacks(entity);

		long stored = 0L;
		long capacity = 0L;

		for (ItemStack stack : itemStacks)
		{
			if (stack.isEmpty() == true)
			{
				continue;
			}

			IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(stack);

			if (oxygenCharger != null && oxygenCharger.getChargeMode() != ChargeMode.NONE)
			{
				IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
				stored = Math.min(stored + oxygenStorage.getOxygenStored(), Integer.MAX_VALUE);
				capacity = Math.min(capacity + oxygenStorage.getMaxOxygenStored(), Integer.MAX_VALUE);
			}

		}

		OxygenCanItem item = AddonItems.OXYGEN_CAN.get();
		return new GaugeValueSimple(item.getRegistryName(), (int) stored, (int) capacity, item.getName(item.getDefaultInstance()));
	}

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
