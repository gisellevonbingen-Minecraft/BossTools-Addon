package boss_tools_giselle_addon.common.compat.thermal;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.registries.AddonItems;
import boss_tools_giselle_addon.common.registries.ItemDeferredRegister;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class AddonThermalItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);
	public static final RegistryObject<Item> PRESS_COMPRESSING_DIE = ITEMS.register("press_compressing_die", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonThermalItems()
	{

	}

}
