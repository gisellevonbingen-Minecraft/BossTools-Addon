package boss_tools_giselle_addon.common.compat.thermal;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.item.AddonItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonThermalItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final RegistryObject<Item> PRESS_COMPRESSING_DIE = ITEMS.register("press_compressing_die", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonThermalItems()
	{

	}

}
