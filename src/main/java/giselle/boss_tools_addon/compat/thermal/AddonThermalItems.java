package giselle.boss_tools_addon.compat.thermal;

import giselle.boss_tools_addon.BossToolsAddon;
import giselle.boss_tools_addon.common.item.AddonItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonThermalItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final RegistryObject<Item> PRESS_COMPRESSING_DIE = ITEMS.register("press_compressing_die", () -> new Item(AddonItems.getMainItemProperties()));

	private AddonThermalItems()
	{

	}

}
