package giselle.bosstools_addon.common.item;

import giselle.bosstools_addon.BossToolsAddon;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AddonItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, BossToolsAddon.MODID);
	public static final RegistryObject<Item> SPACE_FIRE_PROOF_PLATE = ITEMS.register("space_fire_proof_plate", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> SPACE_BREATH_GEAR = ITEMS.register("space_breath_gear", () -> new Item(getMainItemProperties()));
	public static final RegistryObject<Item> VENUS_ACID_PROOF_PLATE = ITEMS.register("venus_acid_proof_plate", () -> new Item(getMainItemProperties()));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private AddonItems()
	{

	}

}
