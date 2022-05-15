package beyond_earth_giselle_addon.common.compat.thermal;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import beyond_earth_giselle_addon.common.registries.ItemDeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class AddonThermalItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BeyondEarthAddon.MODID);
	public static final RegistryObject<Item> PRESS_COMPRESSING_DIE = ITEMS.register("press_compressing_die", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonThermalItems()
	{

	}

}
