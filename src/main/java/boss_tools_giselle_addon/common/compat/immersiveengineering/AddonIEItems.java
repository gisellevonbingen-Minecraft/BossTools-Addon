package boss_tools_giselle_addon.common.compat.immersiveengineering;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.item.AddonItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonIEItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final RegistryObject<Item> MOLD_COMPRESSING = ITEMS.register("mold_compressing", () -> new Item(AddonItems.getMainItemProperties().stacksTo(1)));

	private AddonIEItems()
	{

	}

}
