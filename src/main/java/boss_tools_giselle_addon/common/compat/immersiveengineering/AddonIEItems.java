package boss_tools_giselle_addon.common.compat.immersiveengineering;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.registries.AddonItems;
import boss_tools_giselle_addon.common.registries.ItemDeferredRegister;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class AddonIEItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BossToolsAddon.MODID);
	public static final RegistryObject<Item> MOLD_COMPRESSING = ITEMS.register("mold_compressing", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonIEItems()
	{

	}

}
