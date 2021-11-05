package boss_tools_giselle_addon.compat.mekanism.gear.mekasuit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import boss_tools_giselle_addon.util.ReflectionUtils;
import mekanism.common.capabilities.chemical.item.RateLimitMultiTankGasHandler.GasTankSpec;
import mekanism.common.item.gear.ItemMekaSuitArmor;

public class MekaSuitGasSpecHelper
{
	private static final Map<ItemMekaSuitArmor, Set<GasTankSpec>> CACHES = new HashMap<>();

	public static void addSpec(ItemMekaSuitArmor item, GasTankSpec spec)
	{
		getSpecs(item).add(spec);
	}

	@SuppressWarnings("unchecked")
	public static Set<GasTankSpec> getSpecs(ItemMekaSuitArmor item)
	{
		return CACHES.computeIfAbsent(item, i ->
		{
			try
			{
				return (Set<GasTankSpec>) ReflectionUtils.getDeclaredAccessibleField(ItemMekaSuitArmor.class, "gasTankSpecs").get(i);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			}
		});
	}

	private MekaSuitGasSpecHelper()
	{

	}

}
