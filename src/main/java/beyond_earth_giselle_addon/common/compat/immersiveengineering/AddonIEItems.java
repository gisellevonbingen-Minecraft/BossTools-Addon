package beyond_earth_giselle_addon.common.compat.immersiveengineering;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import beyond_earth_giselle_addon.common.registries.ItemDeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class AddonIEItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BeyondEarthAddon.MODID);
	public static final RegistryObject<Item> MOLD_COMPRESSING = ITEMS.register("mold_compressing", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonIEItems()
	{

	}

}
