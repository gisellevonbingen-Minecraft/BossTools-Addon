package beyond_earth_giselle_addon.common.registries;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class AddonItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BeyondEarthAddon.MODID);
	public static final RegistryObject<OxygenCanItem> OXYGEN_CAN = ITEMS.register("oxygen_can", () -> new OxygenCanItem(new Item.Properties().tab(AddonTabs.tab_main)));
	public static final RegistryObject<Item> SPACE_STATION_ICON = ITEMS.register("space_station_icon", () -> new Item(new Item.Properties()));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private AddonItems()
	{

	}

}
